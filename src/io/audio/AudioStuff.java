package io.audio;

import java.io.InputStream;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public class AudioStuff {

	public static void init() {
		for (Mixer.Info s : AudioSystem.getMixerInfo()) {
			System.out.printf(
					"Name: %s\nVendor: %s\nDescription: %s\nVersion: %s\n\n",
					s.getName(), s.getVendor(), s.getDescription(),
					s.getVersion());
		}
	}

	public static AudioInputStream getMicStream(int micId) throws Exception {
		Mixer mics = getMicMixers(micId);

		if (mics != null) {
			Vector<AudioFormat> Formats = getSuportedFormats(mics);
			AudioFormat format = Formats.get(Formats.size() - 1);
			mics.open();
			DataLine.Info inf = new Info(TargetDataLine.class, format);
			TargetDataLine microphone = (TargetDataLine) mics.getLine(inf);
			microphone.open();
			AudioInputStream aa = new AudioInputStream(microphone);
			return aa;
		}
		throw new Exception("No mic connected");
	}

	public static Vector<AudioFormat> getSuportedFormats(Mixer s) {
		Vector<AudioFormat> ret = new Vector<AudioFormat>();
		for (float frec = 600; frec < 60000; frec += 100) {
			for (int sample = 1; sample < 32; sample++) {
				AudioFormat format = new AudioFormat(frec, sample, 1, true,
						false);
				if (s.isLineSupported(new Info(TargetDataLine.class, format))) {
					ret.add(format);
					System.out.printf("%sHz : %s : \n", frec, sample);
				}
			}
		}
		return ret;
	}

	private static Mixer getMicMixers(int id) {
		int x = 0;
		for (Mixer.Info s : AudioSystem.getMixerInfo()) {
			if (s.getName().contains("Capture")) {
				x++;
			}
			if (x == id)
				return AudioSystem.getMixer(s);
		}
		return null;
	}
}
