package cnc.editor;

import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.geom.Rectangle2D;

import cnc.commands.Builder;

public class BoundTest {
	
	@Test
	public void bound2(){
		
		Builder builder = new Builder();
		
		builder.draw(true).move(1, 2).move(12,30);
		
		VectorObject2d object2d = new VectorObject2d(builder.biuld());
		
		object2d.updateBoundaries();
		Rectangle2D b = object2d.bound;

		assertEquals(1, b.getX(),0.0001);
		assertEquals(2, b.getY(),0.0001);
		assertEquals(12 - 1, b.getWidth(),0.0001);
		assertEquals(30 - 2, b.getHeight(),0.0001);
		
		
	}
	
	@Test
	public void boundHidden(){
		
		Builder builder = new Builder();
		
		builder.draw(true).move(1, 2).move(12,30).draw(false).move(100, 200);
		
		VectorObject2d object2d = new VectorObject2d(builder.biuld());
		
		object2d.updateBoundaries();
		Rectangle2D b = object2d.bound;

		assertEquals(1, b.getX(),0.0001);
		assertEquals(2, b.getY(),0.0001);
		assertEquals(12 - 1, b.getWidth(),0.0001);
		assertEquals(30 - 2, b.getHeight(),0.0001);
		
	}
	
}
