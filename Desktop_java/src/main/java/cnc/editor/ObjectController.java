package cnc.editor;

import static cnc.Config.pixelPerMM;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import cnc.files.PCBPreparer;
import cnc.files.loaders.ADCOMPLoader;
import cnc.files.loaders.HPGLLoader;
import cnc.files.loaders.ImageLoader;
import cnc.files.loaders.ObjectLoader;
import cnc.objects2d.CompositeObject2d;
import cnc.objects2d.Object2d;
import cnc.objects2d.VectorObject2d;
import cnc.tools.opt.Optimizer;
import cnc.utils.IOUtils;

public class ObjectController {
	private ObjectViewer objectViewer;
	
	private CompositeObject2d root;
	private Map<Path2D.Double, Object2d> boxes;
	private Object2d overObject;
	private Object2d selectedObject;
	private Object2d dragObject;
	private Point dragPointOnScreen;
	private Point2D.Float dragStart;
	private PathViewer pathViewer;
	private File lastImportedFile;
	private FileType lastImportedFileType;
	
	private Map<FileType, ObjectLoader> loaders = new HashMap<>();
	
	public void init(CompositeObject2d obj){
		root = obj;
		pathViewer = new PathViewer(this);
		objectViewer = new ObjectViewer(this);
		boxes = new HashMap<Path2D.Double, Object2d>();
		dragStart = new Point2D.Float();
		
		updateStage();
		initLoaders();
		
		
		objectViewer.openWindow();
		
		
	}
	
	private void initLoaders() {
		loaders.put(FileType.ADCOMP, new ADCOMPLoader());
		loaders.put(FileType.HPGL, new HPGLLoader());
		loaders.put(FileType.BIN_IMAGE, new ImageLoader());
		
	}

	public Object2d getRoot() {
		return root;
	}
	
	public Object2d getDragObject() {
		return dragObject;
	}
	
	public Object2d getSelectedObject() {
		return selectedObject;
	}
	
	private void updatePathViewer(){
		pathViewer.setCommands(Optimizer.optiomaze(root.getCommands()));
		pathViewer.update();
	}
	
	private void updateStage(){
		root.updateTransformation();
		root.updateBoundaries();
		updateBoxes();
		objectViewer.update();
	}
	
	private void updateBoxes(){
		boxes.clear();
		updateBoxesParent(root);
	}
	
	private void updateBoxesParent(Object2d object2d){
		if(!(object2d instanceof CompositeObject2d)){
			
			AffineTransform t = new AffineTransform(Viewer.toScreen);
			t.concatenate(object2d.getTransform());
			
			boxes.put((Path2D.Double)t.createTransformedShape(object2d.bound), object2d);
		}
		else
			for(Object2d obj : ((CompositeObject2d)object2d).getChildren())
				updateBoxesParent(obj);
	}
	
	public void endDrag(){
		if(dragObject != null){
			updateStage();
			updatePathViewer();
			dragObject = null;
		}
	}
	
	public void startDrag(int x, int y){
		if(overObject != null){
			dragObject = overObject;
			dragPointOnScreen = new Point(x, y);
			dragStart.setLocation(overObject.position);
			
			updateStage();
		}
	}
	
	public void rotateSelectedObject(){
		if(selectedObject != null){
			selectedObject.rotation += 3.141592f/2;
			updateStage();
			updatePathViewer();
		}
	}
	public void openPathWindow(){
		pathViewer.openWindow();
	}
	
	public void dragObject(int x, int y){
		if(dragObject == null) return;

		float dx = (x - dragPointOnScreen.x) / pixelPerMM;
		float dy = (y - dragPointOnScreen.y) / pixelPerMM;

		dragObject.position.x = dragStart.x + dx;
		dragObject.position.y = dragStart.y - dy;

		updateStage();
	}
	
	public void selectObject(){
		selectedObject = overObject;
	}
	
	public void updateSelectedObject(int x, int y){
		for(Entry<Path2D.Double, Object2d> entry : boxes.entrySet()){
			if(entry.getKey().contains(x, y)){
				overObject = entry.getValue();
				objectViewer.update();
				return;
			}
		}
		
		overObject = null;
		
		objectViewer.update();
	}
	
	public void importFile(File file, FileType type) throws IOException, RuntimeException {
		VectorObject2d obj = loaders.get(type).load(file);
		PCBPreparer.prepare(obj);
		root.add(obj);
		updateStage();
		updatePathViewer();
		lastImportedFile = file;
		lastImportedFileType = type;
	}
	
	public void deleteSelectedObject(){
		if(selectedObject == null) return;
		if(selectedObject.getParent() == null) return;
		
		((CompositeObject2d)selectedObject.getParent()).remove(selectedObject);
		updateStage();
		updatePathViewer();
		
	}

	public void importLastFile() throws IOException, RuntimeException {
		if(lastImportedFile != null)
			importFile(lastImportedFile,lastImportedFileType);
		
	}

	public void saveAsPackets() {
		List<cnc.data.Package> packages = root.getCommands().stream().map((c)->c.toPackage()).collect(Collectors.toList());
		
		try {
			IOUtils.writePackagesAsText(System.out, packages);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
