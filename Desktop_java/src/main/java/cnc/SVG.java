package cnc;

import java.util.List;
import java.util.Locale;

import cnc.commands.Command;
import cnc.commands.Draw;
import cnc.commands.Move;

public class SVG {

	public static String getPath(List<Command> commands, boolean hidden) {

		float width = 42; // mm
		float height = 42; // mm
		float stroke = 0.01f;

		boolean draw = false;
		StringBuffer sb = new StringBuffer();

		sb.append(
				String.format(Locale.ENGLISH,
						"<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"1000px\" height=\"1000px\" viewBox=\"0 0 %f %f\">\n",
						width, height));

		sb.append(String.format(Locale.ENGLISH,
				"\t<path stroke=\"#333333\" stroke-width=\"%f\" fill=\"#333333\" d=\"\n", stroke));
		sb.append(String.format(Locale.ENGLISH, "\t\tM 0 0 L 0 %f L %f %f L %f 0 L 0 0 \n", height, width, height,
				width));
		sb.append("\t\t\" />\n");

		sb.append(String.format(Locale.ENGLISH,
				"\t<path stroke=\"rgb(200, 150, 100)\" stroke-width=\"%f\" fill=\"none\" d=\"\n", stroke));

		for (Command c : commands) {
			if (c instanceof Draw)
				draw = ((Draw) c).getValue();
			else if (c instanceof Move) {
				Move m = (Move) c;

				sb.append(String.format(Locale.ENGLISH, "\t\t%s %f %f\n", draw ? "L" : "M", m.x, 42 - m.y));
			}
		}

		sb.append("\t\t\" />\n");

		sb.append(String.format(Locale.ENGLISH,
				"\t<path stroke=\"rgba(70, 100, 200, 0.6)\" stroke-width=\"%f\" fill=\"none\" d=\"\n", stroke));
		sb.append(String.format(Locale.ENGLISH, "\t\tM 0 %f\n", height));

		draw = false;
		for (Command c : commands) {
			if (c instanceof Draw)
				draw = ((Draw) c).getValue();
			else if (c instanceof Move) {
				Move m = (Move) c;

				sb.append(String.format(Locale.ENGLISH, "\t\t%s %f %f\n", draw ? "M" : "L", m.x, 42 - m.y).replace(",",
						"."));
			}
		}

		sb.append("\t\t\" />\n</svg>");

		return sb.toString();

	}
}
