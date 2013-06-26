package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import models.User

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

  def create() = Action {
    request =>
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

}