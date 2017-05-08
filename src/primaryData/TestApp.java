package primaryData;

import java.io.File;
import java.util.Map;

/**
 * 
 * @author vikas
 *
 */

public class TestApp {

	public static void main(String[] args) {

		int number = 0;

		try {
			int n = Integer.parseInt(args[0]);
			number = n;
		} catch (NumberFormatException e) {
			System.out.println("please enter a valid number ...");
			e.printStackTrace();
		}

		String filePath = args[1];
		ProcessFiles process;
		process = new ProcessFiles(new File(filePath));
		ReadFiles read = new ReadFiles();
		read.readProcessFile(process);

		startThread(read);

		Map<String, Integer> map = read.createMap();
		MapSort mapSort = new MapSort(map);
		System.out.println();
		mapSort.printSorted(number);

	}

	@SuppressWarnings("static-access")
	public static void startThread(ReadFiles read) {

		int numOfThread = 100;

		Thread[] thread = new Thread[numOfThread];

		for (int i = 1; i < numOfThread; i++) {
			thread[i] = new Thread(read);
			thread[i].start();

			try {
				thread[i].sleep(1);
				thread[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
