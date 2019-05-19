package service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReadTxtService {

	private FileInputStream fis;
	private FileOutputStream fos;
	private DataInputStream dis;
	private File file;

	private static ReadTxtService instance;

	public static ReadTxtService getInstance(File file) throws FileNotFoundException {
		if (instance == null)
			return new ReadTxtService(file);
		return instance;
	}

	private ReadTxtService(File file) throws FileNotFoundException {
		this.file = file;
		this.fis = new FileInputStream(file);
		this.dis = new DataInputStream(fis);
	}

	public Map<Integer, List<String>> readFile() throws IOException {
		List<String> tmpLines = new ArrayList<>();
		Map<Integer, List<String>> result = new HashMap<Integer, List<String>>();

		String line = "";
		byte[] buf = new byte[8];
		int c = 0;
		while ((c = this.dis.read(buf)) >= 0) {
			for (byte bit : buf) {
				if (bit != 10 && bit != 13) {
					line += ((char) bit);
				} else {
					if (line != "")
						tmpLines.add(line);
					line = "";
				}
			}
			buf = new byte[8];
		}
		List<String> lines = new ArrayList<>();
		// on enlève les commentaire -> lignes commençant par #
		lines = tmpLines.stream().filter(l -> !l.startsWith("#")).collect(Collectors.toList());
		fis.close();
		int compteur = 0;
		for (String l : lines) {
			compteur++;
			List<String> split = Arrays.asList(l.split(" "));
			result.put(compteur, split);
		}

		return result;
	}
}
