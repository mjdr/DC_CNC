package cnc.commands;

public abstract class Command {

	public abstract cnc.data.Package toPackage();
	public abstract Command copy();
}
