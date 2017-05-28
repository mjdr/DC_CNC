package cnc.commands;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import cnc.tools.opt.Path.PathPoint;

public class Builder {

	private List<Command> commands;

	public Builder() {
		commands = new ArrayList<>();
	}

	public Builder move(Point2D.Float point) {
		return move(point.x, point.y);
	}
	public Builder move(PathPoint point) {
		return move(point.x, point.y);
	}

	public Builder move(float x, float y) {
		commands.add(new Move(x, y));
		return this;
	}

	public Builder draw(boolean value) {
		commands.add(new Draw(value));
		return this;
	}
	public Builder commands(List<Command> commands){
		this.commands.addAll(commands);
		return this;
	}

	public List<Command> build() {
		return commands;
	}

}
