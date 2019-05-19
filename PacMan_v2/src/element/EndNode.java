package element;

import java.awt.Color;
import java.awt.Graphics;

public class EndNode extends AbstractNode {

	public EndNode(int x, int y) {
		super(x, y);
	}

	@Override
	public String toString() {
		return super.toString() + " [type] = end";
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, Math.round(width), Math.round(height));
	}

}
