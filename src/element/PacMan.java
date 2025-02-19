package element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import exception.DijkstraException;
import exception.GridException;
import exception.LinkException;
import misc.Const;
import misc.TypeNode;
import service.Dijkstra;
import service.GhostService;
import service.NodeService;

public class PacMan extends AbstractPersonnage implements KeyListener {

	private NodeService ns;
	private boolean tmpRight = false, tmpLeft = false, tmpUp = false, tmpDown = false;

	private boolean wait = false;

	private Dimension destinationPos;
	private String dir = "";
	private GhostService ghs;

	private AbstractNode currentEndNode;

	public PacMan(int x, int y, int width, int height, int gap) throws GridException {
		super(x, y, width, height, gap);
		ns = NodeService.getInstance();
		ghs = GhostService.getInstance();
		this.destinationPos = actualPos;
		this.currentEndNode = ns.getNodeByPos(this.actualPos);
	}

	private void interval() throws DijkstraException, LinkException {
		if (centered) {
			setDestinationPosFromDir(this.dir);
			setIntervalEndNode();
		}

	}

	private boolean isCollide() {
		if (Wall.getDim().contains(this.destinationPos) && centered)
			return true;
		return false;
	}

	private boolean checkOnPress(String nextDirection) {
		if (up || down) {
			if ("right".equals(nextDirection)) {
				Dimension check = new Dimension(this.destinationPos.width + 1, this.destinationPos.height);
				if (Wall.getDim().contains(check)) {
					return true;
				}
			} else if ("left".equals(nextDirection)) {
				Dimension check = new Dimension(this.destinationPos.width - 1, this.destinationPos.height);
				if (Wall.getDim().contains(check)) {
					return true;
				}
			}
		} else if (left || right) {
			if ("up".equals(nextDirection)) {
				Dimension check = new Dimension(this.destinationPos.width, this.destinationPos.height - 1);
				if (Wall.getDim().contains(check)) {
					return true;
				}
			} else if ("down".equals(nextDirection)) {
				Dimension check = new Dimension(this.destinationPos.width, this.destinationPos.height + 1);
				if (Wall.getDim().contains(check)) {
					return true;
				}
			}
		}
		return false;

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
					dir = "up";
				} else if (!checkOnPress("up")) {
					tmpUp = true;
					wait = true;
					dir = "up";
				}
//				dir = "up";
				break;
			case KeyEvent.VK_DOWN:
				if (up || down) {
					resetDirection(false);
					down = true;
					dir = "down";
				} else if (!checkOnPress("down")) {
					tmpDown = true;
					wait = true;
					dir = "down";
				}
				break;
			case KeyEvent.VK_LEFT:
				if (left || right) {
					resetDirection(false);
					left = true;
					dir = "left";
				} else if ((!checkOnPress("left"))) {
					tmpLeft = true;
					wait = true;
					dir = "left";
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (left || right) {
					resetDirection(false);
					right = true;
					dir = "right";
				} else if ((!checkOnPress("right"))) {
					tmpRight = true;
					wait = true;
					dir = "right";
				}
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void move() {
//		A AMELIORER
		for (double i = 0; i < speed / Const.MAX_FPS; i++) {
			try {
				this.actualPos = gs.getPositionFromPixel(new Dimension(this.anchor.width, this.anchor.height));
			} catch (GridException e) {
				e.printStackTrace();
			}
			this.centered = gs.isCentered(this);
			try {
				interval();
			} catch (DijkstraException | LinkException e) {
				e.printStackTrace();
			}
			if (wait)
				waitToMove();
			if (!isCollide())
				direction();
			else
				resetDirection(false);
		}
	}

	@Override
	public void paint(Graphics g) {
		paintPacMan(g);
	}

	public void paintPacMan(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval((int) this.x, (int) this.y, width, height);
	}

	@Override
	protected void resetDirection(boolean tmp) {
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

	// A OPTIMISER
	private void setIntervalEndNode() throws DijkstraException, LinkException {
		for (AbstractNode node : AbstractNode.getNodes()) {
			if (this.actualPos.equals(node.getPos())) {
				node.setType(TypeNode.END);
				AbstractNode current = node;
				for (AbstractNode otherNode : AbstractNode.getNodes())
					if (!current.equals(otherNode) && !otherNode.getType().equals(TypeNode.BEGIN))
						otherNode.setType(TypeNode.NODE);
				if (!this.currentEndNode.equals(current)) {
					this.currentEndNode = current;
					for (Ghost ghost : ghs.getGhosts()) {
						Dijkstra d = new Dijkstra(ghost.getCurrentNode());
						List<AbstractNode> chemin = d.runAlgorithm();
						ghost.setChemin(chemin);
					}
				}
			}
		}
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

}
