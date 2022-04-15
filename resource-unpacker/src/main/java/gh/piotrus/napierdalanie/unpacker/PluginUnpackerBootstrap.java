package gh.piotrus.napierdalanie.unpacker;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

public class PluginUnpackerBootstrap {

  public static void main(String[] args) throws Throwable {
    ZipFile file = new ZipFile("in_plugin.jar");

    long l;
    InputStream object = file.getInputStream(file.getEntry("\u0000\u0000.class"));
    for (long i = 0L; i < 8L && (l = object.skip(8L - i)) > 0L; i += l) {
    }
    Object object2 = Cipher.getInstance("AES");
    ((Cipher) object2).init(2, new SecretKeySpec(
        "x\u0000x15\u0000x0ah{A]\u0000x1d/\u0000x1b2[X@}K0eQ;P]\u0000x1d/\u0000x".getBytes(), 0, 16,
        "AES"));
    object = new CipherInputStream(object, (Cipher) object2);
    object = new ZipInputStream(object);
    ((ZipInputStream) object).getNextEntry();
    object2 = new File("native/plugin_native");
    Files.copy(object, ((File) object2).toPath(),
        StandardCopyOption.REPLACE_EXISTING);
  }
}
