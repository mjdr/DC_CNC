package cnc.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public class IOUtils {

	public static byte[] writePackage(cnc.data.Package p) {
		byte[] data = new byte[2 * 5];

		writeShort(data, 0, p.type);
		writeShort(data, 2, p.x);
		writeShort(data, 4, p.y);
		writeShort(data, 6, p.z);
		writeShort(data, 8, p.checkSum);

		return data;

	}

	public static void writePackages(OutputStream stream, List<cnc.data.Package> packages) throws IOException {
		for (cnc.data.Package p : packages)
			stream.write(writePackage(p));
	}

	public static void writePackagesAsText(OutputStream stream, List<cnc.data.Package> packages) throws IOException {

		PrintWriter pw = new PrintWriter(stream);

		for (cnc.data.Package p : packages)
			pw.printf("%d %d %d %d %d\n", p.type, p.x, p.y, p.z, p.checkSum);

		pw.flush();
	}

	private static void writeShort(byte[] data, int pos, short value) {
		data[pos + 0] = (byte) (value & 0x00FF);
		data[pos + 1] = (byte) ((value & 0xFF00) >>> 8);
	}
}
