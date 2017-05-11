package cnc.eagle;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cnc.commands.Builder;
import cnc.objects2d.VectorObject2d;

public class ADCOMP {
	

	private static final float SCALER_STEPS_TO_INCH = 254;
	private static final float SCALER_INCH_TO_MM = 25.4f;
	private static final float SCALER_STEPS_TO_MM = 1f/SCALER_STEPS_TO_INCH * SCALER_INCH_TO_MM;
	
	private static boolean draw = false;

	public static VectorObject2d load(File file) throws IOException {

		setupParams();
		
		Builder builder = new Builder();

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			String command;
			while ((command = reader.readLine()) != null) 
				processCommand(builder, command);
		}

		return new VectorObject2d(builder.biuld());
	}
	
	private static void setupParams(){
		draw = false;
	}
	
	private static void processCommand(Builder builder,String command){
		switch (command.charAt(0)) {
			case 'C':break;
			case 'M':
				processMove(builder, command);
				break;
			case 'D':
				processDraw(builder, command);
				break;
				
			default:
				throw new IllegalArgumentException("Command: '" + command + "' is not defiend!");
				
		}
	}
	private static void processMove(Builder builder,String command){
		
		if (draw)
			builder.draw(draw = false);
		
		Point pos = parseCoords(command);
		builder.move(pos.x * SCALER_STEPS_TO_MM, pos.y * SCALER_STEPS_TO_MM);
	}
	private static void processDraw(Builder builder,String command){
		if (!draw)
			builder.draw(draw = true);
		Point pos = parseCoords(command);
		builder.move(pos.x * SCALER_STEPS_TO_MM, pos.y * SCALER_STEPS_TO_MM);
	}
	
	
	private static Point parseCoords(String command){
		String[] data = command.substring(1).split(",");
		
		return new Point(
				Integer.parseInt(data[0]),
				Integer.parseInt(data[1])
		);
	}

}
