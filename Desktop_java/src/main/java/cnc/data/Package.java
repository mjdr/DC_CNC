package cnc.data;

public class Package {

	public static short TYPE_ABSOLUTE = 0;
	public static short TYPE_RELATIVE = 1;

	public short type;
	public short x, y, z;
	public short checkSum;

	public Package() {}

	public Package(short type, short x, short y, short z) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;

		updateCheckSum();
	}

	public void updateCheckSum() {
		checkSum = type;
		checkSum ^= x << 1;
		checkSum ^= y << 2;
		checkSum ^= z << 3;
	}

}
