package files;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import data.Bar;
import data.BarAll;
import data.BarMore;

public class ReadFile {
	static public ArrayList<Bar> readFromFile(String filename, int level) throws Exception {
		ArrayList<Bar> res = new ArrayList<Bar>();
		Scanner sc = new Scanner(new File(filename));
		sc.nextLine();
		int n = 0;
		while (sc.hasNext()) {
			n++;
			String line = sc.nextLine();
			Bar bar = null;
			if (level == 1)
				bar = Bar.parseString(line);
			if (level == 2)
				bar = BarMore.parseString(line);
			if (level == 3)
				bar = BarAll.parseString(line);
			res.add(bar);
		}
		sc.close();
		System.out.printf("%s read finish --- line number: %d \n", filename, n);
		return res;
	}

	static public ArrayList<Bar> readFromFile(String filename) throws Exception {
		return readFromFile(filename, 1);
	}
}
