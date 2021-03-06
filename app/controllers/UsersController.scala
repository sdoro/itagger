package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json._

import views.html.defaultpages.badRequest
import play.api.libs.json.JsValue
import models.User
import scala.collection.mutable.ArrayBuffer

object UsersController extends Controller {

  implicit val userFormat = Json.format[User]

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
      val body: AnyContent = request.body
      val jsonBody: Option[JsValue] = body.asJson
      val user = jsonBody.get.as[User]
      var res: Boolean = false;

      //User.update(user, success, fail)

      User.createOrUpdate(user,
        success => {
          res = true
        },
        fail => {
          res = false
        })
      if (res) {
        Ok(Json.toJson(
          Map("status" -> "success",
            "data" -> (user.username))))
      } else {
        Ok(Json.toJson(
          Map("status" -> "fail", "message" -> ("BadRequest"))))
      }
  }

  def show(id: Long) = Action { request =>
    Ok(Json.toJson(
      Map("status" -> "OK", "message" -> ("Hello " + "name"))))
  }

  //parse.json

  //val json = Json.parse("""{"username":"myfile.avi", "id":12345, "lngt":"qua", "lat":"diqui", "deleted": false}""") // Your WS result
  //
  //
  //val user = json.as[User]

  def create() = Action {
    request =>
      val (user, res) = analyzeRequest(request)
      if (res) {
        Ok(Json.toJson(
          Map("status" -> "success",
            "data" -> (user.username))))
      } else {
        Ok(Json.toJson(
          Map("status" -> "fail", "message" -> ("BadRequest"))))
      }
  }

  def matching() = Action {
    request =>

      val body: AnyContent = request.body
      val jsonBody: Option[JsValue] = body.asJson
      val _username: String = jsonBody.get.\("username").toString
      val _id: Long = jsonBody.get.\("id").toString.toLong
      val _lngt: String = jsonBody.get.\("lngt").toString
      val _lat: String = jsonBody.get.\("lat").toString
      val _maxDistInMt: Long = jsonBody.get.\("max").toString.toLong

      val user: User = new User(id = _id, username = _username, lngt = _lngt, lat = _lat)

      var res: Boolean = false;
      User.createOrUpdate(user,
        success => {
          res = true
        },
        fail => {
          res = false
        })

      if (res == false) {
        Ok(Json.toJson(
          Map("status" -> "fail", "message" -> ("BadRequest"))))
      }

      val neighborhood = User.getNeighbourList(user, User.all, _maxDistInMt, result => {}, fail => {})
      val jsonArray = Json.toJson(neighborhood)

      val jsonObject = Json.toJson(
        Map(
          "data" -> jsonArray,
          "message" -> Json.toJson(sys.env.get("MESSAGE"))
        )
      )

      Ok(jsonObject)

  }

  private def analyzeRequest(request: play.api.mvc.Request[play.api.mvc.AnyContent]): (models.User, Boolean) = {
    val body: AnyContent = request.body
    val jsonBody: Option[JsValue] = body.asJson
    val _username: String = jsonBody.get.\("username").toString
    val _id: Long = jsonBody.get.\("id").toString.toLong
    val _lngt: String = jsonBody.get.\("lngt").toString
    val _lat: String = jsonBody.get.\("lat").toString

    val user: User = new User(id = _id, username = _username, lngt = _lngt, lat = _lat)

    var res: Boolean = false;
    User.createOrUpdate(user,
      success => {
        res = true
      },
      fail => {
        res = false
      })
    (user, res)
  }
}