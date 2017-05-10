package cnc.plotUtils;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;

import cnc.commands.Command;
import cnc.commands.Move;

public class Transformator {

	public static void apply(AffineTransform t, List<Command> commands) {
		Point2D.Float tmp = new Point2D.Float();
		
		for (Command c : commands)
			if (c instanceof Move) {
				Move m = (Move) c;
				t.transform(new Point2D.Float(m.x, m.y), tmp);
				m.x = (float) tmp.getX();
				m.y = (float) tmp.getY();
			}
	}
}
