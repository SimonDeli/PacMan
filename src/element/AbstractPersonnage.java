package element;

import java.awt.Dimension;
import java.awt.Graphics;

import service.GridService;

public abstract class AbstractPersonnage {

    protected boolean right = false, left = false, up = false, down = false;

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Dimension actualPos;
    protected Dimension anchor;
    protected int gap;
    protected int speed = 100;

    protected GridService gs;

    public AbstractPersonnage(int x, int y, int width, int height) {
        this(x, y, width, height, 2);
    }

    public AbstractPersonnage(int x, int y, int width, int height, int gap) {
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
        return this.x + this.width / 2;
    }

    public int getCenterY() {
        return this.y + this.height / 2;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
