package com.cat.zhsy.audio;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AudioConvert {

	private final static String ROOT = Thread.currentThread().getContextClassLoader().getResource("").getPath();

	private static String FFMPEG = ROOT + "tool" + File.separator + "ffmpeg.exe";

	public static void convertToWav(String src) throws Exception {
		String dest = src.substring(0, src.lastIndexOf(".")) + ".wav";
		convertToWav(src, dest);
	}

	public static void convertToWav(String src, String dest) throws Exception {
		List<String> command = new ArrayList<>();
		command.add(FFMPEG);// windows
		// command.add("ffmpeg");// linux

		command.add("-i");// 指定要转换的文件路径
		command.add(src);
		// 音频
		// command.add("-ab");// 声音比特率
		// command.add("128");
		// command.add("-acodec");//
		// command.add("libmp3lame");
		// command.add("-ac");// 声道数量2双声道
		// command.add("2");
		// command.add("-ar");// 设定声音采样率
		// command.add("22050");
		command.add(dest);

		ProcessBuilder builder = new ProcessBuilder();
		builder.redirectErrorStream(true);
		builder.command(command);
		Process process = builder.start();
		printInfo(process.getInputStream());
		process.waitFor();
	}

	private static void printInfo(InputStream input) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
