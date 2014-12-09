package crypto;

import java.lang.annotation.Documented;
import java.util.Random;

import DataTypes.ByteConventions;

public class Encryption {

	static Random gen = new Random();

	public static byte[] genPGPSessionKey() {
		gen.setSeed(System.currentTimeMillis());
		byte[] ret = new byte[64];
		gen.nextBytes(ret);
		System.out.println("Key generated : "
				+ ByteConventions.bytesToHexes(ret));

		return ret;
	}

	// data cannot be longer than 255

	public static byte[] incrypt(byte[] key, byte[] data) {
		byte[] ret = new byte[255];
		System.out.println("Key = " + ByteConventions.bytesToHexes(key));
		System.out.println("Data = " + ByteConventions.bytesToHexes(data));
		int x = 0;
		while (x < data.length) {
			for (int y = 0; y < key.length; y++) {
				if (x == 254)
					return ret;
				ret[x] = (byte) (key[y] & data[x]);
				x++;
			}
		}
		System.out.println("Result = " + ByteConventions.bytesToHexes(ret));
		return ret;
	}

	public static byte[] decrypt(byte[] key, byte[] data) {
		byte[] ret = new byte[255];
		System.out.println("Key = " + ByteConventions.bytesToHexes(key));
		System.out.println("Data = " + ByteConventions.bytesToHexes(data));
		int x = 0;
		while (x < data.length) {
			for (int y = 0; y < key.length; y++) {
				if (x == 254)
					return ret;
				ret[x] = (byte) (~key[y] & data[x]);
				x++;
			}
		}
		System.out.println("Result = " + ByteConventions.bytesToHexes(ret));
		return ret;
	}
}
