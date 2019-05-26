package service;

import java.util.ArrayList;
import java.util.List;

import element.Ghost;

public class GhostService {

	private static GhostService instance;

	private GhostService() {

	}

	public static GhostService getInstance() {
		if (instance == null)
			instance = new GhostService();
		return instance;
	}

	public List<Ghost> getGhosts() {
		if (Ghost.getGhosts() == null || Ghost.getGhosts().isEmpty())
			return new ArrayList<Ghost>();
		return Ghost.getGhosts();
	}
}
