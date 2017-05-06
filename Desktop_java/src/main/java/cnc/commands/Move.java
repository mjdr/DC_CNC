package cnc.commands;

public class Move extends Command {

	public static final float STEP_SIZE = 0.2f; // mm

	public float x, y;

	public Move(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public cnc.data.Package toPackage() {
		return new cnc.data.Package(cnc.data.Package.TYPE_ABSOLUTE,
				(short) (x / STEP_SIZE),
				(short) (y / STEP_SIZE),
				(short) 1);
	}

}
