package gh.piotrus.napierdalanie.transformer;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import pl.memexurer.siurtransformer.loader.file.TransformerFile;

public class SyntheticBridgeRemover implements DecryptionTransformer {
    @Override
    public void decryptClass(TransformerFile file, ClassNode classNode) {
        classNode.access &= ~(Opcodes.ACC_SYNTHETIC | Opcodes.ACC_BRIDGE);
        classNode.access &= ~(Opcodes.ACC_STATIC);
        classNode.methods.forEach(methodNode -> {
            methodNode.access &= ~(Opcodes.ACC_SYNTHETIC | Opcodes.ACC_BRIDGE);
        });
        classNode.fields.forEach(fieldNode -> {
            fieldNode.access &= ~(Opcodes.ACC_SYNTHETIC | Opcodes.ACC_BRIDGE);
            fieldNode.access &= ~(Opcodes.ACC_SUPER | Opcodes.ACC_INTERFACE | Opcodes.ACC_ABSTRACT | Opcodes.ACC_ANNOTATION);
        });
    }
}
