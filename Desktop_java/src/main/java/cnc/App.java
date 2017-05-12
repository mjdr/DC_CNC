package cnc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import cnc.eagle.ADCOMP;
import cnc.eagle.PCBPreparer;
import cnc.editor.ObjectController;
import cnc.editor.ObjectViewer;
import cnc.objects2d.CompositeObject2d;
import cnc.objects2d.VectorObject2d;
import cnc.tools.SVG;

public class App {

	public static void main(String[] args) throws IOException {

		
		CompositeObject2d stage = new CompositeObject2d();
		
		new ObjectController().init(stage);
		
		

	}
}
