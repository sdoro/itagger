package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import views.html.defaultpages.badRequest
import play.api.libs.json.JsValue

object UsersController extends Controller {

  //  def show(id: Long) = Action {
  ////    User.findById(id).map {
  ////      user => 
  ////        Ok(user.id + user.name)
  ////      
  //  (request.body \ "name").asOpt[String].map { name =>
  //    Ok(toJson(
  //      Map("status" -> "OK", "message" -> ("Hello " + name))
  //    ))
  //  }.getOrElse {
  //    BadRequest(toJson(
  //      Map("status" -> "KO", "message" -> "Missing parameter [name]")
  //    ))
  //  }
  //    
  //    }

  def update(id: Long) = Action {
    request =>
      val myId: Long = id
      val body: AnyContent = request.body
      val jsonBody: Option[JsValue] = body.asJson
      val raw = body.asRaw
      jsonBody.map { value =>
        Ok(Json.toJson(
          Map("status" -> "success",
            "data" -> (Json.stringify(value)))))
      }.getOrElse {
        Ok(Json.toJson(
          Map("status" -> "fail", "message" -> ("BadRequest"))))
      }

  }

  def show(id: Long) = Action { request =>
    Ok(Json.toJson(
      Map("status" -> "OK", "message" -> ("Hello " + "name"))))
  }

  //parse.json

  def create() = Action {
    request =>
      val body: AnyContent = request.body
      val jsonBody: Option[JsValue] = body.asJson
      val raw = body.asRaw
      //jsonBody.get
      jsonBody.map { value =>
        Ok(Json.toJson(
          Map("status" -> "success",
            "data" -> (Json.stringify(value)))))
      }.getOrElse {
        Ok(Json.toJson(
          Map("status" -> "fail", "message" -> ("BadRequest"))))
      }

  }

  
//  case class User(id: Long, username: String, long:Double=0.0, lat:Double=0.0, deleted: Boolean = false) {}
//  val jsv:JsValue = Json.toJson("{\"latitude\":\"15.5439025839\",\"longitude\":\"13.5439025839\",\"userid\":\"Fabio\",\"password\":\"pizza123\"}")

  //parse[User]("""{"id":1,"name":"Coda"}""")
  
//val ur: Option[User] = jsv.asOpt[User]


  
  //  def sayHello = Action(parse.json) { request =>
  //  (request.body \ "name").asOpt[String].map { name =>
  //    Ok(toJson(
  //      Map("status" -> "OK", "message" -> ("Hello " + name))
  //    ))
  //  }.getOrElse {
  //    BadRequest(toJson(
  //      Map("status" -> "KO", "message" -> "Missing parameter [name]")
  //    ))
  //  }
  //}

  //  def create() = Action {
  //    request =>
  //      val body: AnyContent = request.body
  //      val jsonBody: Option[JsValue] = body.asJson
  //
  //      jsonBody.map { value =>
  //        Ok(Json.toJson(
  //          Map("status" -> "success", "data" -> (Json.stringify(value)))))
  //      }.getOrElse {
  //        Ok(Json.toJson(
  //          Map("status" -> "fail", "message" -> ("BadRequest"))))
  //      }
  //
  //  }

}