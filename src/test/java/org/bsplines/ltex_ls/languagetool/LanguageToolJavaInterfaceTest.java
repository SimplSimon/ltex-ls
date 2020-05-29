package org.bsplines.ltex_ls.languagetool;

import java.util.List;

import org.bsplines.ltex_ls.*;

import org.eclipse.lsp4j.TextDocumentItem;

import org.eclipse.xtext.xbase.lib.Pair;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.languagetool.markup.AnnotatedText;

@TestInstance(Lifecycle.PER_CLASS)
public class LanguageToolJavaInterfaceTest {
  private SettingsManager settingsManager;
  private DocumentChecker documentChecker;

  @BeforeAll
  public void setUp() throws InterruptedException {
    settingsManager = new SettingsManager();
    documentChecker = new DocumentChecker(settingsManager);
  }

  @Test
  public void testCheck() {
    TextDocumentItem document = DocumentCheckerTest.createDocument("latex",
        "This is an \\textbf{test.}\n% LTeX: language=de-DE\nDies ist eine \\textbf{Test}.\n");
    Pair<List<LanguageToolRuleMatch>, AnnotatedText> checkingResult =
        documentChecker.check(document);
    DocumentCheckerTest.testMatches(checkingResult.getKey(), 8, 10, 58, 75);
  }

  @Test
  public void testOtherMethods() {
    LanguageToolInterface ltInterface = settingsManager.getLanguageToolInterface();
    Assertions.assertDoesNotThrow(() -> ltInterface.activateDefaultFalseFriendRules());
    Assertions.assertDoesNotThrow(() -> ltInterface.activateLanguageModelRules("foobar"));
    Assertions.assertDoesNotThrow(() -> ltInterface.activateNeuralNetworkRules("foobar"));
    Assertions.assertDoesNotThrow(() -> ltInterface.activateWord2VecModelRules("foobar"));
    Assertions.assertDoesNotThrow(() -> ltInterface.enableEasterEgg());
  }
}
