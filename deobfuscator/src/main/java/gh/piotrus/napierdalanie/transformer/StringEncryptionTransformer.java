package gh.piotrus.napierdalanie.transformer;

import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import pl.memexurer.siurtransformer.loader.file.TransformerFile;

public class StringEncryptionTransformer implements DecryptionTransformer {

  private static final String DECRYPT_METHOD_DESCRIPTION = "(Ljava/lang/String;)Ljava/lang/String;";

  public static int getIntValue(AbstractInsnNode node) {
    if (node.getOpcode() >= Opcodes.ICONST_M1
        && node.getOpcode() <= Opcodes.ICONST_5) {
      return node.getOpcode() - 3;
    }
    if (node.getOpcode() == Opcodes.SIPUSH
        || node.getOpcode() == Opcodes.BIPUSH) {
      return ((IntInsnNode) node).operand;
    }
    if (node instanceof LdcInsnNode ldc) {
      if (ldc.cst instanceof Integer) {
        return (int) ldc.cst;
      }
    }
    throw new IllegalArgumentException("Invalid node!");
  }

  public static boolean isInteger(AbstractInsnNode ain) {
    if (ain == null) {
      return false;
    }
    if ((ain.getOpcode() >= Opcodes.ICONST_M1
        && ain.getOpcode() <= Opcodes.ICONST_5)
        || ain.getOpcode() == Opcodes.SIPUSH
        || ain.getOpcode() == Opcodes.BIPUSH) {
      return true;
    }
    if (ain instanceof LdcInsnNode ldc) {
      return ldc.cst instanceof Integer;
    }
    return false;
  }

  @Override
  public void decryptClass(TransformerFile file, ClassNode node) {
    Map<Integer, DecryptMethod> methodMap = new HashMap<>();

    for (MethodNode currentMethod : node.methods) {
      for (AbstractInsnNode node2 : currentMethod.instructions) {
        if (node2 instanceof MethodInsnNode node2Method) {
          if (node2Method.desc.equals(DECRYPT_METHOD_DESCRIPTION)) {
            MethodNode node3 = DecryptionTransformer.findMethodNode(file, node2Method);
            if (node3 == null)
              continue;

            String encrypted = "";

            if (node2.getPrevious() instanceof LdcInsnNode) {
              encrypted = (String) ((LdcInsnNode) node2.getPrevious()).cst;
              currentMethod.instructions.remove(node2.getPrevious());
            } else {
              int position;
              try {
                position = getIntValue(node2.getPrevious().getPrevious());
              } catch (IllegalArgumentException invalid) {
                continue;
              }

              FieldInsnNode fieldInsnNode = (FieldInsnNode) node2.getPrevious().getPrevious()
                  .getPrevious();
              MethodNode clinit = ArrayUnpooler.findArrayEncryptMethod(
                  file.resolveClass(fieldInsnNode.owner).getParsedNode(), fieldInsnNode);
              for (AbstractInsnNode clinitNode : clinit.instructions) {
                if (isInteger(clinitNode)
                    && clinitNode.getNext() instanceof LdcInsnNode
                    && clinitNode.getNext().getNext().getOpcode() == Opcodes.AASTORE) {
                  if (getIntValue(clinitNode) == position) {
                    encrypted = (String) ((LdcInsnNode) clinitNode.getNext()).cst;
                    currentMethod.instructions.remove(
                        node2.getPrevious().getPrevious().getPrevious());
                    currentMethod.instructions.remove(node2.getPrevious().getPrevious());
                    break;
                  }
                }
              }
            }

            if (encrypted != null) {
              DecryptMethod method = methodMap.get(node2Method.name.hashCode());
              if (method == null) {
                try {
                  methodMap.put(node2Method.name.hashCode(),
                      method = DecryptMethod.createMethod(node3));
                } catch (IllegalArgumentException invalid) {
                  continue;
                }
              }

              String decrypted = method.decrypt(encrypted,
                  node2Method.name, currentMethod.name, node.name.replace('/', '.'));
              currentMethod.instructions.set(node2, new LdcInsnNode(decrypted));
            }
          }
        }
      }
    }

    for(DecryptMethod method: methodMap.values()) {
      node.methods.removeIf(methodNode -> methodNode.name.equals(method.name));
    }
  }


  private static class DecryptMethod {

    private final int key1, key2;
    private final String name;

    private DecryptMethod(int key1, int key2, String name) {
      this.key1 = key1;
      this.key2 = key2;
      this.name = name;
    }

    public static DecryptMethod createMethod(MethodNode method) {
      return new DecryptMethod(
          getIntValue(method.instructions.get(0)),
          getIntValue(method.instructions.get(40)),
          method.name);
    }

    public String decrypt(String str, String methodName0, String methodName1, String className1) {
      int n = this.key1;
      char[] arrc = str.toCharArray();
      n = (char) (n ^ (char) methodName0.hashCode());
      n = (char) (n ^ (char) methodName1.hashCode());
      n = (char) (n ^ (char) className1.hashCode());
      char c = (char) (this.key2);
      n = (char) (n ^ c);
      char c2 = (char) (n >> 7);
      int n2 = 0;
      while (n2 < arrc.length) {
        arrc[n2] = (char) (arrc[n2] ^ n ^ n2 & c2);
        n2 = (char) (n2 + 1);
      }
      return new String(arrc);
    }
  }
}
