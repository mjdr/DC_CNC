package cnc.editor;

import static cnc.Config.drawerSize;
import static cnc.Config.pixelPerMM;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cnc.objects2d.CompositeObject2d;
import cnc.objects2d.Object2d;
import cnc.tools.Optimizer;

@SuppressWarnings("serial")
public class ObjectViewer extends Viewer{
	private Object2d object;
	
	private Map<Path2D, Object2d> boxes;
	private Object2d current;
	private Object2d drag;
	private Point dragPointOnScreen;
	private Point2D.Float dragStart;
	private PathViewer pathViewer;
	
	
	public ObjectViewer(Object2d object) {
		super(object.getCommands());
		pathViewer = new PathViewer(commands);
		this.object = object;
		boxes = new HashMap<Path2D, Object2d>();
		dragStart = new Point2D.Float();
		
		updateStage();
		
		openWindow();

	}
	
	
	@Override
	public void addNotify() {
		super.addNotify();
		setFocusable(true);
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				ObjectViewer.this.mouseMoved(e.getX(),e.getY());
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				ObjectViewer.this.mouseDragged(e.getX(),e.getY());
			}

			
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ObjectViewer.this.mousePressed(e.getX(),e.getY());
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ObjectViewer.this.mouseReleased();
			}
		});
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				ObjectViewer.this.keyPressed(e.getKeyCode());
			}
		});
	}
	
	
	protected void keyPressed(int code) {
		if(code == KeyEvent.VK_R && current != null){
			current.rotation += 3.141592f/2;
			updateStage();
			updatePathViewer();
		}
		if(code == KeyEvent.VK_P){
			pathViewer.openWindow();
		}
		
	}


	protected void mouseDragged(int x, int y) {
		if(drag == null) return;

		float dx = (x - dragPointOnScreen.x) / pixelPerMM;
		float dy = (y - dragPointOnScreen.y) / pixelPerMM;

		drag.position.x = dragStart.x + dx;
		drag.position.y = dragStart.y - dy;

		updateStage();
		
	}


	protected void mouseReleased() {
		if(drag != null){
			updateBoxes();
			updatePathViewer();
			drag = null;
		}
	}


	protected void mousePressed(int x, int y) {
		if(current != null){
			drag = current;
			dragPointOnScreen = new Point(x, y);
			dragStart.setLocation(current.position);
			
			updateStage();
		}
			
	}
	
	private void updatePathViewer(){
		pathViewer.setCommands(Optimizer.optiomaze(object.getCommands()));
		pathViewer.update();
	}
	
	private void updateStage(){
		object.updateTransformation();
		object.updateBoundaries();
		commands = object.getCommands();
		updateBoxes();
		repaint();
	}

	private void mouseMoved(int x, int y) {
		
		for(Entry<Path2D, Object2d> entry : boxes.entrySet()){
			if(entry.getKey().contains(x, y)){
				current = entry.getValue();
				repaint();
				return;
			}
		}
		
		current = null;
		
		repaint();
		
	}
	

	@Override
	public void paint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		setupGraphicsStuff(g2d);
		drawBackground(g2d);
		drawCommands(g2d);

		drawAllBounds(g2d, object);

	}
	
	private void drawAllBounds(Graphics2D g2d, Object2d object2d){
		drawBound(g2d, object2d);
		if(!(object2d instanceof CompositeObject2d))
			return;
		for(Object2d obj : ((CompositeObject2d)object2d).getChildren())
			drawAllBounds(g2d, obj);
		
	}
	
	private void drawBound(Graphics2D g2d, Object2d object2d){

		
		g2d.setColor(new Color(1, 1, 1, .1f));
		AffineTransform t = new AffineTransform(toScreen);

		t.concatenate(object2d.getTransform());
		
		Rectangle2D.Float b = object2d.bound;
		Rectangle2D.Float rect = new Rectangle2D.Float(b.x - drawerSize/2, b.y - drawerSize/2, b.width + drawerSize, b.height + drawerSize);
		
		g2d.fill(t.createTransformedShape(rect));
		if(object2d == drag)
			g2d.setColor(Color.green);
		else if(object2d == current)
			g2d.setColor(Color.red);
		else
			g2d.setColor(Color.white);
		
		g2d.draw(t.createTransformedShape(rect));
		
		
		
		
	}
	
	
	private void updateBoxes(){
		boxes.clear();
		updateBoxesParent(object);
	}
	
	private void updateBoxesParent(Object2d object2d){
		if(!(object2d instanceof CompositeObject2d)){
			
			
			AffineTransform t = new AffineTransform(toScreen);
			t.concatenate(object2d.getTransform());
			
			boxes.put((Path2D)t.createTransformedShape(object2d.bound), object2d);
		}
		else
			for(Object2d obj : ((CompositeObject2d)object2d).getChildren())
				updateBoxesParent(obj);
	}
	
	
	
	

}
