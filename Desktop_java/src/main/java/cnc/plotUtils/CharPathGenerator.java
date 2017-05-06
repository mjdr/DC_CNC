package cnc.plotUtils;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import cnc.commands.Command;
import cnc.commands.Draw;
import cnc.commands.Move;

public class CharPathGenerator {

	private static BufferedImage tmp;
	private static Graphics2D g2d;

	static {
		tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) tmp.getGraphics();
	}

	public static List<Command> generate(String string) {
		return generate(string, new Font("Consolas", Font.PLAIN, 15));
	}

	public static List<Command> generate(String string, Font font) {
		FontRenderContext frc = g2d.getFontRenderContext();

		List<Command> cmds = new ArrayList<>();

		GlyphVector gv = font.createGlyphVector(frc, string);

		Shape shape = gv.getOutline();

		PathIterator iter = shape.getPathIterator(AffineTransform.getScaleInstance(1, 1), .01);

		boolean draw = false;
		while (!iter.isDone()) {
			float[] coords = new float[2];

			int r = iter.currentSegment(coords);

			if (r == PathIterator.SEG_CLOSE && draw) {
				cmds.add(new Draw(draw = false));

			} else if (r == PathIterator.SEG_MOVETO) {
				if (draw)
					cmds.add(new Draw(draw = false));
				cmds.add(new Move(coords[0], -coords[1]));
			} else {
				if (!draw)
					cmds.add(new Draw(draw = true));
				cmds.add(new Move(coords[0], -coords[1]));
			}

			iter.next();
		}

		return cmds;
	}

}
