package com.github.celadari.jsonlogicscala.exceptions

import com.github.celadari.jsonlogicscala.tree.JsonLogicCore

final class EvaluateException(
                               msg: String,
                               val condition: JsonLogicCore,
                               val root: JsonLogicCore,
                               val origException: Throwable
                             )
  extends JsonLogicScalaException(msg) {

  def debugTreeString: String = JsonLogicCore.traverseRoot(root, Some(condition))
}
