package com.cat.zhsy.audio;

import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

//播放声音的类
public class PlaySounds {

	// 播放音频文件
	public static void play(String fileurl) throws Exception {

		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileurl);
		System.out.println(in);
		AudioInputStream ais = AudioSystem.getAudioInputStream(in);
		System.out.println(ais);
		AudioFormat aif = ais.getFormat();
		SourceDataLine sdl = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
		sdl = (SourceDataLine) AudioSystem.getLine(info);
		sdl.open(aif);
		sdl.start();
		int nByte = 0;
		byte[] buffer = new byte[1024 * 10];
		while (nByte != -1) {
			nByte = ais.read(buffer, 0, 1024 * 10);
			if (nByte >= 0) {
				sdl.write(buffer, 0, nByte);
			}
		}
		sdl.stop();

	}

	public static void main(String[] args) throws Exception {
		play("1.mp3");
	}
}