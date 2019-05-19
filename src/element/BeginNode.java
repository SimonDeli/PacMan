package element;

import java.awt.Color;
import java.awt.Graphics;

public class BeginNode extends AbstractNode {

	public BeginNode(int x, int y) {
		super(x, y);
	}

	@Override
	public String toString() {
		return super.toString() + " [type] = begin";
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, Math.round(width), Math.round(height));
	}

}
