package cnc.tools;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.List;

import cnc.commands.Command;

@SuppressWarnings("serial")
public class PathViewer extends Viewer {

	public static final boolean isDrawPath = true;

	public PathViewer(List<Command> commands) {
		super(commands);
		this.commands = commands;

	}
	
	@Override
	protected void tick(ActionEvent e){
		//repaint();
	}
	
	

	@Override
	public void paint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		setupGraphicsStuff(g2d);
		drawBackground(g2d);
		drawCommands(g2d);
		if(isDrawPath)
			drawPath(g2d);

	}
}
