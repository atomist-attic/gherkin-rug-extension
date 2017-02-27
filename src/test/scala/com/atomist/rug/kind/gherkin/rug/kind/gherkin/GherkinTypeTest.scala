package com.atomist.rug.kind.gherkin.rug.kind.gherkin

import com.atomist.rug.runtime.rugdsl.DefaultEvaluator
import org.scalatest.{FlatSpec, Matchers}

class GherkinTypeTest extends FlatSpec with Matchers {

  import GherkinMutableViewTest._

  val typo = new GherkinType(DefaultEvaluator)

  it should "be able to find the language in the project" in {
    val mvs = typo.findAllIn(pmv) match {
      case Some(tns) => tns
      case _ => fail(s"failed to find any Gherkin in project")
    }
    assert(mvs.size === 1)
    assert(mvs.head.nodeName === "Gherkin")
  }

  it should "be able to find the language in the file" in {
    val mvs = typo.findAllIn(fmv) match {
      case Some(tns) => tns
      case _ => fail(s"failed to extract Gherkin from file")
    }
    assert(mvs.size === 1)
    assert(mvs.head.nodeName === "Gherkin")
  }
}
