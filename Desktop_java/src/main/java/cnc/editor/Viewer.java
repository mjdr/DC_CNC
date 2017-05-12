package cnc.editor;

import static cnc.Config.drawerSize;
import static cnc.Config.heightMM;
import static cnc.Config.pixelPerMM;
import static cnc.Config.widthMM;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

import cnc.commands.Command;
import cnc.commands.Draw;
import cnc.commands.Move;
import cnc.objects2d.CompositeObject2d;
import cnc.objects2d.Object2d;

@SuppressWarnings("serial")
public abstract class Viewer extends JComponent {	
	
	public static final int S_WIDTH = (int) (widthMM * pixelPerMM);
	public static final int S_HEIGHT = (int) (heightMM * pixelPerMM);
	public static final AffineTransform toScreen;
	
	

	protected ObjectController controller;
	protected JFrame frame = new JFrame("Viewer");
	protected List<Command> commands;

	static {
		toScreen = (AffineTransform.getScaleInstance(pixelPerMM, -pixelPerMM));
		toScreen.concatenate(AffineTransform.getTranslateInstance(0, -S_HEIGHT / pixelPerMM));
	}
	
	public Viewer(ObjectController controller) {
		this.controller = controller;
		this.commands = controller.getRoot().getCommands();
		
		setupFrame();
	}
	
	protected void openWindow(){
		frame.pack();
		frame.setVisible(true);
	}
	
	private void setupFrame(){
		

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().add(this);
	}
	

	@Override
	public void addNotify() {
		super.addNotify();
		setPreferredSize(new Dimension(S_WIDTH, S_HEIGHT));
	}

	
	protected void drawBackground(Graphics2D g2d){
		g2d.setColor(new Color(51, 51, 51));
		g2d.fillRect(0, 0, S_WIDTH, S_HEIGHT);
	}
	protected void setupGraphicsStuff(Graphics2D g2d){
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

	}
	protected void drawCommands(Graphics2D g2d){
		float cx = 0, cy = 0;
		boolean draw = false;
		for (Command c : commands) {

			if (c instanceof Move) {
				Move m = (Move) c;
				
				g2d.setColor(new Color(200, 150, 100, 255));
				if (draw)
					drawLine(g2d,drawerSize * pixelPerMM/2,cx * pixelPerMM, S_HEIGHT - cy * pixelPerMM,
							m.x * pixelPerMM,
							S_HEIGHT - m.y * pixelPerMM);
				cx = m.x;
				cy = m.y;
			} else if (c instanceof Draw) {
				draw = ((Draw) c).getValue();
			} else throw new RuntimeException("Class " + c.getClass().getName() + " not defined!");

		}
		
	}
	protected void drawPath(Graphics2D g2d){
		float cx = 0, cy = 0;
		boolean draw = false;
		for (Command c : commands) {

			if (c instanceof Move) {
				Move m = (Move) c;
				
				if (draw)
					g2d.setColor(new Color(255, 0, 0, 255));
				else
					g2d.setColor(new Color(70, 100, 200, 150));
					
				g2d.drawLine(
						(int)(cx * pixelPerMM), 
						(int)(S_HEIGHT - cy * pixelPerMM),
						(int)(m.x * pixelPerMM),
						(int)(S_HEIGHT - m.y * pixelPerMM)
				);
				
				cx = m.x;
				cy = m.y;
			} else if (c instanceof Draw) {
				draw = ((Draw) c).getValue();
			} else throw new RuntimeException("Class " + c.getClass().getName() + " not defined!");

		}
		
		
	
	}
	private void drawLine(Graphics2D g,float r, float x1,float y1,float x2,float y2){
		
		final float eps = 6; 
		
		float d = (float)Math.hypot(x2 - x1, y2 - y1);
		if(d < eps){
			g.fillOval((int)(x1 - r), (int)(y1 - r), (int)(2*r), (int)(2*r));
			return;
		}

		float dx = (x2 - x1);
		float dy = (y2 - y1);
		
		int n = (int)(d/eps);
		for(int i = 0;i <= n;i++) {
			
			float cx = x1 + (dx*i)/n;
			float cy = y1 + (dy*i)/n;
			
			
			g.fillOval((int)(cx - r), (int)(cy - r), (int)(2*r), (int)(2*r));

		}
		
	}
	protected void drawAllBounds(Graphics2D g2d, Object2d object2d){
		drawBound(g2d, object2d);
		if(!(object2d instanceof CompositeObject2d))
			return;
		for(Object2d obj : ((CompositeObject2d)object2d).getChildren())
			drawAllBounds(g2d, obj);
		
	}
	
	protected void drawBound(Graphics2D g2d, Object2d object2d){

		
		g2d.setColor(new Color(1, 1, 1, .1f));
		AffineTransform t = new AffineTransform(toScreen);

		t.concatenate(object2d.getTransform());
		
		Rectangle2D.Float b = object2d.bound;
		Rectangle2D.Float rect = new Rectangle2D.Float(b.x - drawerSize/2, b.y - drawerSize/2, b.width + drawerSize, b.height + drawerSize);
		
		g2d.fill(t.createTransformedShape(rect));
		if(object2d == controller.getSelectedObject())
			g2d.setColor(Color.red);
		else if(object2d == controller.getDragObject())
			g2d.setColor(Color.green);
		
		else
			g2d.setColor(Color.white);
		
		g2d.draw(t.createTransformedShape(rect));
		
		
		
		
	}

}
