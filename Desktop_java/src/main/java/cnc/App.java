package cnc;

import java.io.IOException;

import cnc.editor.ObjectController;
import cnc.objects2d.CompositeObject2d;

public class App {

	public static void main(String[] args) throws IOException {

		
		CompositeObject2d stage = new CompositeObject2d();
		
		new ObjectController().init(stage);
		
		

	}
}
