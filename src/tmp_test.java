
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class tmp_test {

	public static void main(String[] args) throws Exception {
		double ori = 0.00041;
		double newv1 = 1-Math.pow(1-ori, 6);
		double newv2 = 1-Math.pow(1-ori, 0.1666667); 
		System.out.println(newv1);
		System.out.println(newv2);
//		System.out.println(10/5*2);
		return;
	}
}
