package clueGame;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * BadConfigException
 * Casey Turner, Murat Tuter
 * 
 */

@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception {

		public BadConfigFormatException() {
		
			super("Bad Configuration File");
			
			// open logging file for EC
			FileWriter writer = null;
			try {
				writer = new FileWriter("logfile.txt",true);
			} catch (IOException e) {
				System.out.println("couldnt open error log file");
			}
			
			// write to file
			PrintWriter printWriter = new PrintWriter(writer);
			printWriter.println("ERROR: Bad Configuration File");
			printWriter.close();
		}
		
		public BadConfigFormatException(String errmsg) {
			
			super(errmsg);
			
			// open logging file for EC
			FileWriter writer = null;
			try {
				writer = new FileWriter("logfile.txt",true);
			} catch (IOException e) {
				System.out.println("couldnt open error log  file");
			}
			
			// write to file
			PrintWriter printWriter = new PrintWriter(writer);
			printWriter.println("ERROR: " + errmsg);
			printWriter.close();
		}
	}

