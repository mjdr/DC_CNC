package cnc;

import java.io.File;
import java.io.IOException;

import cnc.editor.ObjectController;
import cnc.files.PCBPreparer;
import cnc.files.loaders.HPGLLoader;
import cnc.files.loaders.ImageLoader;
import cnc.objects2d.CompositeObject2d;
import cnc.objects2d.VectorObject2d;

public class App {

	public static void main(String[] args) throws IOException {

		
		
		CompositeObject2d stage = new CompositeObject2d();
		
		//VectorObject2d hpgl = new ImageLoader().load(new File("~/"));
		
		//PCBPreparer.prepare(hpgl);
		//stage.add(hpgl);
		
		
		
		new ObjectController().init(stage);
		
		

	}
}
