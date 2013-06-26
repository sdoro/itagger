package controllers

import play.api.libs.json._


case class JsonResponse(val status:String, val data:String){

	/**
	 * success 
	 * fail 
	 * error
	 * 
	 */
	var	_status: String = status;
	var _data:String = data;
 	implicit private val  respoWrites = Json.writes[JsonResponse]
	
	def toJson(): String = {
//	  implicit private val StatusWrites = Json.writes[StatusUpdate]
//	  val status = StatusUpdate(user = "pablo", update = "serialize this")
//	  val json = Json.toJson(status)
//	  
	  val respo = new JsonResponse(_status, _data)
	  val json = Json.toJson(respo)
	  Json.stringify(json)
	}
	
}



