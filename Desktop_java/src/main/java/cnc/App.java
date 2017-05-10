package cnc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import cnc.eagle.ADCOMP;
import cnc.editor.CompositeObject2d;
import cnc.editor.VectorObject2d;
import cnc.tools.ObjectViewer;
import cnc.tools.SVG;

public class App {

	public static void main(String[] args) throws IOException {

		
		CompositeObject2d stage = new CompositeObject2d();
		
		VectorObject2d pcb = null;
		
		for(int i = 0;i < 6;i++){
		
			pcb = ADCOMP.load(new File("res/data.adcomp"));
			
	
			pcb.setOriginToCenter();
			pcb.position.x = -2.7f + 4.1f*i;
			pcb.position.y = 6.f;
			
			pcb.updateTransformation();
	
			stage.add(pcb);
		}
		
		
		

		PrintWriter svgImage = new PrintWriter("res/image.svg");
		svgImage.println(SVG.getPath(stage.getCommands(), true));
		svgImage.close();

		//List<cnc.data.Package> packages = commands.stream().map((c) -> c.toPackage()).collect(Collectors.toList());

		// System.out.println(packages.size());
		//
		// try {
		// IOUtils.writePackagesAsText(System.out, packages);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }


		new ObjectViewer(stage);
		//new PathViewer(Optimizer.optiomaze(stage.getCommands()));

	}
}
