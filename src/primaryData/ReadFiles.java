package primaryData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * @author vikas
 *
 */

public class ReadFiles implements Runnable {

	private File file;
	private String[] arr;
	private Map<String, Integer> map;
	private int count;
	private List<String> storeString;
	private List<List<String>> main;
	private String[][] result;
	private File processedFile;

	public ReadFiles() {
		this.map = new HashMap();
		this.main = new ArrayList<>();
	}

	public void readProcessFile(ProcessFiles process) {

		processedFile = process.getFile();
		readListFile(processedFile);
	}

	private synchronized void readListFile(File directory) {

		if (!directory.isDirectory()) {
			readFiles(directory, 1);
		} else {

			File[] listOfFiles = directory.listFiles();
			int numOfFiles = listOfFiles.length;

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					readFiles(listOfFiles[i], numOfFiles);
				} else if (listOfFiles[i].isDirectory()) {
					System.out.println("Directory " + listOfFiles[i].getName());
				}
			}
		}
	}

	private synchronized void readFiles(File file, int numOfFiles) {
		if (file == null) {
			return;
		}

		@SuppressWarnings("resource")
		String content = null;
		Scanner sc = null;
		try {

			sc = new Scanner(file);
			content = sc.useDelimiter("\\Z").next();
			arr = content.split(" ");
			storeString = Arrays.asList(arr);
			main.add(storeString);
			++count;

			if (count == numOfFiles) {
				result = convertList(main);
				arr = conver1D(result);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}

	}

	private String[] conver1D(String[][] array) {

		int totalLength = 0;
		for (String[] arr : array) {

			totalLength += arr.length;
		}

		String[] result = new String[totalLength];

		int idx = 0;
		for (String[] arr : array) {

			for (String i : arr) {

				result[idx++] = i;
			}
		}

		return result;

	}

	private String[][] convertList(List<List<String>> lists) {

		String[][] array = new String[lists.size()][];
		String[] blankArray = new String[0];
		for (int i = 0; i < lists.size(); i++) {
			array[i] = lists.get(i).toArray(blankArray);
		}

		return array;
	}

	public synchronized Map<String, Integer> createMap() {
		for (int i = 0; i < arr.length; i++) {
			if (map.containsKey(arr[i])) {
				map.put(arr[i], map.get(arr[i]) + 1);
			} else {
				map.put(arr[i], 1);
			}
		}

		return map;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String[] getArr() {
		return arr;
	}

	public void setArr(String[] arr) {
		this.arr = arr;
	}

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

	@Override
	public void run() {

		readListFile(processedFile);
		createMap();

	}

}
