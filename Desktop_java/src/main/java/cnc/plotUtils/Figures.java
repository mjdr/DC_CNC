package cnc.plotUtils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import cnc.commands.Builder;
import cnc.commands.Command;

public class Figures {

	private static float drawerSize = .5f;
	private static float lineFillRate = 0.3f;
	private static float circleFillRate = 0.3f;

	public static List<Command> line(Point2D.Float... points) {

		if (points.length < 2)
			throw new IllegalArgumentException("Points must by >= 2");

		Builder builder = new Builder();

		builder
				.move(points[0])
				.draw(true);

		for (int i = 1; i < points.length; i++)
			builder.move(points[i]);

		builder.draw(false);
		return builder.build();

	}

	public static List<Command> line(float x1, float y1, float x2, float y2) {
		return line(new Point2D.Float(x1, y1), new Point2D.Float(x2, y2));
	}

	public static List<Command> circle(Point2D.Float center, float radius) {
		float presition = .2f;

		// L = 2 * pi * r
		// pr = 2 * pi / n * r
		// n = 2 * pi * r / pr
		int steps = (int) (2 * Math.PI * radius / presition) + 1;

		Builder builder = new Builder();

		builder.move(center.x + radius, center.y).draw(true);

		for (int i = 1; i < steps; i++) {
			float angle = (float) (2 * Math.PI / steps * i);
			builder.move(center.x + (float) Math.cos(angle) * radius, center.y + (float) Math.sin(angle) * radius);
		}

		builder.move(center.x + radius, center.y).draw(false);

		return builder.build();

	}

	public static List<Command> fillCircle(Point2D.Float center, float minRadius, float maxRadius) {
		List<Command> commands = new ArrayList<Command>();

		for (float radius = maxRadius - drawerSize / 2; radius >= minRadius + drawerSize / 2; radius -=
				drawerSize / 2 + drawerSize / 2 * circleFillRate)
			commands.addAll(circle(center, radius));

		commands.addAll(circle(center, minRadius + drawerSize / 2));

		return commands;
	}

	public static List<Command> fillCircle(Point2D.Float center, float radius) {
		return fillCircle(center, 0, radius);
	}

	public static List<Command> line(Point2D.Float p1, Point2D.Float p2, float height) {

		float a = p1.y - p2.y;
		float b = p2.x - p1.x;
		float len = (float) Math.hypot(a, b);

		float nx = a / len;
		float ny = b / len;

		List<Command> cmds = new ArrayList<>();

		float h0 = height / 2 - drawerSize / 2;
		float h1 = -height / 2 + drawerSize / 2;

		cmds.addAll(
				line(p1.x + nx * h0, p1.y + ny * h0, p2.x + nx * h0, p2.y + ny * h0));
		cmds.addAll(
				line(p1.x - nx * h0, p1.y - ny * h0, p2.x - nx * h0, p2.y - ny * h0));

		int n = 0;
		for (float h = h0; h >= h1; h -= drawerSize / 2 + drawerSize / 2 * lineFillRate, n++)
			if (n % 2 == 0)
				cmds.addAll(
						line(p1.x + nx * h, p1.y + ny * h, p2.x + nx * h, p2.y + ny * h));
			else cmds.addAll(
					line(p2.x + nx * h, p2.y + ny * h, p1.x + nx * h, p1.y + ny * h));

		if (n % 2 == 0)
			cmds.addAll(
					line(p1.x - nx * h1, p1.y - ny * h1, p2.x - nx * h1,
							p2.y - ny * h1));
		else
			cmds.addAll(
					line(p2.x - nx * h1, p2.y - ny * h1, p1.x - nx * h1, p1.y - ny * h1));

		return cmds;

	}

}
