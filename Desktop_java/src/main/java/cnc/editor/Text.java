package cnc.editor;

import java.util.List;

import cnc.commands.Command;
import cnc.plotUtils.CharPathGenerator;

public class Text extends Object2d {

	private String text;

	public Text(String text) {
		this.text = text;
	}

	@Override
	protected List<Command> getRawCommands() {
		return CharPathGenerator.generate(text);
	}

}
