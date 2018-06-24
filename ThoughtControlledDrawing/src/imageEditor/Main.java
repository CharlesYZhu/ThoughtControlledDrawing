package imageEditor;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Main {
	
	public static PaintApp painter;

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, AWTException {
		PrintWriter textLogger = new PrintWriter("text-log.txt", "UTF-8");
		painter = new PaintApp(textLogger);
		
	}

}
