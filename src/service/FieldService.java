package service;

import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import element.Wall;
import exception.FieldException;
import exception.GridException;
import misc.Const;
import misc.TypeNode;

public class FieldService {

	private static FieldService instance;

	private GridService gs;
	private NodeService ns;
	private WallService ws;

	private FieldService() {
		gs = GridService.getInstance();
		ns = NodeService.getInstance();
		ws = WallService.getInstance();
	}

	public static FieldService getInstance() {
		if (instance == null)
			instance = new FieldService();
		return instance;
	}

	public void createField(Map<Integer, List<String>> data) throws FieldException, GridException {
		if (data == null)
			throw new FieldException("There are no data found");

		for (Map.Entry<Integer, List<String>> entry : data.entrySet()) {
			if ("wall".equals(entry.getValue().get(0))) {
				createHorizontalWall(entry.getKey());
				continue;
			}
			for (String line : entry.getValue()) {
				String[] tab = line.split(",");
				if ("W".equals(tab[1])) {
					Dimension posWall = new Dimension(Integer.parseInt(tab[0]), entry.getKey());
					ws.createWall(
							new Wall(gs.getPixelFromPosition(posWall).width, gs.getPixelFromPosition(posWall).height));
				} else {
					if ("B".equals(tab[1])) {
						Dimension posNode = new Dimension(Integer.parseInt(tab[0]), entry.getKey());
						ns.getNode(TypeNode.BEGIN, gs.getPixelFromPosition(posNode).width,
								gs.getPixelFromPosition(posNode).height);
					} else if ("N".equals(tab[1])) {
						Dimension posNode = new Dimension(Integer.parseInt(tab[0]), entry.getKey());
						ns.getNode(TypeNode.NODE, gs.getPixelFromPosition(posNode).width,
								gs.getPixelFromPosition(posNode).height);
					}
				}
			}
		}
	}

	public void createHorizontalWall(int line) throws GridException {
		for (int i = 0; i < Const.NBR_COL; i++) {
			Dimension posWall = new Dimension(i + 1, line);
			ws.createWall(new Wall(gs.getPixelFromPosition(posWall).width, gs.getPixelFromPosition(posWall).height));
		}
	}

	@SuppressWarnings("unused")
	public void createTestObstacle() throws GridException {
		Dimension posWall1 = new Dimension(10, 3);
		Dimension posWall2 = new Dimension(10, 4);
		Dimension posWall3 = new Dimension(11, 3);
		Dimension posWall4 = new Dimension(11, 4);

		Wall obstacle1 = new Wall(gs.getPixelFromPosition(posWall1).width, gs.getPixelFromPosition(posWall1).height);
		Wall obstacle2 = new Wall(gs.getPixelFromPosition(posWall2).width, gs.getPixelFromPosition(posWall2).height);
		Wall obstacle3 = new Wall(gs.getPixelFromPosition(posWall3).width, gs.getPixelFromPosition(posWall3).height);
		Wall obstacle4 = new Wall(gs.getPixelFromPosition(posWall4).width, gs.getPixelFromPosition(posWall4).height);
	}
}
