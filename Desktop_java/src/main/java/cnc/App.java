package cnc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import cnc.eagle.ADCOMP;
import cnc.editor.ObjectViewer;
import cnc.objects2d.CompositeObject2d;
import cnc.objects2d.VectorObject2d;
import cnc.tools.SVG;

public class App {

	public static void main(String[] args) throws IOException {

		
		CompositeObject2d stage = new CompositeObject2d();
		VectorObject2d pcb = ADCOMP.load(new File("res/data.adcomp"));
		pcb.setOriginToCenter();
		
		stage.add(pcb);
		
		pcb = ADCOMP.load(new File("res/data.adcomp"));
		pcb.setOriginToCenter();
		
		stage.add(pcb);
		

		PrintWriter svgImage = new PrintWriter("res/image.svg");
		svgImage.println(SVG.getPath(stage.getCommands(), true));
		svgImage.close();
		


		new ObjectViewer(stage);
		
		

	}
}
