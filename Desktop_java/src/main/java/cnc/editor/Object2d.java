package cnc.editor;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import cnc.commands.Command;
import cnc.commands.Move;
import cnc.plotUtils.Transformator;

public abstract class Object2d {

	protected AffineTransform transform;
	protected AffineTransform projection;

	public Rectangle2D.Float bound;

	public Point2D.Float position;
	public Point2D.Float origin;
	public Point2D.Float scale;
	public float rotation;

	protected abstract List<Command> getRawCommands();

	public Object2d() {
		transform = AffineTransform.getTranslateInstance(0, 0);
		projection = AffineTransform.getTranslateInstance(0, 0);
		bound = new Rectangle2D.Float(0, 0, 0, 0);
		position = new Point2D.Float(0, 0);
		origin = new Point2D.Float(0, 0);
		scale = new Point2D.Float(1, 1);
		rotation = 0;
	}

	public List<Command> getCommands() {
		List<Command> commands = getRawCommands();
		updateTransformation();
		Transformator.apply(transform, commands);
		Transformator.apply(projection, commands);
		return commands;
	}

	public void updateBoundaries() {
		float minX = Float.MAX_VALUE;
		float minY = Float.MAX_VALUE;
		float maxX = Float.MIN_VALUE;
		float maxY = Float.MIN_VALUE;

		List<Command> commands = getRawCommands();
		for (Command c : commands)
			if (c instanceof Move) {
				Move m = (Move) c;

				if (m.x < minX)
					minX = m.x;
				if (m.x > maxX)
					maxX = m.x;
				if (m.y < minY)
					minY = m.y;
				if (m.y > maxY)
					maxY = m.y;

			}
		bound.x = minX;
		bound.y = minY;
		bound.width = maxX - minX;
		bound.height = maxY - minY;
	}

	public void updateTransformation() {
		AffineTransform tr = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		AffineTransform sc = AffineTransform.getScaleInstance(scale.getX(), scale.getY());

		AffineTransform res = AffineTransform.getScaleInstance(1, 1);
		res.rotate(rotation, origin.x, origin.y);

		transform = res;

		res = AffineTransform.getScaleInstance(1, 1);
		res.concatenate(sc);
		res.concatenate(tr);
		projection = res;

	}

	public void setOriginToCenter() {
		updateBoundaries();
		origin.x = bound.width / 2;
		origin.y = bound.height / 2;
		updateTransformation();
	}

	public AffineTransform getTransform() {
		return transform;
	}

}
