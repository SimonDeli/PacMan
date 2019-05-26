package service;

import java.util.ArrayList;

import element.Wall;

public class WallService {
	private static WallService instance;

	private WallService() {

	}

	public static WallService getInstance() {
		if (instance == null)
			instance = new WallService();
		return instance;
	}

	public void createWall(Wall wall) {
		if (Wall.getWalls() == null || Wall.getWalls().isEmpty())
			Wall.setWalls(new ArrayList<Wall>());
		Wall.getWalls().add(wall);
	}
}
