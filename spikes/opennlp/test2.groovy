@GrabResolver(name='repo1.maven.org', root='http://repo1.maven.org')
@Grapes([
  @Grab(group='org.apache.opennlp', module='opennlp-tools', version='1.5.3'),
  @Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.14'),
  @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.1'),
  @Grab(group='xerces', module='xercesImpl', version='2.9.1') ])

// Download the en files from http://opennlp.sourceforge.net/models-1.5/

//   @Grab(group='org.apache.nlp', module='opennlp-uima', version='1.5.3'),
//   @Grab(group='org.apache.nlp', module='opennlp-maxent', version='3.0.3'),
import groovyx.net.http.*
import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovyx.net.http.*
import org.apache.http.entity.mime.*
import org.apache.http.entity.mime.content.*
import java.nio.charset.Charset
import static groovy.json.JsonOutput.*
import opennlp.tools.tokenize.*


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import opennlp.tools.namefind.*;
import opennlp.tools.tokenize.*;
import opennlp.tools.util.*;
import opennlp.tools.util.featuregen.*;

println("Call test...");
test();
println("All done...");

def test() {
  println("Create tokenizer.....");
  // TokenizerModel tokenizerModel

  println("Declare sentences");
  String[] sentences = [
    "Hallamshire Aikido Club Traditional aikido. Open ability class for people aged 16 years and over. Suitable for beginners to advanced.  Classes are held at Sheffield Springs Academy, S12 (Tuesday and Thursday).",
    "The Steel City Aikido Club trains at the Kyogikan dojo. This is a permanently matted, traditional Japanese-style dojo, the only one of its kind in the country. Welcomes adult beginners, men and women with no prior martial arts experience, whether you want to get fitter, take part in competitions or just start something new alongside a group of qualified and encouraging instructors.  From October 2013 a junior club will start for children and teenagers: 8-10 years, 11-13 years and 14-17 years.  Classes are held at the Kyogikan Dojo, Eldon Street, S1."
  ];
  println("Declare modelDir");
  def modelDir = '/home/ibbo/opennlp'

  println("Create finders....");
  NameFinderME[] finders = new NameFinderME[3];

  String[] names = ["person", "location", "date"];
  for (int mi = 0; mi < names.length; mi++) {
    finders[mi] = new NameFinderME(new TokenNameFinderModel( new FileInputStream( new File(modelDir, "en-ner-" + names[mi] + ".bin"))));
  }


  Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
  for (int si = 0; si < sentences.length; si++) {
    def allAnnotations = []
    String[] tokens = tokenizer.tokenize(sentences[si]);
    println("Tokens: ${tokens}");
    for (int fi = 0; fi < finders.length; fi++) {
      Span[] spans = finders[fi].find(tokens);
      double[] probs = finders[fi].probs(spans);
      for (int ni = 0; ni < spans.length; ni++) {
        allAnnotations.add(
          [names:names[fi], spans:spans[ni], probs:probs[ni], token:getSpan(tokens,spans[ni])]
        );
      }
    }
    println("All annotations: ${allAnnotations}");
  }

  println("test complete");
}

def getSpan(tokens,span) {
  def result_tokens= []
  for ( int i=span.start; i<span.end; i++ ) {
    result_tokens.add(tokens[i]);
  }
  result_tokens.join(' ');
}
