package gh.piotrus.napierdalanie.transformer;

import java.util.function.Consumer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import pl.memexurer.siurtransformer.loader.file.TransformerFile;

public class PluginStringObfTransformer implements Consumer<TransformerFile> {

  public static String decrypt(String string, int value, ClassNode node, MethodNode decryptNode,
      MethodNode callerMethod, int constantPoolSize) {
    char[] cArray = string.toCharArray();
    char c = (char) ((char) ((char) (value ^ (char) decryptNode.name.hashCode())
        ^ (char) callerMethod.name.hashCode()) ^ (char) node.name.hashCode());
    try {
      c = (char) (c ^ (char) constantPoolSize);
    } catch (Exception ignored) {
    }
    char c3 = (char) (c >> 7);
    for (int n = 0; n < cArray.length; n = (char) (n + 1)) {
      cArray[n] = (char) (cArray[n] ^ c ^ n & c3);
    }
    return new String(cArray, 0, cArray.length - 1);
  }

  @Override
  public void accept(TransformerFile file) {
    for (TransformerFile.Class clazz : file.getClasses()) {
      for (MethodNode node1 : clazz.getParsedNode().methods) {
        for (AbstractInsnNode node2 : node1.instructions) {
          if (node2 instanceof MethodInsnNode) {
            if (((MethodInsnNode) node2).desc.equals("(Ljava/lang/String;)Ljava/lang/String;")) {
              MethodNode decryptionNode = DecryptionTransformer.findMethodNode(
                  clazz.getParsedNode(),
                  (MethodInsnNode) node2);

              String decrypted = decrypt((String) ((LdcInsnNode) node2.getPrevious()).cst,
                  NumberTransformer.getIntValue(decryptionNode.instructions.get(9)),
                  clazz.getParsedNode(),
                  decryptionNode,
                  node1, clazz.getClassReader().getItemCount());
              node1.instructions.remove(node2.getPrevious());
              node1.instructions.set(node2, new LdcInsnNode(decrypted));
            }
          }
        }
      }
    }
  }
}
