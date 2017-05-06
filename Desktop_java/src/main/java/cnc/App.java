package cnc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cnc.commands.Command;
import cnc.eagle.ADCOMP;
import cnc.editor.CompositeObject2d;
import cnc.editor.Object2d;

public class App {

	public static void main(String[] args) throws IOException {

		List<Command> commands = new ArrayList<>();

		CompositeObject2d stage = new CompositeObject2d();

		Object2d pcb = ADCOMP.load(new File("res/data.adcomp"));

		pcb.setOriginToCenter();
		// pcb.rotation = 3.141592f / 4;
		pcb.position.y = 10;
		pcb.position.x = -5;

		stage.add(pcb);

		commands.addAll(stage.getCommands());

		PrintWriter svgImage = new PrintWriter("res/image.svg");
		svgImage.println(SVG.getPath(commands, true));
		svgImage.close();

		List<cnc.data.Package> packages = commands.stream().map((c) -> c.toPackage()).collect(Collectors.toList());

		// System.out.println(packages.size());
		//
		// try {
		// IOUtils.writePackagesAsText(System.out, packages);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		new Viewer(commands);

	}
}
