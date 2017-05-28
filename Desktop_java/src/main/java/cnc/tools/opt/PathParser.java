package cnc.tools.opt;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import cnc.commands.Command;
import cnc.commands.Draw;
import cnc.commands.Move;

public class PathParser {
	
	public List<Path> parse(List<Command> commands){
		
		List<Path> result = new ArrayList<>();
		Path current = null;
		Point2D.Float pos = new Point2D.Float(0, 0);
		
		
		for(Command c : commands){
			
			//draw true
			if(c instanceof Draw && ((Draw)c).getValue()){
				if(current == null){
					current = new Path();
					current.p0 = current.new PathPoint(pos.x, pos.y);
					current.addPoint(pos.x, pos.y);
				}
			}
			//draw false
			else if(c instanceof Draw && !((Draw)c).getValue()){
				if(current != null){
					current.addPoint(pos.x, pos.y);
					current.p1 = current.new PathPoint(pos.x, pos.y);
					result.add(current);
					current = null;
				}
			}
			//move
			else if(c instanceof Move){
				Move m = (Move) c;
				if(current != null)
					current.addPoint(m.x, m.y);
				
				pos.setLocation(m.x, m.y);
			}
			
			
			
		}
		
		
		return result;
		
	}
}
