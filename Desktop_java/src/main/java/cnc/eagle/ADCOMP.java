package cnc.eagle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cnc.commands.Builder;
import cnc.editor.VectorObject2d;

public class ADCOMP {

	public static VectorObject2d load(File file) throws IOException {

		Builder builder = new Builder();

		float scaler = 0.04f;

		boolean draw = false;

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.charAt(0) == 'C') {
					continue;
				}
				if (line.charAt(0) == 'M') {
					if (draw)
						builder.draw(draw = false);
					String[] data = line.substring(1).split(",");
					float x = Float.parseFloat(data[0]);
					float y = Float.parseFloat(data[1]);
					builder.move(x * scaler, y * scaler);
					continue;
				}
				if (line.charAt(0) == 'D') {
					if (!draw)
						builder.draw(draw = true);
					String[] data = line.substring(1).split(",");
					float x = Float.parseFloat(data[0]);
					float y = Float.parseFloat(data[1]);
					builder.move(x * scaler, y * scaler);
					continue;
				}

			}

		}

		return new VectorObject2d(builder.biuld());
	}

}
