package cnc.objects2d;

import java.util.List;
import java.util.stream.Collectors;

import cnc.commands.Command;

public class VectorObject2d extends Object2d {

	protected List<Command> commands;

	public VectorObject2d(List<Command> commands) {
		this.commands = commands;
	}

	@Override
	protected List<Command> getRawCommands() {
		return commands.stream().map((c)->c.copy()).collect(Collectors.toList());
	}

}
