package gh.piotrus.napierdalanie;

import static gh.piotrus.napierdalanie.transformer.MappingGenerator.mappings;

import gh.piotrus.napierdalanie.mapping.MappingClassWriter;
import gh.piotrus.napierdalanie.mapping.SkidRemapper;
import gh.piotrus.napierdalanie.transformer.ArrayUnpooler;
import gh.piotrus.napierdalanie.transformer.ClinitFolder;
import gh.piotrus.napierdalanie.transformer.DecryptionTransformer;
import gh.piotrus.napierdalanie.transformer.IntObfuscationRemover;
import gh.piotrus.napierdalanie.transformer.MappingGenerator;
import gh.piotrus.napierdalanie.transformer.NumberTransformer;
import gh.piotrus.napierdalanie.transformer.ResourceRemover;
import gh.piotrus.napierdalanie.transformer.StringEncryptionTransformer;
import gh.piotrus.napierdalanie.transformer.SyntheticBridgeRemover;
import java.io.File;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.CheckClassAdapter;
import pl.memexurer.siurtransformer.TransformationBootstrapBuilder;
import pl.memexurer.siurtransformer.exporter.file.zip.ZipTransformerFileExporter;

public final class NapierdalanieBootstrap {

  private NapierdalanieBootstrap() {
  }

  public static void main(String[] args) {
    new TransformationBootstrapBuilder()
        .withInputFile(new File("in.jar"))
        .execute(new SyntheticBridgeRemover())
        .execute(new NumberTransformer())
        .execute((DecryptionTransformer) (file, node) -> {
          if (node.signature != null) {
            try {
              CheckClassAdapter.checkClassSignature(node.signature);
            } catch (IllegalArgumentException | StringIndexOutOfBoundsException ignored) {
              node.signature = null;
            }
          }
          node.methods.forEach(methodNode -> {
            if (methodNode.signature != null) {
              try {
                CheckClassAdapter.checkMethodSignature(methodNode.signature);
              } catch (IllegalArgumentException | StringIndexOutOfBoundsException ignored) {
                methodNode.signature = null;
              }
            }
          });
          node.fields.forEach(fieldNode -> {
            if (fieldNode.signature != null) {
              try {
                CheckClassAdapter.checkFieldSignature(fieldNode.signature);
              } catch (IllegalArgumentException | StringIndexOutOfBoundsException ignored) {
                fieldNode.signature = null;
              }
            }
          });

          if (node.visibleAnnotations != null) {
            node.visibleAnnotations.clear();
          }
          if (node.invisibleAnnotations != null) {
            node.invisibleAnnotations.clear();
          }

          for (MethodNode node1 : node.methods) {
            if (node1.visibleAnnotations != null) {
              node1.visibleAnnotations.clear();
            }
            if (node1.invisibleAnnotations != null) {
              node1.invisibleAnnotations.clear();
            }

            if (node1.instructions.getFirst() != null &&
                node1.instructions.getFirst().getOpcode() == Opcodes.ICONST_0 &&
                node1.instructions.getFirst().getNext().getOpcode() == Opcodes.LOOKUPSWITCH) {
              node1.instructions.remove(node1.instructions.getFirst());
              node1.instructions.remove(node1.instructions.getFirst());
            }
          }
        })
        .execute(new IntObfuscationRemover())
        .execute(new StringEncryptionTransformer())
        .execute(new ArrayUnpooler())
        .execute(new ClinitFolder())
        .execute(new MappingGenerator())
        .execute(new ResourceRemover())
        .export(new ZipTransformerFileExporter(new File("out.jar")), classFile -> {
          ClassWriter cw = new MappingClassWriter(mappings,
              ClassWriter.COMPUTE_MAXS);
          ClassVisitor remapper = new ClassRemapper(cw, new SkidRemapper(mappings)); //it doesn't work with unknown classes btw
          classFile.getNode().accept(remapper);
          return cw.toByteArray();
        });
  }
}
