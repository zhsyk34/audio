package com.cat.zhsy.audio;

public class Test {

	public static void main(String[] args) {
		AudioPlayer.beep("test.wav", true);
	}

	public static void batch() {
		String[] sounds = { "1", "3", "9" };
		AudioPlayer.beep(sounds);
	}

	public static void single() {
		for (int i = 0; i <= 9; i++) {
			AudioPlayer.beep(String.valueOf(i));
		}
	}

}
