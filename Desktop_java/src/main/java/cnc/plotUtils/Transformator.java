package cnc.plotUtils;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;

import cnc.commands.Command;
import cnc.commands.Move;

public class Transformator {

	public static void apply(AffineTransform t, List<Command> commands) {
		for (Command c : commands)
			if (c instanceof Move) {
				Move m = (Move) c;
				Point2D p = t.transform(new Point2D.Float(m.x, m.y), null);
				m.x = (float) p.getX();
				m.y = (float) p.getY();
			}
	}
}
