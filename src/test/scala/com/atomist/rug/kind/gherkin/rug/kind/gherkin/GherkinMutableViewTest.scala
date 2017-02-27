package com.atomist.rug.kind.gherkin.rug.kind.gherkin

import com.atomist.rug.kind.core.{FileMutableView, ProjectMutableView}
import com.atomist.source.{SimpleFileBasedArtifactSource, StringFileArtifact}
import org.scalatest.{FlatSpec, Matchers}

class GherkinMutableViewTest extends FlatSpec with Matchers {

  import GherkinMutableViewTest._

  it should "be able to get the name of the feature" in {
    assert(gherkinMutableView.featureName == featureTitle)
  }

  it should "be able to set the name of the feature" in {
    val locaGherkinMutableView = new GherkinMutableView(fileArtifact, projectMutableView, new Gherkin)
    val newFeatureName = "US political history"
    locaGherkinMutableView.setFeatureName(newFeatureName)
    assert(locaGherkinMutableView.dirty)
    assert(locaGherkinMutableView.featureName == newFeatureName)
  }

  it should "be able to get the list of scenarios" in {
    assert(gherkinMutableView.scenarioDefinitions.size() == 1)
  }

  it should "be able to get the contents of the file" in {
    assert(gherkinMutableView.contents === fileContents)
  }

  it should "be able to set the contents of the file" in {
    val newContent =
      """This is the new content
        |for the thing we'd like to test.
        |""".stripMargin
    gherkinMutableView.setContents(newContent)
    assert(gherkinMutableView.contents === newContent)
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
  val projectMutableView = new ProjectMutableView(artifactSource)
  val fileMutableView = new FileMutableView(fileArtifact, projectMutableView)
  val gherkinMutableView = new GherkinMutableView(fileArtifact, projectMutableView, new Gherkin)
}
