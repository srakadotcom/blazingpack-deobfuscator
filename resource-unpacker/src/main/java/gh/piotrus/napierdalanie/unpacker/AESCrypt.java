/**
 * @url https://github.com/JetBrains/jdk8u_jdk/blob/master/src/share/classes/com/sun/crypto/provider/AESCrypt.java
  */

package gh.piotrus.napierdalanie.unpacker;

public class AESCrypt {
  private static int[] array746;
  private static final int[] array747;
  private int[] array748 = null;
  private static final int[] array749;
  private static final int[] array750;
  private static final byte[] array751;
  private static final byte[] array752;
  private static int[] array753;
  private static final int[] array754;
  private static final int[] array755;
  private static final int[] array756;
  private static final int[] array757;
  private static final byte[] array758;
  private static final int[] array759;

  private static int method1452(int n, int n2) {
    return n != 0 && n2 != 0 ? array746[(array753[n & 0xFF] + array753[n2 & 0xFF]) % 255] : 0;
  }

  private static int method1453(int n, byte[] byArray) {
    if (n == 0) {
      return 0;
    }
    n = array753[n & 0xFF];
    int n2 = byArray[0] != 0 ? array746[(n + array753[byArray[0] & 0xFF]) % 255] & 0xFF : 0;
    int n3 = byArray[1] != 0 ? array746[(n + array753[byArray[1] & 0xFF]) % 255] & 0xFF : 0;
    int n4 = byArray[2] != 0 ? array746[(n + array753[byArray[2] & 0xFF]) % 255] & 0xFF : 0;
    int n5 = byArray[3] != 0 ? array746[(n + array753[byArray[3] & 0xFF]) % 255] & 0xFF : 0;
    return n2 << 24 | n3 << 16 | n4 << 8 | n5;
  }

  public void doBlock(byte[] byArray, int n, byte[] byArray2, int n2) {
    int n3 = 4;
    int n4 = (byArray[n++] << 24 | (byArray[n++] & 0xFF) << 16 | (byArray[n++] & 0xFF) << 8 | byArray[n++] & 0xFF) ^ this.array748[n3++];
    int n5 = (byArray[n++] << 24 | (byArray[n++] & 0xFF) << 16 | (byArray[n++] & 0xFF) << 8 | byArray[n++] & 0xFF) ^ this.array748[n3++];
    int n6 = (byArray[n++] << 24 | (byArray[n++] & 0xFF) << 16 | (byArray[n++] & 0xFF) << 8 | byArray[n++] & 0xFF) ^ this.array748[n3++];
    int n7 = (byArray[n++] << 24 | (byArray[n++] & 0xFF) << 16 | (byArray[n++] & 0xFF) << 8 | byArray[n] & 0xFF) ^ this.array748[n3++];
    int n8 = array757[n4 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n6 >>> 8 & 0xFF] ^ array750[n5 & 0xFF] ^ this.array748[n3++];
    int n9 = array757[n5 >>> 24] ^ array747[n4 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n6 & 0xFF] ^ this.array748[n3++];
    int n10 = array757[n6 >>> 24] ^ array747[n5 >>> 16 & 0xFF] ^ array755[n4 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n6 >>> 16 & 0xFF] ^ array755[n5 >>> 8 & 0xFF] ^ array750[n4 & 0xFF] ^ this.array748[n3++];
    n4 = array757[n8 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n10 >>> 8 & 0xFF] ^ array750[n9 & 0xFF] ^ this.array748[n3++];
    n5 = array757[n9 >>> 24] ^ array747[n8 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n10 & 0xFF] ^ this.array748[n3++];
    n6 = array757[n10 >>> 24] ^ array747[n9 >>> 16 & 0xFF] ^ array755[n8 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n10 >>> 16 & 0xFF] ^ array755[n9 >>> 8 & 0xFF] ^ array750[n8 & 0xFF] ^ this.array748[n3++];
    n8 = array757[n4 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n6 >>> 8 & 0xFF] ^ array750[n5 & 0xFF] ^ this.array748[n3++];
    n9 = array757[n5 >>> 24] ^ array747[n4 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n6 & 0xFF] ^ this.array748[n3++];
    n10 = array757[n6 >>> 24] ^ array747[n5 >>> 16 & 0xFF] ^ array755[n4 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n6 >>> 16 & 0xFF] ^ array755[n5 >>> 8 & 0xFF] ^ array750[n4 & 0xFF] ^ this.array748[n3++];
    n4 = array757[n8 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n10 >>> 8 & 0xFF] ^ array750[n9 & 0xFF] ^ this.array748[n3++];
    n5 = array757[n9 >>> 24] ^ array747[n8 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n10 & 0xFF] ^ this.array748[n3++];
    n6 = array757[n10 >>> 24] ^ array747[n9 >>> 16 & 0xFF] ^ array755[n8 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n10 >>> 16 & 0xFF] ^ array755[n9 >>> 8 & 0xFF] ^ array750[n8 & 0xFF] ^ this.array748[n3++];
    n8 = array757[n4 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n6 >>> 8 & 0xFF] ^ array750[n5 & 0xFF] ^ this.array748[n3++];
    n9 = array757[n5 >>> 24] ^ array747[n4 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n6 & 0xFF] ^ this.array748[n3++];
    n10 = array757[n6 >>> 24] ^ array747[n5 >>> 16 & 0xFF] ^ array755[n4 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n6 >>> 16 & 0xFF] ^ array755[n5 >>> 8 & 0xFF] ^ array750[n4 & 0xFF] ^ this.array748[n3++];
    n4 = array757[n8 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n10 >>> 8 & 0xFF] ^ array750[n9 & 0xFF] ^ this.array748[n3++];
    n5 = array757[n9 >>> 24] ^ array747[n8 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n10 & 0xFF] ^ this.array748[n3++];
    n6 = array757[n10 >>> 24] ^ array747[n9 >>> 16 & 0xFF] ^ array755[n8 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n10 >>> 16 & 0xFF] ^ array755[n9 >>> 8 & 0xFF] ^ array750[n8 & 0xFF] ^ this.array748[n3++];
    n8 = array757[n4 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n6 >>> 8 & 0xFF] ^ array750[n5 & 0xFF] ^ this.array748[n3++];
    n9 = array757[n5 >>> 24] ^ array747[n4 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n6 & 0xFF] ^ this.array748[n3++];
    n10 = array757[n6 >>> 24] ^ array747[n5 >>> 16 & 0xFF] ^ array755[n4 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n6 >>> 16 & 0xFF] ^ array755[n5 >>> 8 & 0xFF] ^ array750[n4 & 0xFF] ^ this.array748[n3++];
    n4 = array757[n8 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n10 >>> 8 & 0xFF] ^ array750[n9 & 0xFF] ^ this.array748[n3++];
    n5 = array757[n9 >>> 24] ^ array747[n8 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n10 & 0xFF] ^ this.array748[n3++];
    n6 = array757[n10 >>> 24] ^ array747[n9 >>> 16 & 0xFF] ^ array755[n8 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n10 >>> 16 & 0xFF] ^ array755[n9 >>> 8 & 0xFF] ^ array750[n8 & 0xFF] ^ this.array748[n3++];
    n8 = array757[n4 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n6 >>> 8 & 0xFF] ^ array750[n5 & 0xFF] ^ this.array748[n3++];
    n9 = array757[n5 >>> 24] ^ array747[n4 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n6 & 0xFF] ^ this.array748[n3++];
    n10 = array757[n6 >>> 24] ^ array747[n5 >>> 16 & 0xFF] ^ array755[n4 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n6 >>> 16 & 0xFF] ^ array755[n5 >>> 8 & 0xFF] ^ array750[n4 & 0xFF] ^ this.array748[n3++];
    n4 = array757[n8 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n10 >>> 8 & 0xFF] ^ array750[n9 & 0xFF] ^ this.array748[n3++];
    n5 = array757[n9 >>> 24] ^ array747[n8 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n10 & 0xFF] ^ this.array748[n3++];
    n6 = array757[n10 >>> 24] ^ array747[n9 >>> 16 & 0xFF] ^ array755[n8 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n10 >>> 16 & 0xFF] ^ array755[n9 >>> 8 & 0xFF] ^ array750[n8 & 0xFF] ^ this.array748[n3++];
    n8 = array757[n4 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n6 >>> 8 & 0xFF] ^ array750[n5 & 0xFF] ^ this.array748[n3++];
    n9 = array757[n5 >>> 24] ^ array747[n4 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n6 & 0xFF] ^ this.array748[n3++];
    n10 = array757[n6 >>> 24] ^ array747[n5 >>> 16 & 0xFF] ^ array755[n4 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n6 >>> 16 & 0xFF] ^ array755[n5 >>> 8 & 0xFF] ^ array750[n4 & 0xFF] ^ this.array748[n3++];
    n4 = array757[n8 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n10 >>> 8 & 0xFF] ^ array750[n9 & 0xFF] ^ this.array748[n3++];
    n5 = array757[n9 >>> 24] ^ array747[n8 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n10 & 0xFF] ^ this.array748[n3++];
    n6 = array757[n10 >>> 24] ^ array747[n9 >>> 16 & 0xFF] ^ array755[n8 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n10 >>> 16 & 0xFF] ^ array755[n9 >>> 8 & 0xFF] ^ array750[n8 & 0xFF] ^ this.array748[n3++];
    n8 = array757[n4 >>> 24] ^ array747[n7 >>> 16 & 0xFF] ^ array755[n6 >>> 8 & 0xFF] ^ array750[n5 & 0xFF] ^ this.array748[n3++];
    n9 = array757[n5 >>> 24] ^ array747[n4 >>> 16 & 0xFF] ^ array755[n7 >>> 8 & 0xFF] ^ array750[n6 & 0xFF] ^ this.array748[n3++];
    n10 = array757[n6 >>> 24] ^ array747[n5 >>> 16 & 0xFF] ^ array755[n4 >>> 8 & 0xFF] ^ array750[n7 & 0xFF] ^ this.array748[n3++];
    n7 = array757[n7 >>> 24] ^ array747[n6 >>> 16 & 0xFF] ^ array755[n5 >>> 8 & 0xFF] ^ array750[n4 & 0xFF] ^ this.array748[n3++];
    n5 = this.array748[0];
    byArray2[n2++] = (byte)(array758[n8 >>> 24] ^ n5 >>> 24);
    byArray2[n2++] = (byte)(array758[n7 >>> 16 & 0xFF] ^ n5 >>> 16);
    byArray2[n2++] = (byte)(array758[n10 >>> 8 & 0xFF] ^ n5 >>> 8);
    byArray2[n2++] = (byte)(array758[n9 & 0xFF] ^ n5);
    n5 = this.array748[1];
    byArray2[n2++] = (byte)(array758[n9 >>> 24] ^ n5 >>> 24);
    byArray2[n2++] = (byte)(array758[n8 >>> 16 & 0xFF] ^ n5 >>> 16);
    byArray2[n2++] = (byte)(array758[n7 >>> 8 & 0xFF] ^ n5 >>> 8);
    byArray2[n2++] = (byte)(array758[n10 & 0xFF] ^ n5);
    n5 = this.array748[2];
    byArray2[n2++] = (byte)(array758[n10 >>> 24] ^ n5 >>> 24);
    byArray2[n2++] = (byte)(array758[n9 >>> 16 & 0xFF] ^ n5 >>> 16);
    byArray2[n2++] = (byte)(array758[n8 >>> 8 & 0xFF] ^ n5 >>> 8);
    byArray2[n2++] = (byte)(array758[n7 & 0xFF] ^ n5);
    n5 = this.array748[3];
    byArray2[n2++] = (byte)(array758[n7 >>> 24] ^ n5 >>> 24);
    byArray2[n2++] = (byte)(array758[n10 >>> 16 & 0xFF] ^ n5 >>> 16);
    byArray2[n2++] = (byte)(array758[n9 >>> 8 & 0xFF] ^ n5 >>> 8);
    byArray2[n2] = (byte)(array758[n8 & 0xFF] ^ n5);
  }

  public AESCrypt(byte[] byArray) {
    int n;
    int[][] nArray = new int[15][4];
    int[] nArray2 = new int[8];
    int n2 = 0;
    int n3 = 0;
    while (n2 < 8) {
      nArray2[n2] = byArray[n3] << 24 | (byArray[n3 + 1] & 0xFF) << 16 | (byArray[n3 + 2] & 0xFF) << 8 | byArray[n3 + 3] & 0xFF;
      ++n2;
      n3 += 4;
    }
    int n4 = 0;
    for (n3 = 0; n3 < 8 && n4 < 60; ++n3, ++n4) {
      nArray[14 - n4 / 4][n4 % 4] = nArray2[n3];
    }
    int n5 = 0;
    while (n4 < 60) {
      n = nArray2[7];
      nArray2[0] = nArray2[0] ^ (array751[n >>> 16 & 0xFF] << 24 ^ (array751[n >>> 8 & 0xFF] & 0xFF) << 16 ^ (array751[n & 0xFF] & 0xFF) << 8 ^ array751[n >>> 24] & 0xFF ^ array752[n5++] << 24);
      n2 = 1;
      n3 = 0;
      while (n2 < 4) {
        int n6 = n2++;
        nArray2[n6] = nArray2[n6] ^ nArray2[n3];
        ++n3;
      }
      n = nArray2[3];
      nArray2[4] = nArray2[4] ^ (array751[n & 0xFF] & 0xFF ^ (array751[n >>> 8 & 0xFF] & 0xFF) << 8 ^ (array751[n >>> 16 & 0xFF] & 0xFF) << 16 ^ array751[n >>> 24] << 24);
      n3 = 4;
      n2 = n3 + 1;
      while (n2 < 8) {
        int n7 = n2++;
        nArray2[n7] = nArray2[n7] ^ nArray2[n3];
        ++n3;
      }
      for (n3 = 0; n3 < 8 && n4 < 60; ++n3, ++n4) {
        nArray[14 - n4 / 4][n4 % 4] = nArray2[n3];
      }
    }
    for (int i = 1; i < 14; ++i) {
      for (n3 = 0; n3 < 4; ++n3) {
        n = nArray[i][n3];
        nArray[i][n3] = array749[n >>> 24 & 0xFF] ^ array754[n >>> 16 & 0xFF] ^ array759[n >>> 8 & 0xFF] ^ array756[n & 0xFF];
      }
    }
    this.array748 = method3189(nArray);
  }

  public static int[] method3189(int[][] nArray) {
    int n;
    int n2 = nArray.length;
    int[] nArray2 = new int[n2 * 4];
    for (n = 0; n < 4; ++n) {
      nArray2[n] = nArray[n2 - 1][n];
    }
    for (n = 1; n < n2; ++n) {
      System.arraycopy(nArray[n - 1], 0, nArray2, n * 4, 4);
    }
    return nArray2;
  }

  static {
    int n;
    int n2;
    array746 = new int[256];
    array753 = new int[256];
    array751 = new byte[256];
    array758 = new byte[256];
    array757 = new int[256];
    array747 = new int[256];
    array755 = new int[256];
    array750 = new int[256];
    array749 = new int[256];
    array754 = new int[256];
    array759 = new int[256];
    array756 = new int[256];
    array752 = new byte[30];
    int n3 = 283;
    int n4 = 0;
    AESCrypt.array746[0] = 1;
    for (n2 = 1; n2 < 256; ++n2) {
      n4 = array746[n2 - 1] << 1 ^ array746[n2 - 1];
      if ((n4 & 0x100) != 0) {
        n4 ^= n3;
      }
      AESCrypt.array746[n2] = n4;
    }
    for (n2 = 1; n2 < 255; ++n2) {
      AESCrypt.array753[AESCrypt.array746[n2]] = n2;
    }
    byte[][] byArrayArray = new byte[][]{{1, 1, 1, 1, 1, 0, 0, 0}, {0, 1, 1, 1, 1, 1, 0, 0}, {0, 0, 1, 1, 1, 1, 1, 0}, {0, 0, 0, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 1, 1, 1, 1}, {1, 1, 0, 0, 0, 1, 1, 1}, {1, 1, 1, 0, 0, 0, 1, 1}, {1, 1, 1, 1, 0, 0, 0, 1}};
    byte[] byArray = new byte[]{0, 1, 1, 0, 0, 0, 1, 1};
    byte[][] byArray2 = new byte[256][8];
    byArray2[1][7] = 1;
    for (n2 = 2; n2 < 256; ++n2) {
      n4 = array746[255 - array753[n2]];
      for (n = 0; n < 8; ++n) {
        byArray2[n2][n] = (byte)(n4 >>> 7 - n & 1);
      }
    }
    byte[][] byArray3 = new byte[256][8];
    for (n2 = 0; n2 < 256; ++n2) {
      for (n = 0; n < 8; ++n) {
        byArray3[n2][n] = byArray[n];
        for (n4 = 0; n4 < 8; ++n4) {
          byte[] byArray4 = byArray3[n2];
          int n5 = n;
          byArray4[n5] = (byte)(byArray4[n5] ^ byArrayArray[n][n4] * byArray2[n2][n4]);
        }
      }
    }
    for (n2 = 0; n2 < 256; ++n2) {
      AESCrypt.array751[n2] = (byte)(byArray3[n2][0] << 7);
      for (n = 1; n < 8; ++n) {
        int n6 = n2;
        array751[n6] = (byte)(array751[n6] ^ byArray3[n2][n] << 7 - n);
      }
      AESCrypt.array758[AESCrypt.array751[n2] & 0xFF] = (byte)n2;
    }
    byte[][] byArrayArray2 = new byte[][]{{2, 1, 1, 3}, {3, 2, 1, 1}, {1, 3, 2, 1}, {1, 1, 3, 2}};
    byte[][] byArray5 = new byte[4][8];
    for (n2 = 0; n2 < 4; ++n2) {
      for (n4 = 0; n4 < 4; ++n4) {
        byArray5[n2][n4] = byArrayArray2[n2][n4];
      }
      byArray5[n2][n2 + 4] = 1;
    }
    byte[][] byArray6 = new byte[4][4];
    for (n2 = 0; n2 < 4; ++n2) {
      byte by = byArray5[n2][n2];
      if (by == 0) {
        for (n = n2 + 1; byArray5[n][n2] == 0 && n < 4; ++n) {
        }
        if (n == 4) {
          throw new RuntimeException("G matrix is not invertible");
        }
        for (n4 = 0; n4 < 8; ++n4) {
          byte by2 = byArray5[n2][n4];
          byArray5[n2][n4] = byArray5[n][n4];
          byArray5[n][n4] = by2;
        }
        by = byArray5[n2][n2];
      }
      for (n4 = 0; n4 < 8; ++n4) {
        if (byArray5[n2][n4] == 0) continue;
        byArray5[n2][n4] = (byte)array746[(255 + array753[byArray5[n2][n4] & 0xFF] - array753[by & 0xFF]) % 255];
      }
      for (n = 0; n < 4; ++n) {
        if (n2 == n) continue;
        for (n4 = n2 + 1; n4 < 8; ++n4) {
          byte[] byArray7 = byArray5[n];
          int n7 = n4;
          byArray7[n7] = (byte)(byArray7[n7] ^ AESCrypt.method1452(byArray5[n2][n4], byArray5[n][n2]));
        }
        byArray5[n][n2] = 0;
      }
    }
    for (n2 = 0; n2 < 4; ++n2) {
      for (n4 = 0; n4 < 4; ++n4) {
        byArray6[n2][n4] = byArray5[n2][n4 + 4];
      }
    }
    for (n = 0; n < 256; ++n) {
      byte by = array758[n];
      AESCrypt.array757[n] = AESCrypt.method1453(by, byArray6[0]);
      AESCrypt.array747[n] = AESCrypt.method1453(by, byArray6[1]);
      AESCrypt.array755[n] = AESCrypt.method1453(by, byArray6[2]);
      AESCrypt.array750[n] = AESCrypt.method1453(by, byArray6[3]);
      AESCrypt.array749[n] = AESCrypt.method1453(n, byArray6[0]);
      AESCrypt.array754[n] = AESCrypt.method1453(n, byArray6[1]);
      AESCrypt.array759[n] = AESCrypt.method1453(n, byArray6[2]);
      AESCrypt.array756[n] = AESCrypt.method1453(n, byArray6[3]);
    }
    AESCrypt.array752[0] = 1;
    int n8 = 1;
    for (n = 1; n < 30; ++n) {
      n8 = AESCrypt.method1452(2, n8);
      AESCrypt.array752[n] = (byte)n8;
    }
    array753 = null;
    array746 = null;
  }
}
