package cnc.commands;

import cnc.data.Package;

public class Draw extends Command {

	private boolean value;

	public Draw(boolean value) {
		super();
		this.value = value;
	}

	@Override
	public cnc.data.Package toPackage() {
		return value ? new cnc.data.Package(Package.TYPE_RELATIVE, (short) 0, (short) 0, (short) 1)
				: new cnc.data.Package(Package.TYPE_RELATIVE, (short) 0, (short) 0, (short) 0);
	}

	public boolean getValue() {
		return value;
	}

}
