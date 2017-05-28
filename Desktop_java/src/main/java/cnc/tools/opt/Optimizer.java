package cnc.tools.opt;

import java.util.ArrayList;
import java.util.List;

import cnc.commands.Command;
import cnc.commands.Draw;
import cnc.commands.Move;
import cnc.tools.opt.Path.PathPoint;

public class Optimizer {
	
	private static PathParser pathParser = new PathParser();
	
	public static List<Command> optiomaze(List<Command> commands){
		
		

		List<Path>paths = pathParser.parse(commands);
		
		
		
		
		List<Command> optCommands = new ArrayList<>();
		
		configurePaths(paths);
		for(Path p : paths)
			optCommands.addAll(p.getCommands());
		

		removeDoubleDrawFalse(optCommands);
		
		return optCommands;
	}
	
	private static void configurePaths(List<Path> paths) {
		for(int i = 0;i < paths.size() - 1;i++){
			PathPoint min = minDist(paths, i);
			if(min == null) continue;
			Path minP = min.getPath();
			if(min == minP.p1)
				minP.reverse = true;
			int minPIndex = paths.indexOf(minP);
			paths.set(minPIndex, paths.get(i + 1));
			paths.set(i + 1, minP);
			
		}
	}
	
	private static PathPoint minDist(List<Path> paths, int index){
		float minDist = 1e10f;
		PathPoint point = null;
		PathPoint lastEnd = paths.get(index).getEndPoint();
		
		for(int i = index + 1;i < paths.size();i++){
			Path path = paths.get(i);
			float d = path.p0.distance(lastEnd);
			if(d < minDist){
				minDist = d;
				point = path.p0;
			}
			d = path.p1.distance(lastEnd);
			if(d < minDist){
				minDist = d;
				point = path.p1;
			}
		}
		
		return point;
	}





	private static void removeDoubleDrawFalse(List<Command> commands){
		for(int i = 1;i < commands.size();i++){
			Command c0 = commands.get(i - 1);
			Command c1 = commands.get(i);
			if(!(c0 instanceof Draw && c1 instanceof Draw)) continue;
			Draw d0 = (Draw) c0;
			Draw d1 = (Draw) c1;
			if(!d0.getValue() && !d1.getValue())
				commands.remove(i);
		}
	}
}
