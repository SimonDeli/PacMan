package element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import misc.TypeNode;
import service.GridService;
import service.NodeService;

public class PacMan extends AbstractPersonnage implements KeyListener {

	private boolean right = false, left = false, up = false, down = false;
	private boolean tmpRight = false, tmpLeft = false, tmpUp = false, tmpDown = false;
	private boolean centered;
	private boolean wait = false;

	private Dimension actualPos;
	private Dimension destinationPos;
	private Dimension anchor;
	private AbstractNode endNode;

	private String dir = "";

	private static GridService gs;
	private static NodeService ns;

	public PacMan(int x, int y, int width, int height, int gap) {
		super(x, y, width, height, gap);
		gs = GridService.getInstance();
		ns = NodeService.getInstance();
		this.anchor = new Dimension(this.getCenterX(), this.getCenterY());
		this.actualPos = gs.getPositionFromPixel(new Dimension(this.getCenterX(), this.getCenterY()));
		this.destinationPos = actualPos;
		this.endNode = ns.getNode(TypeNode.END, x, y);
	}

	@Override
	public void paint(Graphics g) {
		paintPacMan(g);
	}

	public void paintPacMan(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(this.x, this.y, width, height);
	}

	@Override
	public void move() {
		this.centered = gs.isCentered(this);
		this.actualPos = gs.getPositionFromPixel(new Dimension(this.anchor.width, this.anchor.height));
		interval();
		if (wait)
			waitToMove();
		if (!isCollide())
			direction();

	}

	private void direction() {
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

	private void interval() {
		if (centered) {
			setDestinationPosFromDir(this.dir);
			setIntervalEndNode();
		}

	}

	private void setIntervalEndNode() {
		this.endNode.setX(this.x - gap / 2);
		this.endNode.setY(this.y - gap / 2);
	}

	private boolean isCollide() {
		if (Wall.getDim().contains(this.destinationPos) && centered)
			return true;
		return false;
	}

	private void waitToMove() {
		if (tmpRight && centered) {
			resetDirection(false);
			right = true;
			wait = false;
			resetDirection(true);
		} else if (tmpLeft && centered) {
			resetDirection(false);
			left = true;
			wait = false;
			resetDirection(true);
		} else if (tmpUp && centered) {
			resetDirection(false);
			up = true;
			wait = false;
			resetDirection(true);
		} else if (tmpDown && centered) {
			resetDirection(false);
			down = true;
			wait = false;
			resetDirection(true);
		}
	}

	private void setAnchorFromType(String type) {
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

	private void setDestinationPosFromDir(String dir) {
		switch (dir) {
		case "up":
			this.destinationPos.setSize(this.actualPos.width, this.actualPos.height - 1);
			break;
		case "down":
			this.destinationPos.setSize(this.actualPos.width, this.actualPos.height + 1);
			break;
		case "right":
			this.destinationPos.setSize(this.actualPos.width + 1, this.actualPos.height);
			break;
		case "left":
			this.destinationPos.setSize(this.actualPos.width - 1, this.actualPos.height);
			break;
		}
	}

	private void resetDirection(boolean tmp) {
		if (!tmp) {
			this.right = false;
			this.left = false;
			this.up = false;
			this.down = false;
		} else {
			this.tmpRight = false;
			this.tmpLeft = false;
			this.tmpUp = false;
			this.tmpDown = false;
		}

	}

	@Override
	public void keyPressed(KeyEvent key) {
		int code = key.getKeyCode();
		if (!tmpUp && !tmpDown && !tmpRight && !tmpLeft)
			switch (code) {
			case KeyEvent.VK_UP:
				if (up || down) {
					resetDirection(false);
					up = true;
				} else {
					tmpUp = true;
					wait = true;
				}
				dir = "up";
				break;
			case KeyEvent.VK_DOWN:
				if (up || down) {
					resetDirection(false);
					down = true;
				} else {
					tmpDown = true;
					wait = true;
				}
				dir = "down";
				break;
			case KeyEvent.VK_LEFT:
				if (left || right) {
					resetDirection(false);
					left = true;
				} else {
					tmpLeft = true;
					wait = true;
				}
				dir = "left";
				break;
			case KeyEvent.VK_RIGHT:
				if (left || right) {
					resetDirection(false);
					right = true;
				} else {
					tmpRight = true;
					wait = true;
				}
				dir = "right";
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
