package service;

public class LinkService {

	private static LinkService instance;

	private LinkService() {

	}

	public static LinkService getInstance() {
		if (instance == null)
			return new LinkService();
		return instance;
	}

	public void craeteLink() {

	}
}
