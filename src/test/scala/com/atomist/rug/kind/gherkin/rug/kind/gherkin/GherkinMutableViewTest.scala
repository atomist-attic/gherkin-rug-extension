package com.atomist.rug.kind.gherkin.rug.kind.gherkin

import com.atomist.rug.kind.core.{FileMutableView, ProjectMutableView}
import com.atomist.source.{SimpleFileBasedArtifactSource, StringFileArtifact}
import org.scalatest.{FlatSpec, Matchers}

class GherkinMutableViewTest extends FlatSpec with Matchers {

  import GherkinMutableViewTest._

  it should "be able to get something interesting" in {
    import GherkinMutableView._
    assert(mv.somethingUseful === useful)
  }

  it should "be able to get the contents of the file" in {
    assert(mv.contents === fileContents)
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

  private val fileContents =
    """Contents of a sample file that you would
      |like to test.
      |""".stripMargin
  val fileName: String = "file" + gherkinExt
  val fileArtifact = StringFileArtifact(fileName, fileContents)
  val artifactSource = SimpleFileBasedArtifactSource(fileArtifact)
  val pmv = new ProjectMutableView(artifactSource)
  val fmv = new FileMutableView(fileArtifact, pmv)
  val mv = new GherkinMutableView(fileArtifact, pmv, new Gherkin)
}
