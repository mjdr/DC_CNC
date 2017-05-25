package cnc.files;

import cnc.Config;
import cnc.objects2d.VectorObject2d;

public class PCBPreparer {
	public static void prepare(VectorObject2d pcb){
		pcb.setOriginToCenter();
		pcb.position.x = -pcb.bound.x + Config.drawerSize/2;
		pcb.position.y = -pcb.bound.y + Config.drawerSize/2;
		pcb.setScalable(false);
	}
}
