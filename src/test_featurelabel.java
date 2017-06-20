
import java.io.PrintWriter;
import java.util.ArrayList;

import data.Bar;
import feature.InsessionOnebarDelta;
import files.ReadFile;
import labeling.InsessionLabel;

public class test_featurelabel {

	public static void main(String[] args) throws Exception {
		String file = "d:/finance/data/format/pp.1s.20160101-20160408";
		ArrayList<Bar> barlist = ReadFile.readFromFile(file);
		ArrayList<Double> feature = InsessionOnebarDelta.generateFeature(barlist);
		ArrayList<Double> label = InsessionLabel.tagLabel(barlist, 10);
		PrintWriter pw = new PrintWriter("d:/check");
		int sum = 0;
		for (int i = 0; i < barlist.size(); i++) {
			// System.out.println(feature.get(i)+"\t"+label.get(i));
			if (feature.get(i) <= -40)
				pw.println(
						barlist.get(i).day + " " + barlist.get(i).time + "\t" + feature.get(i) + "\t" + label.get(i));
			if (feature.get(i) <= -40)
				sum += label.get(i) - 8;
		}
		System.out.println(sum);
		pw.close();
	}
}
