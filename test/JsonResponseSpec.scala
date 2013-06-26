package test

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import controllers.JsonResponse




/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */

class JsonResponseSpec extends Specification {
  
  "JsonResponse" should {
    
    "transform to json" in {
    	val jsonresponse = new JsonResponse("success", "value1")
    	jsonresponse.toJson must equalTo("{status:\"success\", data:\"value1\" }")     
    }    
  }
}