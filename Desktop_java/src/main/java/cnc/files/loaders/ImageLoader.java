package cnc.files.loaders;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import cnc.Config;
import cnc.commands.Builder;
import cnc.commands.Command;
import cnc.objects2d.VectorObject2d;

public class ImageLoader extends ObjectLoader{

	public interface Binarizator {
		boolean[][] toBinary(BufferedImage image);
	}
	
	
	private Binarizator binarizator = new TrashholdBinarizator();
	
	
	@Override
	public VectorObject2d load(File file) throws IOException {
		
		// Load image
		BufferedImage image = ImageIO.read(file);
		// image -> binary
		boolean[][] binary = binarizator.toBinary(image);
		// binary -> commands
		List<Command> command = convertBinaryToCommands(binary);
		
		return new VectorObject2d(command);
	}


	private List<Command> convertBinaryToCommands(boolean[][] binary) {
		Builder builder = new Builder();
		int h = binary[0].length;
		float size = Config.drawerSize * 0.95f;
		Point p = new Point(0, 0);

		builder.draw(false);
		for(int y = 0;y < binary[0].length;y++){
			boolean line = false;
			
			for(int x = 0;x < binary.length;x++){
				boolean color = binary[x][h - y - 1];
				if(color){
					if(!line){ 
						//Line setup
						p.setLocation(x, y);
						line = true;
					}
				}
				else{
					if(line){
						//draw line
						
						builder.move(p.x * size,p.y * size).draw(true).move((x - 1) * size,y * size).draw(false);
						line = false;
					}
				}
			}
			
			if(line)
				builder.move(p.x * size,p.y * size).draw(true).move((binary.length - 1) * size,y * size).draw(false);
			
			
			
		}
		

		//builder.build().forEach(System.out::println);
		return builder.build();
	}
	
	
	
	
	class TrashholdBinarizator implements Binarizator{

		int val = 127;
		
		@Override
		public boolean[][] toBinary(BufferedImage image) {
			boolean[][] binary = new boolean[image.getWidth()][image.getHeight()];
			
			for(int x = 0;x < image.getWidth();x++)
				for(int y = 0;y < image.getHeight();y++){
					int blue = (image.getRGB(x, y) & 255);
					binary[x][y] = blue <= val;
				}
				
			
			return binary;
		}
		
	}
	
}
