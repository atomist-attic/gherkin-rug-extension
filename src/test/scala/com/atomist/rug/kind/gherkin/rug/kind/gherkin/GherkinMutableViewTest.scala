package com.atomist.rug.kind.gherkin.rug.kind.gherkin

import com.atomist.rug.kind.core.{FileMutableView, ProjectMutableView}
import com.atomist.source.{SimpleFileBasedArtifactSource, StringFileArtifact}
import org.scalatest.{FlatSpec, Matchers}

class GherkinMutableViewTest extends FlatSpec with Matchers {

  import GherkinMutableViewTest._

  it should "be able to get the contents of the file" in {
    assert(mv.contents === fileContents)
  }

  it should "be able to get the name of the feature from the file" in {
    assert(mv.featureDefinition.feature.getName == featureTitle)
  }

  it should "be able to set the contents of the file" in {
    val newContent =
      """This is the new content
        |for the thing we'd like to test.
        |""".stripMargin
    mv.setContents(newContent)
    assert(mv.contents === newContent)
  }
}

object GherkinMutableViewTest {

  import GherkinType._

  private val featureTitle =  "Australian political history"

  private val fileContents =
    s"""
Feature: $featureTitle
 This is a test
 to demonstrate that the Gherkin DSL
 is a good fit for Rug BDD testing

Scenario: Australian politics, 1972-1991
 Given an empty project
 Given a visionary leader
 When politics takes its course
 Then the rage is maintained
    """.stripMargin

  val fileName: String = "file" + gherkinExt
  val fileArtifact = StringFileArtifact(fileName, fileContents)
  val artifactSource = SimpleFileBasedArtifactSource(fileArtifact)
  val pmv = new ProjectMutableView(artifactSource)
  val fmv = new FileMutableView(fileArtifact, pmv)
  val mv = new GherkinMutableView(fileArtifact, pmv, new Gherkin)
}
