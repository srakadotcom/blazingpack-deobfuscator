package gh.piotrus.napierdalanie.transformer;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import pl.memexurer.siurtransformer.loader.file.TransformerFile;

public class ClinitFolder implements DecryptionTransformer {

    private MethodNode findDestinationMethod(TransformerFile file, MethodNode node) {
      if (node == null || node.instructions.size() == 0) return null;
      AbstractInsnNode firstNode = node.instructions.getFirst() instanceof LabelNode ? node.instructions.getFirst().getNext() : node.instructions.getFirst();
      if(!(firstNode instanceof MethodInsnNode))
        return null;

      if (node.instructions.size() <= 3) {
        MethodNode node1 = DecryptionTransformer.findMethodNode(file, (MethodInsnNode) firstNode);
        if (node.instructions.size() <= 3)
          return node1;
        else
          return findDestinationMethod(file, node1);
      }

      return null;
    }

  @Override
  public void decryptClass(TransformerFile file, ClassNode node) {
    MethodNode clinit = DecryptionTransformer.findClinit(node);

    boolean modified;

    int i = 0;
    do {
      modified = false;

      MethodNode node1 = findDestinationMethod(file, clinit);
      if (node1 != null) {
        clinit.instructions.clear();
        clinit.instructions.insert(node1.instructions);
        node.methods.remove(node1);
        modified = true;
      }
    } while (modified);
  }
}
