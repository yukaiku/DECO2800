package deco2800.thomas.worlds.dungeons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.environment.Portal;
import deco2800.thomas.entities.environment.tundra.TundraEncryptionMachine;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.PlayerManager;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.dungeons.tundra.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TundraDungeon extends AbstractWorld {
	public static final int DEFAULT_WIDTH = 5;
	public static final int DEFAULT_HEIGHT = 5;

	/**
	 * Skin - for creating dialogs
	 */
	private final Skin skin;

	/**
	 * Dialogs that are managed by the Dialog Manager (not actually used)
	 */
	private List<AbstractDialogBox> allTundraDungeonDialogs;

	/**
	 * This boolean variable is used to initialize the dungeon when the onTick() method is called the first time
	 */
	private boolean initialized = false;

	/**
	 * Special coordinates
	 */
	private SquareVector helpCoordinates;
	private SquareVector encryptionMachineCoordinates;
	private SquareVector exitPortalCoordinates;

	/**
	 * The puzzle
	 */
	private final String WORD_FILE_PATH = "resources/environment/tundra/tundra-dungeon-words.txt";
	private String keyword;
	private HashMap<SquareVector, Character> puzzle;
	private HashSet<SquareVector> usedCoordinates;
	private String playerAnswer;

	public TundraDungeon() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Constructor
	 * @param width width of the dungeon
	 * @param height height of the dungeon
	 */
	public TundraDungeon(int width, int height) {
		super(width, height);
		this.allTundraDungeonDialogs = new ArrayList<>();

		for (int col = -width; col < width; col++) {
			for (int row = -height; row < height; row++) {
				if ((col + row) % 2 == 0) {
					tiles.add(new Tile("tundra-tile-4", col, row));
				} else {
					tiles.add(new Tile("tundra-tile-5", col, row));
				}
			}
		}

		generateTileMap();
		generateTileIndices();

		usedCoordinates = new HashSet<>();

		// PlayerPeon
		this.setPlayerEntity(new PlayerPeon(-0f, -0f, 0.15f));
		addEntity(this.getPlayerEntity());
		usedCoordinates.add(this.playerEntity.getPosition());

		// Enemy Manager
		GameManager.get().addManager(new EnemyManager(this));

		this.skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));

		createSpecialTiles();
		createPuzzle();
	}

	/**
	 * Create special tiles for the dungeon. These includes:
	 * - A help tile: the player can invoke the help dialog when moving to this tile
	 * - An encryption machine tile: the player can enter the answer for the puzzle by moving to this tile
	 * - An exit portal tile: the player can exit from the dungeon by moving to this tile (after they have already solved the puzzle)
	 */
	private void createSpecialTiles() {
		helpCoordinates = new SquareVector(-5, -5);
		entities.add(new StaticEntity(getTile(helpCoordinates), 1, "tundra-dungeon-help", false));
		usedCoordinates.add(helpCoordinates);

		encryptionMachineCoordinates = new SquareVector(2, 0);
		entities.add(new TundraEncryptionMachine(getTile(encryptionMachineCoordinates)));
		usedCoordinates.add(encryptionMachineCoordinates);

		exitPortalCoordinates = new SquareVector(4, 0);
		usedCoordinates.add(exitPortalCoordinates);
	}

	/**
	 * Spawn the exit portal
	 */
	private void spawnExitPortal() {
		Tile portalTile = getTile(exitPortalCoordinates);
		entities.add(new Portal(portalTile, false, "portal", "ExitPortal"));
	}

	/**
	 * Create the puzzle
	 * The puzzle requires a 5-letter word, which is read and selected from a text file.
	 * Then each letter is put at a random position in the dungeon.
	 */
	private void createPuzzle() {
		ArrayList<String> words = readWords();
		if (words == null) {
			throw new RuntimeException("Cannot load words for TundraDungeon's puzzle.");
		}
		keyword = words.get(new Random().nextInt(words.size()));

		puzzle = new HashMap<>();

		ArrayList<SquareVector> coordinates = new ArrayList<>();

		for (int col = -width; col < width; col++) {
			for (int row = -height; row < height; row++) {
				coordinates.add(new SquareVector(col, row));
			}
		}

		Collections.shuffle(coordinates);

		int j = 0;

		for (SquareVector coordinate : coordinates) {
			if (usedCoordinates.contains(coordinate)) {
				continue;
			}

			puzzle.put(coordinate, keyword.charAt(j));
			usedCoordinates.add(coordinate);

			j++;
			if (j == keyword.length()) {
				break;
			}
		}

		playerAnswer = null;
	}

	public void setPlayerAnswer(String playerAnswer) {
		this.playerAnswer = playerAnswer;
	}

	public String getWordFilePath() {
		return WORD_FILE_PATH;
	}

	/**
	 * Read all words from a text file that saves 5-letter English words
	 * @return a list of all words in the text file
	 */
	public ArrayList<String> readWords() {
		ArrayList<String> words = new ArrayList<>();

		try(BufferedReader reader = new BufferedReader(new FileReader(WORD_FILE_PATH))) {
			String line = reader.readLine();

			while (line != null) {
				words.add(line);
				line = reader.readLine();
			}

			reader.close();

			return words;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void generateTiles() {

	}

	/**
	 * Initialize the dungeon. This method is called the first time onTick() is called.
	 */
	private void initialize() {
		Stage stage = GameManager.get().getStage();

		// Set up all types of dialogs
		TundraDungeonDialog.setup(stage, skin);

		// Launch instructions
		TundraDungeonInstructionDialog.launch();

		// Set initialized
		initialized = true;
	}

	/**
	 * Check if the puzzle is solved. This method is called in onTick()
	 */
	private void checkPuzzleSolved() {
		if (playerAnswer != null) {
			if (playerAnswer.equals(keyword)) {
				GameManager.getManagerFromInstance(PlayerManager.class).grantWizardSkill(WizardSkills.ICEBALL);
				spawnExitPortal();
			} else {
				TundraDungeonAnnouncementDialog announcementDialog = new TundraDungeonAnnouncementDialog("Oops", skin);
				announcementDialog.show();
			}

			playerAnswer = null;
		}
	}

	/**
	 * Handle the text dialog. This method is called in onTick()
	 */
	private void handleTextDialog() {
		if (getPlayerEntity().getPosition().isCloseEnoughToBeTheSame(encryptionMachineCoordinates) && !TundraDungeonTextDialog.isLocked()) {
			PlayerPeon playerPeon = (PlayerPeon) getPlayerEntity();
			if (playerPeon.getMovementTask() != null) {
				playerPeon.getMovementTask().stopTask();
			}
			TundraDungeonTextDialog textDialog = new TundraDungeonTextDialog(this, "Your answer", skin);
			textDialog.show();
			TundraDungeonTextDialog.lock();
		} else if (!getPlayerEntity().getPosition().isCloseEnoughToBeTheSame(encryptionMachineCoordinates) && TundraDungeonTextDialog.isLocked()) {
			TundraDungeonTextDialog.release();
		}
	}

	/**
	 * Handle the hint dialog. This method is called in onTick()
	 */
	private void handleHintDialog() {
		if (!TundraDungeonHintDialog.isLocked()) {
			for (SquareVector coordinates : puzzle.keySet()) {
				if (getPlayerEntity().getPosition().isCloseEnoughToBeTheSame(coordinates)) {
					TundraDungeonHintDialog hintDialog = new TundraDungeonHintDialog("Letter found!", skin, puzzle.get(coordinates));
					hintDialog.show();
					TundraDungeonHintDialog.lock();
					PlayerPeon playerPeon = (PlayerPeon) getPlayerEntity();
					if (playerPeon.getMovementTask() != null) {
						playerPeon.getMovementTask().stopTask();
					}
					break;
				}
			}
		} else {
			boolean toRelease = true;
			for (SquareVector coordinates : puzzle.keySet()) {
				if (getPlayerEntity().getPosition().isCloseEnoughToBeTheSame(coordinates)) {
					toRelease = false;
					break;
				}
			}

			if (toRelease) {
				TundraDungeonHintDialog.release();
			}
		}
	}

	/**
	 * Handle the instruction dialog. This method is called in onTick()
	 */
	private void handleInstructionDialog() {
		if (TundraDungeonInstructionDialog.isToShow()) {
			TundraDungeonInstructionDialog.showDialog();
		}

		if (getPlayerEntity().getPosition().isCloseEnoughToBeTheSame(helpCoordinates) && !TundraDungeonInstructionDialog.isLocked()) {
			PlayerPeon playerPeon = (PlayerPeon) getPlayerEntity();
			if (playerPeon.getMovementTask() != null) {
				playerPeon.getMovementTask().stopTask();
			}
			TundraDungeonInstructionDialog.launch();
			TundraDungeonInstructionDialog.lock();
		} else if (!getPlayerEntity().getPosition().isCloseEnoughToBeTheSame(helpCoordinates) && TundraDungeonInstructionDialog.isLocked()) {
			TundraDungeonInstructionDialog.release();
		}
	}

	/**
	 * Adds a dialog box to this world
	 * @param box dialog box
	 */
	public void addDialogue(AbstractDialogBox box){ this.allTundraDungeonDialogs.add(box);}

	@Override
	public String getType() {
		return "TundraDungeon";
	}

	@Override
	public void onTick(long i) {
		super.onTick(i);

		if (!initialized) {
			initialize();
		}

		checkPuzzleSolved();
		handleInstructionDialog();
		handleHintDialog();
		handleTextDialog();

		for (AbstractEntity e : this.getEntities()) {
			e.onTick(0);
		}
	}

	@Override
	public List<AbstractDialogBox> returnAllDialogues() {
		return allTundraDungeonDialogs;
	}
}
