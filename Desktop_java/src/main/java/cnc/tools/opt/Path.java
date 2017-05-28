package cnc.tools.opt;

import java.util.ArrayList;
import java.util.List;

import cnc.commands.Builder;
import cnc.commands.Command;

public class Path {

	public PathPoint p0;
	public PathPoint p1;
	public boolean reverse;
	
	private List<PathPoint> drawPoints;
	
	
	public Path() {
		drawPoints = new ArrayList<PathPoint>();
	}
	
	
	public void addPoint(float x, float y){
		drawPoints.add(new PathPoint(x, y));
	}
	
	public List<Command> getCommands(){
		if(reverse)
			return getCommandsBack();
		return getCommandsFor();
	}
	private List<Command> getCommandsFor(){
		Builder builder = new Builder();
		builder.draw(false);
		builder.move(p0);
		
		builder.draw(true);
		
		for(PathPoint p : drawPoints)
			builder.move(p);
		
		builder.draw(false);
		
		return builder.build();
			
	}
	
	private List<Command> getCommandsBack(){
		Builder builder = new Builder();
		builder.draw(false);
		builder.move(p1);
		
		builder.draw(true);
		
		for(int i = drawPoints.size() - 1;i >= 0;i--)
			builder.move(drawPoints.get(i));
		
		builder.draw(false);
		
		return builder.build();
			
	}
	
	public PathPoint getStartPoint(){
		return reverse ? p1 : p0;
	}
	public PathPoint getEndPoint(){
		return reverse ? p0 : p1;
	}
	
	
	public class PathPoint {
		public float x;
		public float y;
		
		public PathPoint(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		public float distance(PathPoint point) {
			return (float)Math.sqrt((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y));
		}
		
		public Path getPath(){
			return Path.this;
		}
		
	}
}
