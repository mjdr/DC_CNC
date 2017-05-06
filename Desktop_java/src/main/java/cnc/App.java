package cnc;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cnc.commands.Command;
import cnc.editor.CompositeObject2d;
import cnc.editor.Text;
import cnc.plotUtils.CharPathGenerator;
import cnc.plotUtils.Transformator;

public class App {

	public static void main(String[] args) {

		List<Command> commands = new ArrayList<>();

		// commands.addAll(figures.line(new Point2D.Float(32, 10), new
		// Point2D.Float(21, 21), 3f));
		// commands.addAll(figures.fillCircle(new Point2D.Float(21, 21), 1.5f));
		// commands.addAll(figures.line(new Point2D.Float(21, 21), new
		// Point2D.Float(32, 32), 3f));

		List<Command> text = CharPathGenerator.generate("Hello");
		Transformator.apply(AffineTransform.getTranslateInstance(1, 25), text);
		// commands.addAll(text);
		List<Command> text2 = CharPathGenerator.generate("World");
		Transformator.apply(AffineTransform.getTranslateInstance(1, 5), text2);
		// commands.addAll(text2);

		// Object2d stage = new Line(new Point2D.Float(0, 0), new
		// Point2D.Float(21, 21), 1f);
		CompositeObject2d stage = new CompositeObject2d();

		Text hello = new Text("hello");

		hello.position.x = 1;
		hello.position.y = 15;

		hello.setOriginToCenter();
		hello.rotation = 0f;

		Text hello2 = new Text("hello");

		hello2.position.x = 1;
		hello2.position.y = 15;

		hello2.setOriginToCenter();
		hello2.rotation = 3.141592f / 2f;

		stage.add(hello);
		stage.add(hello2);

		Text world = new Text("world");
		world.position.x = 1;
		world.position.y = 5;
		// stage.add(world);

		commands.addAll(stage.getCommands());

		List<cnc.data.Package> packages = commands.stream().map((c) -> c.toPackage()).collect(Collectors.toList());

		// System.out.println(packages.size());
		//
		// try {
		// IOUtils.writePackagesAsText(System.out, packages);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		new Viewer(stage);

	}
}
