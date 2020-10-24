package deco2800.thomas.worlds.dungeons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Rock;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.environment.Portal;
import deco2800.thomas.entities.environment.tundra.TundraEncryptionMachine;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.PlayerManager;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.TestWorld;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.dungeons.tundra.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TundraDungeon extends AbstractWorld {
	private final Logger logger = LoggerFactory.getLogger(TestWorld.class);
	static final int DEFAULT_WIDTH = 5;
	static final int DEFAULT_HEIGHT = 5;

	private final Skin skin;

	boolean initialized = false;

	String keyword;
	HashMap<SquareVector, Character> puzzle;
	HashSet<SquareVector> usedCoordinates;
	SquareVector helpCoordinates;
	SquareVector machineCoordinates;
	SquareVector exitPortalCoordinates;
	String playerAnswer;

	private List<AbstractDialogBox> allTundraDungeonDialogs;

	public TundraDungeon() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

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

		helpCoordinates = new SquareVector(-5, -5);
		entities.add(new Rock(getTile(helpCoordinates), false));
		usedCoordinates.add(helpCoordinates);

		machineCoordinates = new SquareVector(2, 0);
		entities.add(new TundraEncryptionMachine(getTile(machineCoordinates)));
		usedCoordinates.add(machineCoordinates);

		exitPortalCoordinates = new SquareVector(4, 0);
		usedCoordinates.add(exitPortalCoordinates);

		createPuzzle();
		System.err.println("keyword: " + keyword);
	}

	private void spawnExitPortal() {
		Tile portalTile = getTile(4, 0);
		entities.add(new Portal(portalTile, false, "portal", "ExitPortal"));
	}

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

	private ArrayList<String> readWords() {
		ArrayList<String> words = new ArrayList<>();
		String filePath = "resources/environment/tundra/tundra-dungeon-words.txt";

		try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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

	private void initialize() {
		Stage stage = GameManager.get().getStage();

		// Add help button
		TextButton helpButton = new TextButton("?", skin);
		helpButton.setPosition((Gdx.graphics.getWidth() >> 1) + helpButton.getWidth() / 2,
				(Gdx.graphics.getHeight() >> 1) + helpButton.getHeight() / 2);
		helpButton.setSize(100, 50);
		helpButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TundraDungeonInstructionDialog.launch();
			}
		});

		// Set up all types of dialogs
		TundraDungeonDialog.setup(stage, skin);

		// Launch instructions
		TundraDungeonInstructionDialog.launch();

		// Set initialized
		initialized = true;
	}

	private void checkPuzzleSolved() {
		if (playerAnswer != null) {
			if (playerAnswer.equals(keyword)) {
				GameManager.getManagerFromInstance(PlayerManager.class).grantWizardSkill(WizardSkills.ICEBALL);
				spawnExitPortal();
				playerAnswer = null;
			} else {
				TundraDungeonAnnouncementDialog announcementDialog = new TundraDungeonAnnouncementDialog("Oops", skin);
				announcementDialog.show();
				playerAnswer = null;
			}
		}
	}

	private void handleTextDialog() {
		if (getPlayerEntity().getPosition().isCloseEnoughToBeTheSame(machineCoordinates) && !TundraDungeonTextDialog.isLocked()) {
			PlayerPeon playerPeon = (PlayerPeon) getPlayerEntity();
			if (playerPeon.getMovementTask() != null) {
				playerPeon.getMovementTask().stopTask();
			}
			TundraDungeonTextDialog textDialog = new TundraDungeonTextDialog(this, "Your answer", skin);
			textDialog.show();
			TundraDungeonTextDialog.lock();
		} else if (!getPlayerEntity().getPosition().isCloseEnoughToBeTheSame(machineCoordinates) && TundraDungeonTextDialog.isLocked()) {
			TundraDungeonTextDialog.release();
		}
	}

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
	 * @param box
	 */
	public void addDialogue(AbstractDialogBox box){ this.allTundraDungeonDialogs.add(box);}

	@Override
	public String getType(){ return "TundraDungeon"; }

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
