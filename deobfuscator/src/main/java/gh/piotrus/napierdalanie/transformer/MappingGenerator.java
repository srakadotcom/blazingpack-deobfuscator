package gh.piotrus.napierdalanie.transformer;

import gh.piotrus.napierdalanie.mapping.MappedClass;
import gh.piotrus.napierdalanie.mapping.MappingFactory;
import gh.piotrus.napierdalanie.mapping.MappingRenamer;
import gh.piotrus.napierdalanie.mapping.remap.impl.ModeSimple;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.objectweb.asm.tree.ClassNode;
import pl.memexurer.siurtransformer.loader.file.TransformerFile;
import pl.memexurer.siurtransformer.loader.file.TransformerFile.Class;

public class MappingGenerator  implements Consumer<TransformerFile> {
  public static Map<String, MappedClass> mappings;

  @Override
  public void accept(TransformerFile file) {
    Map<String, ClassNode> map = new HashMap<>();
    for(Class clazz: file.getClasses()) {
      map.put(clazz.getParsedNode().name, clazz.getParsedNode());
    }

    long start = System.currentTimeMillis();
    mappings = MappingFactory.mappingsFromNodes(map);
    System.out.println("Generated mappings in " + (System.currentTimeMillis() - start) + "ms!");

    start = System.currentTimeMillis();
    MappingRenamer renamer = new MappingRenamer();
    mappings = renamer.remapClasses(mappings, new ModeSimple());
    System.out.println("Remapped mappings in " + (System.currentTimeMillis() - start) + "ms!");
  }
}
