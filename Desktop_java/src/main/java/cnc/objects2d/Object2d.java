package cnc.objects2d;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import cnc.commands.Command;
import cnc.commands.Draw;
import cnc.commands.Move;
import cnc.plotUtils.Transformator;

public abstract class Object2d {

	
	protected Object2d parent;
	protected AffineTransform transform;
	protected AffineTransform projection;
	protected AffineTransform combiend;
	protected boolean scalable = true;

	public Rectangle2D.Float bound;

	public Point2D.Float position;
	public Point2D.Float origin;
	public Point2D.Float scale;
	public float rotation;

	protected abstract List<Command> getRawCommands();

	public Object2d() {
		this(null);
	}
	
	public Object2d(Object2d parent) {
		transform = AffineTransform.getTranslateInstance(0, 0);
		projection = AffineTransform.getTranslateInstance(0, 0);
		bound = new Rectangle2D.Float(0, 0, 0, 0);
		position = new Point2D.Float(0, 0);
		origin = new Point2D.Float(0, 0);
		scale = new Point2D.Float(1, 1);
		rotation = 0;
	}
	
	public void setParent(Object2d parent) {
		this.parent = parent;
	}
	
	public Object2d getParent() {
		return parent;
	}
	

	public List<Command> getCommands() {
		List<Command> commands = getRawCommands();
		updateTransformation();
		Transformator.apply(combiend, commands);
		return commands;
	}

	public void updateBoundaries() {
		float minX = 1e5f;
		float minY = 1e5f;
		float maxX = 0;
		float maxY = 0;

		List<Command> commands = getRawCommands();
		boolean draw = false;
		for (Command c : commands)
			if (c instanceof Move && draw) {
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
			else if(c instanceof Draw){
				draw = ((Draw) c).getValue();
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
		res.rotate(rotation,origin.getX(),origin.getY());
		transform = res;

		res = AffineTransform.getScaleInstance(1, 1);
		if(scalable)
			res.concatenate(sc);
		res.concatenate(tr);
		projection = res;
		
		combiend = new AffineTransform();
		combiend.concatenate(projection);
		combiend.concatenate(transform);
	}

	public void setOriginToCenter() {
		updateBoundaries();
		updateTransformation();
		
		
		if(parent != null)
			parent.updateTransformation();
		origin.x = bound.x + bound.width / 2;
		origin.y = bound.y + bound.height / 2;
		
		if(parent != null)
			updateTransformation();
	}
	
	public AffineTransform getTransform() {
		return combiend;
	}
	
	public boolean isScalable() {
		return scalable;
	}
	public void setScalable(boolean scalable) {
		this.scalable = scalable;
	}

}
