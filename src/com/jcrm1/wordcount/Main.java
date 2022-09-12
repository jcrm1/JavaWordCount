package com.jcrm1.wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;

public class Main {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: <word or phrase>");
			System.exit(1);
		}
		HashSet<File> files = recursiveSearch(new File(System.getProperty("user.dir")));
		long numOfWords = 0;
		String word = "";
		for (int i = 0; i < args.length; i++) word += args[i] + " ";
		word = word.substring(0, word.length() - 1).toLowerCase();
		for (File file : files) {
			try {
				InputStream is = Files.newInputStream(Path.of(file.getAbsolutePath()), StandardOpenOption.READ);
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String text;
				while ((text = reader.readLine()) != null) {
					text = text.toLowerCase();
					for (int i = text.indexOf(word); i < text.length(); i = text.indexOf(word, i + 1)) {
						if (i == -1) break;
						numOfWords++;
					}
				}
				is.close();
				reader.close();
			} catch (IOException e) {
				
			}
		}
		System.out.println("Total number of \"" + word + "\": " + numOfWords);
	}
	private static HashSet<File> recursiveSearch(File dir) {
		HashSet<File> files = new HashSet<File>();
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (file.isDirectory()) files.addAll(recursiveSearch(file));
				else files.add(file);
			}
		}
		return files;
	}
}
