package com.jcrm1.wordcount;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
	private static final int maxThreads = 10;
	private static final ExecutorService pool = Executors.newFixedThreadPool(maxThreads);
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: [--print_files] <word or phrase>");
			System.exit(1);
		}
		ConcurrentLinkedQueue<File> files = recursiveSearch(new File(System.getProperty("user.dir")));
		String word = "";
		int start = 0;
		if (args[0].equals("--print_files")) start = 1;
		for (int i = start; i < args.length; i++) word += args[i] + " ";
		word = word.substring(0, word.length() - 1).toLowerCase();
		ConcurrentLinkedQueue<String> results = new ConcurrentLinkedQueue<String>();
		for (int i = 0; i < maxThreads; i++) pool.submit(new TaskCheckFiles(results, files, start == 1, word));
		while (!files.isEmpty()) {
			
		}
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (String str : results) {
			System.out.println(str);
		}
		System.out.println("Total number of \"" + word + "\": " + TaskCheckFiles.getWordCount());
		System.out.println(files.size());
	}
	private static ConcurrentLinkedQueue<File> recursiveSearch(File dir) {
		ConcurrentLinkedQueue<File> files = new ConcurrentLinkedQueue<File>();
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (file.isDirectory()) files.addAll(recursiveSearch(file));
				else files.add(file);
			}
		}
		return files;
	}
}
