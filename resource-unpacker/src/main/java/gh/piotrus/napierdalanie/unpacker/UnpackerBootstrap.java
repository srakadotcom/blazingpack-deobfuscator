package gh.piotrus.napierdalanie.unpacker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.ZipInputStream;

public class UnpackerBootstrap {

  public static void main(String[] args) throws Throwable {
    PackerReader packerReader = new PackerReader(
        new File("in.jar"),
        -724180845,
        "d\u0000x10\u0000x0eQ;Z]\u0000x1d/\u0000x1b2[X@'K0eQ;Z]\u0000x1d/\u0000x".getBytes());

    try (ZipInputStream inputStream = new ZipInputStream(packerReader)) {
      inputStream.getNextEntry();

      int[] nArray = new int[5];
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      byte[] byArray = new byte[16384];

      int n3;
      while ((n3 = inputStream.read(byArray)) != -1) {
        byteArrayOutputStream.write(byArray, 0, n3);
      }
      byte[] byArray2 = byteArrayOutputStream.toByteArray();
      ByteBuffer byteBuffer = ByteBuffer.wrap(byArray2, byArray2.length - 20, 20);
      byteBuffer.asIntBuffer().get(nArray);

      new File("native").mkdir();

      for (int i = 0; i < 15; i++) {
        inputStream.getNextEntry();
        write(inputStream, "native/" + i + ".native", i,
            nArray[i / 3], byArray2);
      }
    }
  }

  private static void write(InputStream stream, String fileName, int n, int n2, byte[] byArray)
      throws IOException {
    int n3;
    byte[] byArray2 = new byte[8192];
    FileOutputStream fileOutputStream = new FileOutputStream(fileName);
    if (n == 0) {
      int n4;
      for (n4 = n2;
          n4 > 0 && (n3 = stream.read(byArray2, 0, Math.min(n4, byArray2.length))) != -1;
          n4 -= n3) {
        fileOutputStream.write(byArray2, 0, n3);
      }
      if (n4 != 0) {
        throw new IOException("eof: " + n4);
      }
      fileOutputStream.write(byArray, 0, byArray.length - 20);
    }
    while ((n3 = stream.read(byArray2, 0, byArray2.length)) != -1) {
      fileOutputStream.write(byArray2, 0, n3);
    }
    fileOutputStream.close();
  }
}
