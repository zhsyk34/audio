package com.cat.zhsy.audio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

//音频播放,wav等格式可直接运行,mp3等格式需引入额外的jar包
public class AudioPlayer {

	// 路径等的配置信息
	private static final String DIR = "audio" + File.separator;
	private static final String SUFFIX = ".mp3";

	public static void beep(List<String> names) {
		for (String name : names) {
			beep(name);
		}
	}

	public static void beep(String[] names) {
		for (String name : names) {
			beep(name);
		}
	}

	public static void beep(String name, boolean defined) {
		String path = defined ? name : (DIR + File.separator + name + SUFFIX);
		System.out.println(path);
		play(path);
	}

	public static void beep(String name) {
		String path = DIR + File.separator + name + SUFFIX;
		System.out.println(path);
		play(path);
	}

	// 播放
	private static void play(String path) {
		final InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		try {
			final AudioInputStream stream = AudioSystem.getAudioInputStream(input);
			final AudioFormat format = getOutFormat(stream.getFormat());
			final Info info = new Info(SourceDataLine.class, format);

			final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			if (line != null) {
				line.open(format);
				line.start();
				stream(AudioSystem.getAudioInputStream(format, stream), line);
				line.drain();
				line.stop();
			}
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	private static AudioFormat getOutFormat(AudioFormat format) {
		final int channel = format.getChannels();
		final float rate = format.getSampleRate();
		return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, channel, channel * 2, rate, false);
	}

	private static void stream(AudioInputStream in, SourceDataLine line) throws IOException {
		final byte[] buffer = new byte[65536];
		for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
			line.write(buffer, 0, n);
		}
	}
}