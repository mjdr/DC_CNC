package cnc.editor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class ObjectViewer extends Viewer implements ActionListener{
	
	private JFileChooser fileChooser;
	private JMenuBar menuBar = new JMenuBar();
	
	public ObjectViewer(ObjectController controller) {
		super(controller);
		fileChooser = new JFileChooser();
		frame.setJMenuBar(menuBar);
		frame.setTitle("Object Viewer");
		
		initMenuBar();
	}

	private void initMenuBar() {
		JMenu menu;
		JMenuItem item;
		
		menu = new JMenu("File");
		menuBar.add(menu);
		
		item = new JMenuItem("Open");
		item.addActionListener(this);
		menu.add(item);
		item = new JMenuItem("Save as packets");
		item.addActionListener(this);
		menu.add(item);
		item = new JMenuItem("Save as svg");
		item.addActionListener(this);
		menu.add(item);
		item = new JMenuItem("Close");
		item.addActionListener(this);
		menu.add(item);
		
		menu = new JMenu("Edit");
		menuBar.add(menu);
		
		item = new JMenuItem("Import ADCOMP (Eagle)");
		item.addActionListener(this);
		menu.add(item);
		item = new JMenuItem("Import HPGL       (KiCad)");
		item.addActionListener(this);
		menu.add(item);
		item = new JMenuItem("Import Image");
		item.addActionListener(this);
		menu.add(item);
		item = new JMenuItem("Last import");
		item.addActionListener(this);
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0));
		menu.add(item);
		item = new JMenuItem("Rotate");
		item.addActionListener(this);
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
		menu.add(item);
		item = new JMenuItem("Delete");
		item.addActionListener(this);
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		menu.add(item);
		
		menu = new JMenu("Window");
		menuBar.add(menu);
		item = new JMenuItem("Paths viewer");
		item.addActionListener(this);
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
		menu.add(item);
		
		
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
			@Override
			public void mouseClicked(MouseEvent e) {
				ObjectViewer.this.mouseClicked(e.getX(),e.getY());
			}
		});
	}
	protected void mouseClicked(int x, int y) {
		controller.updateSelectedObject(x, y);
		controller.selectObject();
	}
	private void importFile(FileType type){
		
		fileChooser.setDialogTitle("Open " + type.toString() + " file");
		if(fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
		
		File file = fileChooser.getSelectedFile();
		try {
			controller.importFile(file, type);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Import error: " + e.getMessage());
		}
	}
	private void importLastFile(){
	
	try {
		controller.importLastFile();
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "Import error: " + e.getMessage());
	}
}
	
	public void update(){
		commands = controller.getRoot().getCommands();
		repaint();
	}
	protected void mouseDragged(int x, int y) {
		controller.dragObject(x, y);
		
	}
	protected void mouseReleased() {
		controller.endDrag();
	}
	protected void mousePressed(int x, int y) {
		controller.startDrag(x, y);
	}	private void mouseMoved(int x, int y) {
		controller.updateSelectedObject(x, y);
	}
	@Override
	public void paint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		setupGraphicsStuff(g2d);
		drawBackground(g2d);
		drawCommands(g2d);

		drawAllBounds(g2d, controller.getRoot());

	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!(e.getSource() instanceof JMenuItem)) return;
		JMenuItem item = (JMenuItem) e.getSource();
		
		
		switch (item.getText()) {
			case "Open":
				
				break;
			case "Save as packets":
				controller.saveAsPackets();
				break;
			case "Save as svg":
				
				break;
			case "Close":
				
				break;
			case "Import ADCOMP (Eagle)":
				importFile(FileType.ADCOMP);
				break;
			case "Import HPGL       (KiCad)":
				importFile(FileType.HPGL);
				break;
			case "Import Image":
				importFile(FileType.BIN_IMAGE);
				break;
			case "Last import":
				importLastFile();
				break;
			case "Rotate":
				controller.rotateSelectedObject();
				break;
			case "Delete":
				controller.deleteSelectedObject();
				break;
			case "Paths viewer":
				controller.openPathWindow();
				break;

		}
		
	}

}
