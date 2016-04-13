/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import acm.util.ErrorException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NameSurferDataBase implements NameSurferConstants {

	private HashMap<String, NameSurferEntry> nameDataBase = new HashMap<>();
	NameSurferEntry entry;

   /* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the
	 * data in the specified file.  The constructor throws an error
	 * exception if the requested file does not exist or if an error
	 * occurs as the file is being read.
	 */

	public NameSurferDataBase(String filename) {
		updateNameDataBase(filename);
	}

	private void updateNameDataBase(String filename){
		BufferedReader rd;
		try {
			rd = new BufferedReader(new FileReader(filename));
			while (true){
				String line = rd.readLine();

				if (line == null) break;
				entry = new NameSurferEntry(line);
				nameDataBase.put(entry.getName(), entry);
			}
			rd.close();
		} catch (IOException ex){
			throw new ErrorException(ex);
		}
	}

   /* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one
	 * exists.  If the name does not appear in the database, this
	 * method returns null.
	 */
	public NameSurferEntry findEntry(String name) {

		return nameDataBase.get(capitalizer(name));
	}

	private String capitalizer(String name){
		String result;
		char firstChar = name.charAt(0);
		String remainder = name.substring(1);

		if (Character.isLowerCase(firstChar)){
			firstChar = Character.toUpperCase(firstChar);
		}
		remainder = remainder.toLowerCase();

		result = firstChar + remainder;
		return result;
	}
}
