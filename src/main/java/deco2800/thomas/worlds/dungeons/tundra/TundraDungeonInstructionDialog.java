package deco2800.thomas.worlds.dungeons.tundra;

import com.badlogic.gdx.Gdx;

public class TundraDungeonInstructionDialog extends TundraDungeonDialog {
	final private static String TITLE = "Instruction";

	private static final int WIDTH = 1000;
	private static final int HEIGHT = 500;
	private static final int POS_X = Gdx.graphics.getWidth() / 2;
	private static final int POS_Y = Gdx.graphics.getHeight() / 2;

	private static boolean toShow = false;
	private static boolean lock = true;

	final private String[] instructions = new String[] {
			"To get out of this dungeon, you will need to obtain a secret key.",
			"The secret key is a meaningful English word.",
			"Letters of this word are hidden inside this dungeon. Move around to find them!",
			"Once you have obtained all the letters, rearrange these letters into a meaningful English word.",
			"After you have come up with the word, go to the encryption device and type it in."
	};

	private static int instructionCounter = 0;

	private TundraDungeonInstructionDialog() {
		super(TITLE, TundraDungeonInstructionDialog.skin);
		text(instructions[instructionCounter]);

		if (instructionCounter == 0) {
			button("Next", "Next");
			button("Skip", "Skip");
		} else if (instructionCounter == instructions.length - 1) {
			button("Back", "Back");
			button("Finish", "Finish");
		} else {
			button("Back", "Back");
			button("Next", "Next");
			button("Skip", "Skip");
		}
	}

	public static void launch() {
		toShow = true;
		instructionCounter = 0;
	}

	public static boolean isLocked() {
		return lock;
	}

	public static void lock() {
		lock = true;
	}

	public static void release() {
		lock = false;
	}

	public TundraDungeonDialog show() {
		return this.show(WIDTH, HEIGHT, POS_X, POS_Y);
	}

	public static boolean isToShow() {
		return toShow;
	}

	public static void showDialog() {
		TundraDungeonInstructionDialog dialog = new TundraDungeonInstructionDialog();
		dialog.show();
		toShow = false;
	}

	@Override
	protected void result(Object buttonText) {
		if (buttonText == "Skip" || buttonText == "Finish") {
			instructionCounter = instructions.length;
			toShow = false;
		} else if (buttonText == "Back") {
			instructionCounter--;
			toShow = true;
		} else if (buttonText == "Next") {
			instructionCounter++;
			toShow = true;
		}
	}
}
