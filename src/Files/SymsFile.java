package Files;

import java.awt.Rectangle;

import DataTypes.ByteConventions.byteSegement;

public class SymsFile extends CustomFiles {

	public SymsFile(String fileName, byte[] header) throws Exception {
		super(fileName, header);
	}

	byteSegement charIndex, CharDimX, CharDimY, charData[];

	public SymsFile(String trgt) throws Exception {
		super(trgt, 6, 0);
		charIndex = new byteSegement(0, 2, FileHeader);
		CharDimX = new byteSegement(2, 2, FileHeader);
		CharDimY = new byteSegement(4, 2, FileHeader);
		buildCharData();
	}

	public int getCharIndex() {
		return charIndex.toInt();
	}

	public Rectangle getCharDimentions() {
		return new Rectangle(CharDimX.toInt(), CharDimY.toInt());
	}

	public byteSegement[] getCharData() {
		return charData;
	}

	private void buildCharData() {
		System.out.println("[SYMSFILE][BUILDINGCHARDATA] : START");
		charData = new byteSegement[charIndex.toInt()];
		int x = 0;
		while (x < charIndex.toInt()) {
			try {
				System.out
						.println("[SYMSFILE][BUILDINGCHARDATA][ATTEMPTING] : "
								+ x);
				charData[x] = new byteSegement(6 + ((CharDimX.toInt() / 8)
						* CharDimY.toInt() * x),
						((CharDimX.toInt() / 8) * CharDimY.toInt()), FileData);
				System.out
						.println("[SYMSFILE][BUILDINGCHARDATA][ADDED] : " + x);
				x++;

			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		System.out.println("[SYMSFILE][BUILDINGCHARDATA] : END");
	}

}
