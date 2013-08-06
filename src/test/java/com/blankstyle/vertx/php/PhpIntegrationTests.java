package com.blankstyle.vertx.php;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.vertx.testtools.ScriptClassRunner;
import org.vertx.testtools.TestVerticleInfo;

@TestVerticleInfo(filenameFilter=".+\\.php", funcRegex="function[\\s]+(test[^\\s(]+)")
@RunWith(ScriptClassRunner.class)
public class PhpIntegrationTests {
  @Test
  public void __vertxDummy() {
  }
}
