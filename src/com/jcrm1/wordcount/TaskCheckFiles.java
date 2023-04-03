package com.jcrm1.wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskCheckFiles implements Runnable {
	private static volatile long wordCount = 0;
	private final ConcurrentLinkedQueue<String> results;
	private final ConcurrentLinkedQueue<File> input;
	private final boolean printFiles;
	private final String word;
	private boolean isDone = false;
	public TaskCheckFiles(ConcurrentLinkedQueue<String> results, ConcurrentLinkedQueue<File> input, boolean printFiles, String word) {
		this.results = results;
		this.input = input;
		this.printFiles = printFiles;
		this.word = word;
	}
	@Override
	public void run() {
		while (!input.isEmpty()) {
			try {
				File file = input.poll();
				if (file == null) break;
				InputStream is = Files.newInputStream(Path.of(file.getAbsolutePath()), StandardOpenOption.READ);
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String text;
				int perFile = 0;
				while ((text = reader.readLine()) != null) {
					text = text.toLowerCase();
					for (int i = text.indexOf(word); i < text.length(); i = text.indexOf(word, i + 1)) {
						if (i == -1) break;
						perFile++;
					}
				}
				if (printFiles && perFile > 0) results.add(file.getAbsolutePath());
				wordCount += perFile;
				is.close();
				reader.close();
			} catch (IOException e) {
				
			}
		}
		isDone = true;
	}
	public static long getWordCount() {
		return wordCount;
	}
	public boolean isDone() {
		return this.isDone;
	}
}
