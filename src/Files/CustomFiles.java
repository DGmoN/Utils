package Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import DataTypes.ByteConventions;
import DataTypes.ByteConventions.byteSegement;
import Formating.Misc;

public abstract class CustomFiles extends File {

	protected byte[] FileData;
	protected byteSegement FileHeader;

	private FileOutputStream Out;

	protected final int HEADERSIZE, HEADEROFSET;

	private boolean inits = false;

	public CustomFiles(String fileName, byte[] header) throws Exception {
		super(fileName);
		HEADERSIZE = header.length;
		HEADEROFSET = 0;
		Out = new FileOutputStream(this);
		for (byte s : header) {
			System.out.println("[CUSTOM_FILE][WRITING][HEADER][HEX] : "
					+ ByteConventions.byteToHex(s));
		}
		WriteToFile(header);
		inits = true;
	}

	public void WriteToFile(byte... a) {
		try {
			if (inits)
				System.out.println("[CUSTOM_FILE][WRITING][DATA][BIN] : "
						+ ByteConventions.toBinaryString(a));
			Out.write(a);
			Out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CustomFiles(String trgt, int HeaderSise, int headerOffset)
			throws Exception {
		super(trgt);
		HEADERSIZE = HeaderSise;
		InputStream READSTR = new URL("file:////" + getAbsolutePath())
				.openStream();
		FileData = new byte[(int) this.length()];
		HEADEROFSET = headerOffset;
		System.out.println("[CUSTOMFILES][CONSTRUCTOR#2][BYTESREAD] : "
				+ READSTR.read(FileData));
		FileHeader = new byteSegement(HEADEROFSET, HEADERSIZE, FileData);
	}

	public CustomFiles(File trgt, int HeaderSise, int headerOffset)
			throws Exception {
		super(trgt.getAbsolutePath());
		HEADERSIZE = HeaderSise;
		InputStream READSTR = new URL("file:////" + getAbsolutePath())
				.openStream();
		FileData = new byte[(int) this.length()];
		HEADEROFSET = headerOffset;
		System.out.println("[CUSTOMFILES][CONSTRUCTOR#3][BYTESREAD] : "
				+ READSTR.read(FileData));
		FileHeader = new byteSegement(HEADEROFSET, HEADERSIZE, FileData);
	}

	public CustomFiles(URL s, int i, int j) throws URISyntaxException {
		super(s.toURI());
		HEADERSIZE = i;
		HEADEROFSET = j;
	}
}
