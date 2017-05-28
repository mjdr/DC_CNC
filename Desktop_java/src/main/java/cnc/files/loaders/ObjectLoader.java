package cnc.files.loaders;

import java.io.File;
import java.io.IOException;

import cnc.objects2d.VectorObject2d;

public abstract class ObjectLoader {
	public abstract VectorObject2d load(File file) throws IOException;	
	
}
