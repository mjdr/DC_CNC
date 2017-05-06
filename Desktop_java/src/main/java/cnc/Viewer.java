package cnc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;
import javax.swing.JFrame;

import cnc.commands.Command;
import cnc.commands.Draw;
import cnc.commands.Move;
import cnc.editor.Object2d;

public class Viewer extends JComponent {

	private static final long serialVersionUID = 1L;

	public static final float pixelPerMM = 18;

	public static final float widthMM = 42; // mm
	public static final float heightMM = 42; // mm
	public static final float drawerSize = .5f; // mm

	public static final int WIDTH = (int) (widthMM * pixelPerMM);
	public static final int HEIGHT = (int) (heightMM * pixelPerMM);

	public static final boolean draw = true;

	private AffineTransform toScreen = AffineTransform.getTranslateInstance(0, 0);

	private Object2d stage;

	public Viewer(Object2d stage) {
		this.stage = stage;

		JFrame frame = new JFrame("Viewer");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);

		toScreen = (AffineTransform.getScaleInstance(pixelPerMM, -pixelPerMM));
		toScreen.concatenate(AffineTransform.getTranslateInstance(0, -HEIGHT / pixelPerMM));

	}

	@Override
	public void addNotify() {
		super.addNotify();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setStroke(new BasicStroke(drawerSize * pixelPerMM));

		g2d.setColor(new Color(51, 51, 51));
		g2d.fillRect(0, 0, WIDTH, HEIGHT);

		float cx = 0, cy = 0;
		boolean draw = false;
		for (Command c : stage.getCommands()) {

			if (c instanceof Move) {
				Move m = (Move) c;
				if (draw)
					g2d.setColor(new Color(200, 150, 100, 255));
				else
					g2d.setColor(new Color(70, 100, 200, 150));
				if (draw || this.draw)
					g2d.drawLine((int) (cx * pixelPerMM), HEIGHT - (int) (cy * pixelPerMM),
							(int) (m.x * pixelPerMM),
							HEIGHT - (int) (m.y * pixelPerMM));
				cx = m.x;
				cy = m.y;
			} else if (c instanceof Draw) {
				draw = ((Draw) c).getValue();
			} else throw new RuntimeException("Class " + c.getClass().getName() + " not defined!");

		}

		g2d.setStroke(new BasicStroke(1));

		AffineTransform t = new AffineTransform(toScreen);

		t.concatenate(stage.getTransform());

		g2d.draw(t.createTransformedShape(stage.bound));

	}

}
