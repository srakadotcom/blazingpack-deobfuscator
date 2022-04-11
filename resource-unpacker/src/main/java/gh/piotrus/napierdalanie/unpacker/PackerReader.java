package gh.piotrus.napierdalanie.unpacker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackerReader
    extends InputStream {
  private static final String FILE_IDENTIFIER = "Skoro tu jeste\u015B to mi\u0142o";

  private static final List<Character> characters = new ArrayList<>();

  static {
    char c;
    for (c = 'a'; c <= 'z'; c = (char) (c + '\u0001')) {
      characters.add(c);
    }
    for (c = 'A'; c <= 'Z'; c = (char) (c + '\u0001')) {
      characters.add(c);
    }
  }

  private final Map<String, byte[]> map171 = new HashMap<>();
  private final Set<String> set173 = new HashSet<>();
  private final byte[] array172 = new byte[16];
  private final boolean boolean175;
  private final AESCrypt aesBlock;
  private final Random field178;
  private InputStream field176;
  private int int179 = 16;

  public PackerReader(File file, int seed, byte[] key) {
    this.field178 = new Random(seed);
    this.boolean175 = true;;
    this.aesBlock = new AESCrypt(key);

    init(file);

    this.field176 = this.method293();
  }

  private void init(File file) {
    try {
      ZipFile class258 = new ZipFile(file);
      Enumeration<? extends ZipEntry> enumeration = class258.entries();

      ZipEntry entry;
      while (enumeration.hasMoreElements()) {
        entry = enumeration.nextElement();
        if (entry.getName().contains(FILE_IDENTIFIER)) { //Ta? To zajebiscie...
          int n;
          ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

          InputStream inputStream = class258.getInputStream(entry);
          inputStream.skip(10);

          byte[] byArray = new byte[8192];
          while ((n = inputStream.read(byArray)) != -1) {
            byteArrayOutputStream.write(byArray, 0, n);
          }

          String[] splitted = entry.getName().split("\\.");
          map171.put(splitted[splitted.length - 2], byteArrayOutputStream.toByteArray());
        }
      }
      class258.close();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  private InputStream method293() {
    String string = characters.get(this.field178.nextInt(characters.size())) + "";
    while (this.set173.contains(string)) {
      string = string + characters.get(this.field178.nextInt(characters.size()));
    }
    this.set173.add(string);
    return new ByteArrayInputStream(map171.get(string));
  }

  @Override
  public int read() throws IOException {
    if (this.boolean175) {
      int n = this.method296(this.array172, 0, 1);
      if (n != 1) {
        return -1;
      }
      return this.array172[0] & 0xFF;
    }
    if (this.getBoolean294()) {
      return -1;
    }
    return this.array172[this.int179++] & 0xFF;
  }

  private boolean getBoolean294() throws IOException {
    int n;
    if (this.int179 != 16) {
      return false;
    }
    for (int i = 0; i < 16; i += n) {
      n = this.method296(this.array172, i, 16 - i);
      if (n >= 0) {
        continue;
      }
      return true;
    }

    this.aesBlock.doBlock(this.array172, 0, this.array172, 0);
    this.int179 = 0;
    return false;
  }

  public int method296(byte[] byArray, int n, int n2) throws IOException {
    if (this.field176 == null) {
      return -1;
    }
    int n3 = this.field176.read(byArray, n, n2);
    while (n3 == -1) {
      this.field176 = this.method293();
      n3 = this.field176.read(byArray, n, n2);
    }
    return n3;
  }

  @Override
  public int read(byte[] byArray, int n, int n2) throws IOException {
    if (this.boolean175) {
      return this.method296(byArray, n, n2);
    }
    if (byArray == null) {
      throw new NullPointerException();
    }
    if (n < 0 || n2 < 0 || n2 > byArray.length - n) {
      throw new IndexOutOfBoundsException();
    }
    if (this.getBoolean294()) {
      return -1;
    }
    int n3 = n2;
    while (this.int179 < 16 && n2 > 0) {
      byArray[n++] = this.array172[this.int179++];
      --n2;
    }
    while (n2 >= 16 && !this.getBoolean294()) {
      System.arraycopy(this.array172, 0, byArray, n, 16);
      n += 16;
      n2 -= 16;
      this.int179 = 16;
    }
    if (n2 < 16 && n2 > 0 && !this.getBoolean294()) {
      System.arraycopy(this.array172, 0, byArray, n, n2);
      this.int179 = n2;
      n2 = 0;
    }
    return n3 - n2;
  }
}
