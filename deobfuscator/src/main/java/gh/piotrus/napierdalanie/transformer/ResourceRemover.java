package gh.piotrus.napierdalanie.transformer;

import java.util.function.Consumer;
import pl.memexurer.siurtransformer.loader.file.TransformerFile;

public class ResourceRemover implements Consumer<TransformerFile> {
  private static final String FILE_IDENTIFIER = "Skoro tu jeste\u015B to mi\u0142o";

  @Override
  public void accept(TransformerFile file) {
    file.getBrokenClasses().removeIf(resource -> resource.getName().contains(FILE_IDENTIFIER));
  }
}
