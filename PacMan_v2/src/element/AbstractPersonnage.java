package element;

import java.awt.Graphics;

public abstract class AbstractPersonnage {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int gap;

	public AbstractPersonnage(int x, int y, int width, int height) {
		this(x, y, width, height, 2);
	}

	public AbstractPersonnage(int x, int y, int width, int height, int gap) {
		this.x = x + gap / 2;
		this.y = y + gap / 2;
		this.width = width - gap;
		this.height = height - gap;
		this.gap = gap;
	}

	public int getX() {
		return this.x;
	}

	public int getCenterX() {
		return this.x + this.width / 2;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public int getCenterY() {
		return this.y + this.height / 2;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public abstract void paint(Graphics g);

	public abstract void move();
}
