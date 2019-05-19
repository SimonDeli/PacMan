package service;

public class GhostService {

	private Dijkstra dijkstra;

	private static GhostService instance;

	private GhostService() {

	}

	public static GhostService getInstance() {
		if (instance == null)
			instance = new GhostService();
		return instance;
	}

//	public Ghost createGhost(int x, int y, int width, int height) {
//		return new Ghost(x, y, width, height);
//	}
}
