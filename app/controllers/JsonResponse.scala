package controllers

import play.api.libs.json.JsValue
import play.api.libs.json.JsObject
import play.api.libs.json.Json
import play.api.libs.json.JsNull

class JsonResponse(val _status:String, val _data: String){

	/**
	 * success 
	 * fail 
	 * error
	 * 
	 */
	var	status: String = _status;
	var data: String = _data;

	
	def toJson(): String = {
	  "fail"
//	  Json.stringify(Json.toJson(this));
	}
	

	def main(args: Array[String]) {
  
		Json.obj(
  "users" -> Json.arr(
    Json.obj(
      "name" -> "bob",
      "age" -> 31,
      "email" -> "bob@gmail.com"  	  
    ),
    Json.obj(
      "name" -> "kiki",
      "age" -> 25,
      "email" -> JsNull  	  
    )
  )
)
		
	}
	
	
}



