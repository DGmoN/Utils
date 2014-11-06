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
		int charDataLenth = CharDimX.toInt() / 8 * CharDimY.toInt();
		System.out.println("[SYMSFILE][BUILDINGCHARDATA] : START");
		charData = new byteSegement[charIndex.toInt()];
		int x = 0;
		while (x < charIndex.toInt()) {
			try {
				charData[x] = new byteSegement(6 + (charDataLenth * x)+(x*2),
						charDataLenth+2, FileData);
				x++;
				
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
			System.out
			.println("[SYMSFILE][BUILDINGCHARDATA][PROSSESING] : "
					+ (((float)x/this.length())*1000)+"%");
		}
		System.out.println("[SYMSFILE][BUILDINGCHARDATA] : DONE");
	}

}
