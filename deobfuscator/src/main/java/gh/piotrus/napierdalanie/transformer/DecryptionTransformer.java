package gh.piotrus.napierdalanie.transformer;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import pl.memexurer.siurtransformer.loader.file.TransformerFile;

import java.util.function.Consumer;

public interface DecryptionTransformer extends Consumer<TransformerFile> {

    static MethodNode findClinit(ClassNode classNode) {
        return findMethodNode(classNode, "<clinit>", "()V");
    }

    static MethodNode findMethodNode(ClassNode classNode, MethodInsnNode methodInsnNode) {
        return findMethodNode(classNode, methodInsnNode.name, methodInsnNode.desc);
    }

    static FieldNode findFieldNode(ClassNode classNode, FieldInsnNode fieldInsnNode) {
        return classNode.fields.stream()
            .filter(fieldNode -> fieldNode.name.equals(fieldInsnNode.name) && fieldInsnNode.desc.equals(fieldInsnNode.desc))
            .findFirst().orElse(null);
    }

    static MethodNode findMethodNode(ClassNode classNode, String name, String desc) {
        return classNode.methods.stream()
            .filter(methodNode -> methodNode.name.equals(name) && methodNode.desc.equals(desc))
            .findFirst().orElse(null);
    }


    static MethodNode findMethodNode(TransformerFile file, MethodInsnNode methodInsnNode) {
        TransformerFile.Class clazz = file.resolveClass(methodInsnNode.owner);
        if(clazz == null)
            return null;

        return findMethodNode(clazz.getParsedNode(), methodInsnNode);
    }

    void decryptClass(TransformerFile file, ClassNode node);

    @Override
    default void accept(TransformerFile file) {
        for (TransformerFile.Class clazz : file.getClasses()) {
            decryptClass(file, clazz.getParsedNode());
        }
    }
}
