package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  /**
   * Display the 'new computer form'.
   */
  def ping = Action {

    val jsResp = new JsonResponse("success", "pong");

    Ok("ciao");

  }

  def fake(id: Long) = Action { request =>
    Ok(Json.toJson(
      Map("status" -> "OK", "message" -> ("id " + id * 2))))

  }

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

}