package cnc.files.kicad;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cnc.commands.Builder;
import cnc.commands.Command;
import cnc.objects2d.VectorObject2d;
import cnc.plotUtils.Figures;
import cnc.tools.Optimizer;

public class HPGL {
	
	private static final float MAGIC_KOEF = 1f/40;
	private static Point2D.Float currentPosition = new Point2D.Float();
	
	public static VectorObject2d load(File file) throws IOException {
		
		currentPosition.setLocation(0, 0);
		
		String data = readToLine(file);
		String[] commands = data.split(";");
		Builder builder = new Builder();
		
		for(String command : commands){
			String[] parts = command.split("\\s|\\,");
			translate(parts, builder);
		}
		
		List<Command> rawCommands = builder.build();
		Optimizer.optiomaze(rawCommands);
		return new VectorObject2d(rawCommands);
	}
	
	
	private static void translate(String[] command, Builder builder){
		String c = command[0];
		if(c.equals("IN")) return;
		else if(c.startsWith("VS")) return;
		else if(c.startsWith("SP")) return;
		else if(c.equals("PU"))
			builder.draw(false);
		else if(c.equals("PD"))
			builder.draw(true);
		else if(c.equals("PA")){
			float x = 0;
			float y = 0;
			if(command.length == 3){
				x = Integer.parseInt(command[1]) * MAGIC_KOEF;
				y = Integer.parseInt(command[2]) * MAGIC_KOEF;
			}
			else if(command.length == 1){}
			else{
				System.out.println("ERR: " + Arrays.toString(command));
				return;
			}
			builder.move(x, y);
			currentPosition.setLocation(x, y);
			
		}
		else if(c.equals("CI"))
			builder.commands(Figures.circle(currentPosition, Integer.parseInt(command[1]) * MAGIC_KOEF));
		
		else
			System.out.println("C: " + Arrays.toString(command));
	}
	
	
	
	private static String readToLine(File file) throws IOException{
		StringBuffer buffer = new StringBuffer();
		try(BufferedReader reader = new BufferedReader(new FileReader(file))){
			String line;
			while((line = reader.readLine()) != null)
				buffer.append(line);
		}
		return buffer.toString();
	}
}
