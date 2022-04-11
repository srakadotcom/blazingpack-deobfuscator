package gh.piotrus.napierdalanie.transformer;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import pl.memexurer.siurtransformer.loader.file.TransformerFile;

public class IntObfuscationRemover implements DecryptionTransformer {

  private static final String DECRYPT_METHOD_DESCRIPTION = "(Ljava/lang/Class;I)I";

  @Override
  public void decryptClass(TransformerFile file, ClassNode node) { //won't work with loader classes
    for (MethodNode node1 : node.methods) {
      for (AbstractInsnNode node2 : node1.instructions) {
        if (node2 instanceof MethodInsnNode method) {
          if (method.desc.equals(DECRYPT_METHOD_DESCRIPTION)) {
            if (node2.getPrevious().getPrevious() instanceof LdcInsnNode ldc) {
              TransformerFile.Class clazz = file.resolveClass(
                  ((org.objectweb.asm.Type) ldc.cst).getInternalName());
              if (clazz == null) {
                System.out.println("Class not found!");
                continue;
              }

              int value = clazz.getClassReader().readInt(clazz.getClassReader()
                  .getItem(NumberTransformer.getIntValue(node2.getPrevious())));

              node1.instructions.remove(node2.getPrevious().getPrevious());
              node1.instructions.remove(node2.getPrevious());
              node1.instructions.set(node2, NumberTransformer.getNumber(value));
            }
          }
        }
      }
    }
  }

}
