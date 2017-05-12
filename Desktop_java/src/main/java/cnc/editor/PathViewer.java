package cnc.editor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JFrame;

import cnc.commands.Command;

@SuppressWarnings("serial")
public class PathViewer extends Viewer {

	public static final boolean isDrawPath = true;

	public PathViewer(ObjectController controller) {
		super(controller);
		
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setTitle("Object Viewer");

	}
	
	public void update(){
		repaint();
	}
	
	public void setCommands(List<Command> commands) {
		this.commands = commands;
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
