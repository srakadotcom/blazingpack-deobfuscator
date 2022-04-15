package gh.piotrus.napierdalanie;

import gh.piotrus.napierdalanie.transformer.PluginStringObfTransformer;
import java.io.File;
import pl.memexurer.siurtransformer.TransformationBootstrapBuilder;
import pl.memexurer.siurtransformer.exporter.clazz.FramelessClassFileExporter;
import pl.memexurer.siurtransformer.exporter.file.zip.ZipTransformerFileExporter;

public final class PluginDeobfuscatorBootstrap {

  private PluginDeobfuscatorBootstrap() {
  }

  public static void main(String[] args) {
    new TransformationBootstrapBuilder()
        .withInputFile(new File("in_plugin.jar"))
        .execute(new PluginStringObfTransformer())
        .export(new ZipTransformerFileExporter(new File("in_out.jar")),
            new FramelessClassFileExporter());
  }
}
