package com.atomist.rug.kind.gherkin.rug.kind.gherkin

import com.atomist.rug.kind.gherkin.rug.kind.core.{FileMutableView, ProjectMutableView}
import com.atomist.rug.kind.gherkin.rug.kind.dynamic.ChildResolver
import com.atomist.rug.kind.gherkin.rug.runtime.rugdsl.{DefaultEvaluator, Evaluator}
import com.atomist.rug.kind.gherkin.rug.spi.{ReflectivelyTypedType, Type}
import com.atomist.rug.kind.gherkin.tree.TreeNode

object GherkinType {
  val gherkinExt = ".feature"
}

class GherkinType(evaluator: Evaluator)
  extends Type(evaluator)
    with ChildResolver
    with ReflectivelyTypedType {

  import GherkinType._

  def this() = this(DefaultEvaluator)

  override def description = "Rug language extension for Gherkin"

  override def runtimeClass = classOf[GherkinMutableView]

  override def findAllIn(context: TreeNode): Option[Seq[TreeNode]] = context match {
      case pmv: ProjectMutableView =>
        Some(pmv.currentBackingObject.allFiles
          .filter(f => f.name.endsWith(gherkinExt))
          .map(f => new GherkinMutableView(f, pmv, new Gherkin))
        )
      case fmv: FileMutableView if fmv.name.endsWith(gherkinExt) =>
        Some(Seq(new GherkinMutableView(fmv.currentBackingObject, fmv.parent, new Gherkin)))
      case _ => None
    }
}
