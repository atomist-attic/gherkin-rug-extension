package com.atomist.rug.kind.gherkin.rug.kind.gherkin

import com.atomist.rug.kind.core.{LazyFileArtifactBackedMutableView, ProjectMutableView}
import com.atomist.rug.spi.{ExportFunction, ExportFunctionParameterDescription}
import com.atomist.source.FileArtifact
import gherkin.ast.ScenarioDefinition
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
  private var parsedContent = parser.parse(_currentContent)

  override protected def currentContent: String = _currentContent

  @ExportFunction(readOnly = true, description = "Returns the contents of the Gherkin file")
  def contents: String = currentContent

  @ExportFunction(readOnly = true, description = "Returns the name of the Gherkin feature")
  def featureName: String = {
    parsedContent.getFeature.getName
  }

  @ExportFunction(readOnly = true, description = "Returns the name of the Gherkin feature")
  def setFeatureName(@ExportFunctionParameterDescription(name = "name", description = "New name for the feature") name: String): Unit = {
    val result = currentContent.replaceAllLiterally(featureName, name)
    this._currentContent = result
    this.parsedContent = parser.parse(_currentContent)
  }

  @ExportFunction(readOnly = true, description = "Returns the list of the scenarios")
  def scenarioDefinitions: java.util.List[ScenarioDefinition] = {
    parsedContent.getFeature.getChildren
  }

  @ExportFunction(readOnly = false, description = "Set contents of Gherkin file to `content`")
  def setContents(@ExportFunctionParameterDescription(name = "content", description = "New contents for file") content: String): Unit = _currentContent = content
}
