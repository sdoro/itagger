package controllers

import play.api._
import play.api.mvc._
import play.libs.Json
import play.api.libs.json.Writes._
import play.api.libs.json.JsNull

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

  
  
}