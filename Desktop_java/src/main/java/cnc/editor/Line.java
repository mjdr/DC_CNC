package cnc.editor;

import java.awt.geom.Point2D;
import java.util.List;

import cnc.commands.Command;
import cnc.plotUtils.Figures;

public class Line extends Object2d {

	public Point2D.Float start, end;
	public float height;

	public Line(Point2D.Float start, Point2D.Float end, float height) {
		this();
		this.start = start;
		this.end = end;
		this.height = height;

	}

	public Line() {
		super();
	}

	@Override
	protected List<Command> getRawCommands() {
		return Figures.line(start, end, height);
	}

}
