package element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import service.GridService;

public class Wall {
	private int x;
	private int y;
	private float width;
	private float height;
	private static Set<Dimension> dim;
	private static List<Wall> walls;
	private GridService gs;

	public Wall(int x, int y) {
		this.gs = GridService.getInstance();
		this.x = x;
		this.y = y;
		this.width = gs.getWidthCol();
		this.height = gs.getHeightCol();
		if (dim == null)
			dim = new HashSet<>();
		if (walls == null)
			walls = new ArrayList<>();
		dim.add(gs.getPositionFromPixel(new Dimension(x, y)));
		walls.add(this);
	}

	public void paint(Graphics g) {
		g.setColor(new Color(0, 151, 230));
		g.fillRect(x, y, Math.round(width), Math.round(height));
	}

	public static Set<Dimension> getDim() {
		return dim;
	}

	public static void setDim(Set<Dimension> dim) {
		Wall.dim = dim;
	}

	public static List<Wall> getWalls() {
		return walls;
	}

	public static void setWalls(List<Wall> walls) {
		Wall.walls = walls;
	}
}
