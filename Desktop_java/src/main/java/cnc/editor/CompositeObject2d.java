package cnc.editor;

import java.util.ArrayList;
import java.util.List;

import cnc.commands.Command;

public class CompositeObject2d extends Object2d {

	protected List<Object2d> objects;

	public CompositeObject2d() {
		objects = new ArrayList<>();
	}

	@Override
	protected List<Command> getRawCommands() {

		List<Command> result = new ArrayList<>();

		for (Object2d obj : objects)
			result.addAll(obj.getCommands());

		return result;
	}

	public void add(Object2d obj) {
		objects.add(obj);
	}
}
