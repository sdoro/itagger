package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json


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

  def show(id: Long) = Action { request =>
      Ok(Json.toJson(
        Map("status" -> "OK", "message" -> ("Hello " + "name"))))
  }


}