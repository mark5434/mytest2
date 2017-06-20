package files;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import data.Bar;

public class ReadFile {
	static public ArrayList<Bar> readFromFile(String filename) throws Exception {
		ArrayList<Bar> res = new ArrayList<Bar>();
		Scanner sc = new Scanner(new File(filename));
		sc.nextLine();
		int n = 0;
		while (sc.hasNext()) {
			n++;
			String line = sc.nextLine();
			Bar bar = Bar.parseString(line);
			res.add(bar);
		}
		sc.close();
		System.out.printf("%s read finish --- line number: %d \n", filename, n);
		return res;
	}

}
