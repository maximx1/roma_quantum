package controllers

import org.json4s._

package object helpers {
  private implicit val format = DefaultFormats
  def Json(o: Any) = Extraction.decompose(o)
}