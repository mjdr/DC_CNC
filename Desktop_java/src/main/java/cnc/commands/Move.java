package cnc.commands;

import cnc.Config;

public class Move extends Command {

	public float x, y;

	public Move(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public cnc.data.Package toPackage() {
		return new cnc.data.Package(cnc.data.Package.TYPE_ABSOLUTE,
				(short) (x / Config.STEP_SIZE),
				(short) (y / Config.STEP_SIZE),
				(short) 1);
	}
	
	@Override
	public String toString() {
		return String.format("move(%.3f,%.3f)",x,y);
	}

	@Override
	public Command copy() {
		return new Move(x,y);
	}

}
