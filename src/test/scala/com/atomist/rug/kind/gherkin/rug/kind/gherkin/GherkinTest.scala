package com.atomist.rug.kind.gherkin.rug.kind.gherkin

import org.scalatest.{FlatSpec, Matchers}

class GherkinTest extends FlatSpec with Matchers {

  it should "get it" in {
    assert(Gherkin().it === "it")
  }
}
