package element;

import java.awt.Dimension;
import java.awt.Graphics;

import exception.GridException;
import service.GridService;

public abstract class AbstractPersonnage {

	protected boolean right = false, left = false, up = false, down = false;

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected Dimension actualPos;
	protected Dimension anchor;
	protected int gap;
	protected double speed = 200;
	protected boolean centered;

	protected GridService gs;

	public AbstractPersonnage(double x, double y, int width, int height) throws GridException {
		this(x, y, width, height, 2);
	}

	public AbstractPersonnage(double x, double y, int width, int height, int gap) throws GridException {
		gs = GridService.getInstance();

		this.x = x + gap / 2;
		this.y = y + gap / 2;
		this.width = width - gap;
		this.height = height - gap;
		this.anchor = new Dimension(this.getCenterX(), this.getCenterY());
		this.gap = gap;
		this.actualPos = gs.getPositionFromPixel(new Dimension(this.getCenterX(), this.getCenterY()));
	}

	protected void direction() {
		if (this.right) {
			setAnchorFromType("left");
			x++;
		}
		if (this.left) {
			setAnchorFromType("right");
			x--;
		}
		if (this.down) {
			setAnchorFromType("top");
			y++;
		}
		if (this.up) {
			setAnchorFromType("bottom");
			y--;
		}
	}

	public int getCenterX() {
		return (int) this.x + this.width / 2;
	}

	public int getCenterY() {
		return (int) this.y + this.height / 2;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public abstract void move();

	public abstract void paint(Graphics g);

	protected void resetDirection(boolean tmp) {
		if (!tmp) {
			this.right = false;
			this.left = false;
			this.up = false;
			this.down = false;
		}
	}

	protected void setAnchorFromType(String type) {
		this.anchor.width = this.getCenterX();
		this.anchor.height = this.getCenterY();

		if ("left".equals(type)) {
			this.anchor.width -= this.width / 2;
		} else if ("right".equals(type)) {
			this.anchor.width += this.width / 2;
		} else if ("top".equals(type)) {
			this.anchor.height -= this.height / 2;
		} else if ("bottom".equals(type)) {
			this.anchor.height += this.height / 2;
		}
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
}
