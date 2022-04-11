package gh.piotrus.napierdalanie.transformer;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import pl.memexurer.siurtransformer.loader.file.TransformerFile;

public class ArrayUnpooler implements DecryptionTransformer {

  public static MethodNode findArrayEncryptMethod(ClassNode node, FieldInsnNode fieldInsnNode) {
    for (MethodNode node1 : node.methods) {
      for (AbstractInsnNode node2 : node1.instructions) {
        if (node2.getOpcode() == Opcodes.PUTSTATIC) {
          if (((FieldInsnNode) node2).name.equals(fieldInsnNode.name)
              && ((FieldInsnNode) node2).desc.equals(fieldInsnNode.desc)) {
            return node1;
          }
        }
      }
    }
    return null;
  }

  @Override
  public void decryptClass(TransformerFile file, ClassNode node) {
    boolean znalazlo;

    MethodNode decryptMethod = null;
    FieldNode decryptArray = null;

    do {
      znalazlo = false;

      for (MethodNode node1 : node.methods) {
        for (AbstractInsnNode node2 : node1.instructions) {
          if (node2.getOpcode() == Opcodes.GETSTATIC
              && NumberTransformer.isInteger(node2.getNext())
              && node2.getNext().getNext() != null
              && node2.getNext().getNext().getOpcode() == Opcodes.AALOAD) {
            int position = NumberTransformer.getIntValue(node2.getNext());

            String stringArrayValue = null;

            FieldInsnNode fieldInsnNode = (FieldInsnNode) node2;
            if (decryptMethod == null) {
              decryptMethod = findArrayEncryptMethod(node, fieldInsnNode);
              decryptArray = DecryptionTransformer.findFieldNode(node, fieldInsnNode);
            }

            for (AbstractInsnNode clinitNode : decryptMethod.instructions) {
              if (NumberTransformer.isInteger(clinitNode)
                  && clinitNode.getNext() instanceof LdcInsnNode
                  && clinitNode.getNext().getNext().getOpcode() == Opcodes.AASTORE) {
                if (NumberTransformer.getIntValue(clinitNode) == position) {
                  stringArrayValue = (String) ((LdcInsnNode) clinitNode.getNext()).cst;
                }
              }
            }

            if (stringArrayValue == null) {
              throw new IllegalArgumentException("Failed to find " + position + "@" + fieldInsnNode.owner);
            } else {
              node1.instructions.remove(node2.getNext().getNext());
              node1.instructions.remove(node2.getNext());
              node1.instructions.set(node2, new LdcInsnNode(stringArrayValue));

            }

            znalazlo = true;
          }
        }
      }
    } while (znalazlo);

    if (decryptMethod != null) {
      for(MethodNode methodNode: node.methods) {
        for (AbstractInsnNode insnNode : methodNode.instructions) {
          if (insnNode instanceof MethodInsnNode methodInsnNode && methodInsnNode.name.equals(
              decryptMethod.name)) {
            methodNode.instructions.remove(methodInsnNode);
          }
        }
      }
      node.methods.remove(decryptMethod);
      node.fields.remove(decryptArray);
    }
  }
}
