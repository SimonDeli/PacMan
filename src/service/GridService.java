package service;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import element.AbstractNode;
import element.AbstractPersonnage;
import element.Wall;
import misc.Const;

public class GridService {

	private static GridService instance;
	private float widthCol;
	private float heightCol;

	private GridService() {
		this.widthCol = (float) Const.SIZE_F.width / (float) Const.NBR_COL;
		this.heightCol = (float) Const.SIZE_F.height / (float) Const.NBR_ROW;
	}

	public static synchronized GridService getInstance() {
		if (instance == null)
			return new GridService();
		return instance;
	}

	public void paintGrid(Graphics g) {
		g.setColor(Color.WHITE);
		int widthCol = Const.SIZE_F.width / Const.NBR_COL;
		int heightRow = Const.SIZE_F.height / Const.NBR_ROW;
		for (int i = 0; i < Const.NBR_COL; i++) {
			int x = widthCol * (i + 1);
			g.drawLine(x, 0, x, Const.SIZE_F.height);
		}
		for (int i = 0; i < Const.NBR_ROW; i++) {
			int y = heightRow * (i + 1);
			g.drawLine(0, y, Const.SIZE_F.width, y);
		}
	}

	public Dimension getPositionFromPixel(Dimension pixel) {
		Dimension result = new Dimension();
		double x = Math.floor((float) (pixel.width * Const.NBR_COL) / (float) Const.SIZE_F.width);
		double y = Math.floor((float) (pixel.height * Const.NBR_ROW) / (float) Const.SIZE_F.height);

		result.setSize(x, y);
		return result;
	}

	public Dimension getPixelFromPosition(Dimension position) {
		Dimension result = new Dimension();
		int px = position.width;
		int py = position.height;

		float x = (px - 1) * widthCol;
		float y = (py - 1) * heightCol;

		result.setSize(x, y);
		return result;
	}

	public String getTypeFromPos(Dimension pos) {
		for (AbstractNode node : AbstractNode.getNodes())
			if (node.getPos().equals(pos))
				return "node";

		if (Wall.getDim().contains(pos))
			return "wall";
		return null;
	}

	public boolean isCentered(AbstractPersonnage target) {
		int x = target.getCenterX();
		int y = target.getCenterY();

		if (Math.round((x % widthCol) - widthCol / 2) == 0 && Math.round((y % heightCol) - heightCol / 2) == 0)
			return true;
		return false;
	}

	public float getWidthCol() {
		return this.widthCol;
	}

	public void setWidthCol(float widthCol) {
		this.widthCol = widthCol;
	}

	public float getHeightCol() {
		return this.heightCol;
	}

	public void setHeightCol(float heightCol) {
		this.heightCol = heightCol;
	}
}
