package org.annolab.tt4j;

import static java.util.Arrays.asList;
import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;
public class Example {
  public static void main(String[] args) throws Exception {
    // Point TT4J to the TreeTagger installation directory. The executable is expected
    // in the "bin" subdirectory - in this example at "/opt/treetagger/bin/tree-tagger"
    System.setProperty("treetagger.home", "C:\\Program Files\\TreeTagger");
    TreeTaggerWrapper tt = new TreeTaggerWrapper<String>();
    try {
      tt.setModel("C:\\Program Files\\TreeTagger\\lib\\english-utf8.par");

      tt.setHandler((token, pos, lemma) -> 
        System.out.println(token + "\t" + pos + "\t" + lemma));


      tt.process(asList(new String[] { "This", "is", "a", "test", "." }));
    }
    finally {
      tt.destroy();
    }
  }
}
Java 8