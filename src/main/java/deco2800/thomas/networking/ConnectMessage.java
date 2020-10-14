package deco2800.thomas.networking;
import deco2800.thomas.entities.AbstractEntity;

public class ConnectMessage {
	private String username;
	private AbstractEntity playerEntity;

	public String getUsername(){
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public AbstractEntity getPlayerEntity(){
		return playerEntity;
	}

	public void setPlayerEntity(AbstractEntity playerEntity) {
		this.playerEntity = playerEntity;
	}
}
