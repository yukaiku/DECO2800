package deco2800.thomas.managers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.networking.*;
import deco2800.thomas.worlds.Tile;

import org.objenesis.strategy.StdInstantiatorStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * NetworkManager
 *
 * Handles both server and client side of the networking stack.
 */
public class NetworkManager extends TickableManager  {
    private static final Logger logger = LoggerFactory.getLogger(NetworkManager.class);

    private boolean isConnected = false;
    private boolean isHosting = false;

    private int messagesReceived = 0;
    private int messagesSent = 0;

    private static final int BUFFER_SIZE = 1048576;
    private static final int TCP_PORT = 54556;
    private static final int UDP_PORT = 54778;

    private Server server = null;
    private Client client = null;

    private HashMap<Integer, String> userConnections = new HashMap<>();

    // Unique identifier for this application. Attempting to connect to a server
    // with the same username as another User on that server is not allowed.
    private String localUsername = "";

    /**
     * Increments the number of messages received.
     * <p>
     *     Used be the client and server listeners to update the number of
     *     messages received by this particular NetworkManager.
     * </p>
     */
    public void incrementMessagesReceived() {
        messagesReceived++;
    }

    /**
     * Add a user connection to the list of connections
     * @param connectionID - the id of the connected user
     * @param username - the username of the connected user
     */
    public void addClientConnection(Integer connectionID, String username) {
        if (!userConnections.containsKey(connectionID)) {
            userConnections.put(connectionID, username);
        } else {
            logger.error("A client is attempting to connect on the same ConnectionID as another client!");
        }
    }

    /**
     * Deletes a mapping between the given client and user.
     * <p>
     *     Once a client disconnects from the server (for whatever reason),
     *     if they had a user then it will be deleted.
     * </p>
     * @param connectionID The connectionID of the client to be removed.
     * @param username The username of the client to be removed.
     */
    public void removeClientConnection(Integer connectionID, String username) {
        userConnections.remove(connectionID, username);
    }

    /**
     * Retrieves a username mapped to a particular connection ID.
     * @param connectionID The connection ID of the client to retrieve the username of.
     * @return the username mapped to the given connection ID, or null if it doesn't exist.
     */
    public String getClientUsernameFromConnection(int connectionID) {
        return userConnections.getOrDefault(connectionID, null);
    }

    /**
     * Returns the number of messages received
     * @return the number of messages received
     */
    public int getMessagesReceived() {
        return messagesReceived;
    }

    /**
     * Returns the number of messages sent
     * @return the number of messages sent
     */
    public int getMessagesSent() {
        return messagesSent;
    }

    /**
     * Is this user the host?
     * @return true if hosting, false otherwise
     */
    public boolean isHost() {
        return isHosting;
    }

    /**
     * Connects to a host at specified ip and port
     * @param ip - the ip of the host
     * @param username - the username to connect with
     * @return boolean - Was the connection successful?
     */
	public boolean connectToHost(String ip, String username) {
        this.localUsername = username;
        logger.info("Connecting with username {}", this.localUsername);
        if (isConnected || isHosting) {
            return false;
        }
        isConnected = true;
        GameManager.get().getManager(OnScreenMessageManager.class).addMessage("Attempting to connect to server...");
        client = new Client(BUFFER_SIZE, BUFFER_SIZE);

        // Register all classes
        Kryo kyro = client.getKryo();
        // KYRO auto registration
        kyro.setRegistrationRequired(false);
        ((Kryo.DefaultInstantiatorStrategy)
                kyro.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());

        // Start the client on ports 54555 and 54777
        client.start();
        try {
            client.connect(5000, ip, TCP_PORT, UDP_PORT);
        } catch (IOException e) {
            logger.error("Failed initial connection due to {}", e);
            GameManager.get().getManager(OnScreenMessageManager.class).addMessage("Connection failed.");
            return false;
        }

        // Add all listeners for the client, allowing it to receive information from the host.
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                messagesReceived++;
                if (object instanceof TileUpdateMessage) {
                    GameManager.get().getWorld().updateTile(((TileUpdateMessage) object).tile);
                    GameManager.get().getWorld().assignTileNeighbours();
                } else if (object instanceof ChatMessage) {
                    GameManager.get().getManager(OnScreenMessageManager.class).addMessage(object.toString());
                } else if (object instanceof SingleEntityUpdateMessage){
                    GameManager.get().getWorld().updateEntity(((SingleEntityUpdateMessage) object).entity);
                } else if (object instanceof TileDeleteMessage) {
                    GameManager.get().getWorld().disposeTile(((TileDeleteMessage) object).tileID);
                } else if (object instanceof EntityDeleteMessage) {
                    GameManager.get().getWorld().disposeEntity(((EntityDeleteMessage) object).entityID);
                }
            }
         });

        


        logger.info("Sending initial connect message to host, requesting initial information to be sent to this client.");
        
        ConnectMessage request = new ConnectMessage();
        request.username = username;
        client.sendTCP(request);

        // Broadcast to the host in-game that this client has connected.
        sendChatMessage("Joined the game.");

        // Broadcast to this client in-game that it has successfully connected to the server.
        GameManager.get().getManager(OnScreenMessageManager.class).addMessage("Connected to server.");
        logger.info("CLIENT WAS INITIALISED SUCCESSFULLY.");
        return true;
    }

    /**
     * Hosts a game with the specified username on ports (54555, 54777).
     * @param username - the username of the host
     * @return true if
     */
	public boolean startHosting(String username) {
        this.localUsername = username;
        logger.info("Hosting with username {}", this.localUsername);
        if (isConnected || isHosting) {
            return false;
        }
        isHosting = true;

        server = new Server(BUFFER_SIZE, BUFFER_SIZE);

        // Register the classes to be serialised with kryo
        Kryo kyro = server.getKryo();
        // KYRO auto registration
        kyro.setRegistrationRequired(false);
        ((Kryo.DefaultInstantiatorStrategy)
                kyro.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());

        // Start the server on ports 54555 and 54777
        server.start();
        try {
            server.bind(TCP_PORT, UDP_PORT);
        } catch (IOException e) {
            logger.error("Failed to bind server to ports 54555 and 54777");
            return false;
        }

        // Add all listeners for the host, allowing it to receive information from all its clients.
        logger.info("Attempting to add message listeners to server.");
        server.addListener(new Listener() {
            @Override
            public void received (Connection connection, Object object) {
                messagesReceived++;
                System.out.println(object);

                if (object instanceof SingleEntityUpdateMessage) {
                    SingleEntityUpdateMessage message = (SingleEntityUpdateMessage) object;

                    System.out.println(message);

                    GameManager.get().getWorld().updateEntity(((SingleEntityUpdateMessage) object).entity);
                }

                if (object instanceof ChatMessage) {
                    // Forward the chat message to all clients
                    server.sendToAllTCP(object);
                    GameManager.get().getManager(OnScreenMessageManager.class).addMessage(object.toString());
                }

                if (object instanceof ConnectMessage) {
                    sendChatMessage(connection.getID() + " connected.");
                    // Reply with the tilemap
                    TileUpdateMessage message = new TileUpdateMessage();
                    List<Tile> tiles = GameManager.get().getWorld().getTiles();
                    for (Tile t : tiles) {
                        message.tile = t;
                        server.sendToTCP(connection.getID(), message);
                    }
                }

                if (object instanceof TileDeleteMessage) {
                    GameManager.get().getWorld().disposeTile(((TileDeleteMessage) object).tileID);
                }
            }
        });

        logger.info("HOST WAS INITIALISED SUCCESSFULLY.");
        return true;
    }

    /**
     * On tick method
     * @param i
     */
    @Override
    public void onTick(long i) {
        if (isConnected) {
            onTickClient();
        } else if (isHosting) {
            onTickHost();
        }
    }

    /**
     * Runs each tick for hosts.
     */
    private void onTickHost() {
        //Only send messages if there are clients
        for (AbstractEntity e : GameManager.get().getWorld().getEntities()) {
            SingleEntityUpdateMessage message = new SingleEntityUpdateMessage();
            message.entity = e;
            server.sendToAllTCP(message);
        }

    }

    /**
     * Runs each tick for clients.
     */
    private void onTickClient(){
    }


    /**
     * Sends a chat message to the network clients
     * @param message
     */
    public void sendChatMessage(String message) {
        if (isHosting) {
            GameManager.get().getManager(OnScreenMessageManager.class).addMessage("[" + this.localUsername + "] " + message);
            server.sendToAllTCP(new ChatMessage(this.localUsername, message));
        } else {
            client.sendTCP(new ChatMessage(this.localUsername, message));
        }
    }

    /**
     * Get the ID of the client
     * @return int - the client id
     */
    public int getID() {
        if (isHosting) {
            return 0;
        }
        if (isConnected) {
            return client.getID();
        }
        return -1;
    }

    /**
     * Get the username of a host/client
     * @return String - the username of the host/client
     */
    public String getUsername() {
        return localUsername;
    }

    /**
     * Delete the given tile
     * @param t - the tile to delete
     */
    public void deleteTile(Tile t) {
        TileDeleteMessage msg = new TileDeleteMessage();
        msg.tileID = t.getTileID();
        if (isHosting) {
            server.sendToAllTCP(msg);
        } else {
            client.sendTCP(msg);
        }
    }



    /**
     * Delete the given entity.
     * <p>
     *     The host will ensure the entity has also been deleted in its own game.
     *     Client deletion of entities is handled in the EntityDeleteMessage class,
     *     which will be sent by the server once it receives the EntityDeleteMessage
     *     from the client.
     * </p>
     * @param e - the entity to delete
     */
    public void deleteEntity(AbstractEntity e) {
        EntityDeleteMessage msg = new EntityDeleteMessage();
        msg.entityID = e.getEntityID();
        if (isHosting) {
            server.sendToAllTCP(msg);
        } else {
            client.sendTCP(msg);
        }
    }

}