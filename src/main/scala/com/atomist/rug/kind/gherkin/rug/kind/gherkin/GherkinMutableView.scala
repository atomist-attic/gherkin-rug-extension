package com.atomist.rug.kind.gherkin.rug.kind.gherkin

import com.atomist.rug.kind.core.{LazyFileArtifactBackedMutableView, ProjectMutableView}
import com.atomist.rug.spi.{ExportFunction, ExportFunctionParameterDescription}
import com.atomist.source.{ArtifactSource, FileArtifact}
import gherkin.ast.Feature
import gherkin.{AstBuilder, Parser}

object GherkinMutableView {
  val parser = new Parser(new AstBuilder())

}

class GherkinMutableView(
                                    originalBackingObject: FileArtifact,
                                    parent: ProjectMutableView,
                                    gherkin: Gherkin
                                  )
  extends LazyFileArtifactBackedMutableView(originalBackingObject, parent) {

  import GherkinMutableView._

  override def nodeName = "Gherkin"

  private val originalContent = originalBackingObject.content
  private var _currentContent = originalContent

  override protected def currentContent: String = _currentContent

  @ExportFunction(readOnly = true, description = "Returns the contents of the Gherkin file")
  def contents: String = currentContent

  def featureDefinition = {

        val gherkinDocument = parser.parse(currentContent)
        FeatureDefinition(gherkinDocument.getFeature)
  }

  @ExportFunction(readOnly = false, description = "Set contents of Gherkin file to `content`")
  def setContents(
                   @ExportFunctionParameterDescription(name = "content", description = "New contents for file") content: String
                 ): Unit = _currentContent = content
}

case class FeatureDefinition(feature: Feature)
