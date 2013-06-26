package scalatest

import org.scalatest.FlatSpec
import scala.collection.mutable.Stack
import controllers.JsonResponse

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */

class JsonResponseSpec extends FlatSpec {

  "A JsonResponse" should "be serialized" in {
    val jsonResp = new JsonResponse("success", "value1")
    val repoString = jsonResp.toJson()
    assert(repoString== "{status:\"success\",data:\"value1\"}")
  }

}