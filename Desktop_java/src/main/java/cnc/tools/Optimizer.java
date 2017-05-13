package cnc.tools;

import java.util.List;

import cnc.commands.Command;
import cnc.commands.Draw;
import cnc.commands.Move;

public class Optimizer {
	public static List<Command> optiomaze(List<Command> commands){
		
		boolean draw = false;
		
		for(int i = 0;i < commands.size() - 1;i++){
			Command c0 = commands.get(i);
			Command c1 = commands.get(i + 1);
			
			if(c0 instanceof Draw){
				draw = ((Draw) c0).getValue();
				continue;
			}
			if(
					!draw &&
					(c0 instanceof Move) &&
					(c1 instanceof Move)
					){
				commands.remove(i);
				i--;
			}
		}
		
		
		while(!draw && commands.get(commands.size()-1) instanceof Move){
			commands.remove(commands.size()-1);
		}
		
		return commands;
	}
}
