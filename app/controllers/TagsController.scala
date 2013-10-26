package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json._

import views.html.defaultpages.badRequest
import play.api.libs.json.JsValue
import models.Tag
import scala.collection.mutable.ArrayBuffer

object TagsController extends Controller {

  implicit val tagFormat = Json.format[Tag]

  
  def create() = Action {
    request =>

      val body: AnyContent = request.body
      val jsonBody: Option[JsValue] = body.asJson
      
      //val _id: Long = jsonBody.get.\("id").toString.toLong
      val _tagname: String = jsonBody.get.\("tagname").toString
      val _lngt: String = jsonBody.get.\("lngt").toString
      val _lat: String = jsonBody.get.\("lat").toString

      val tag: Tag = new Tag(id = -1, tagname = _tagname, lngt = _lngt, lat = _lat)

      var res: Boolean = false;
      Tag.createOrUpdate(tag,
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

      val jsonObject = Json.toJson(
        Map(
          //"data" -> jsonArray,
          "message" -> Json.toJson(sys.env.get("MESSAGE"))
        )
      )

      Ok(jsonObject)

  }
  
    def getNeighbourList() = Action {
    request =>

      val _maxDistInMt: String   = request.getQueryString("max").getOrElse("10000");
      val _lngt: String          = request.getQueryString("lngt").getOrElse("0");
      val _lat: String           = request.getQueryString("lat").getOrElse("0");
           
      val neighborhood = Tag.getNeighbourList(_lat.toDouble, _lngt.toDouble, _maxDistInMt.toDouble, result => {}, fail => {})
      val jsonArray = Json.toJson(neighborhood)

      val jsonObject = Json.toJson(
        Map(
          "data" -> jsonArray,
          "message" -> Json.toJson(sys.env.get("MESSAGE"))
        )
      )

      Ok(jsonObject)

  }
  
  
//  def update(id: Long) = Action {
//    request =>
//      val body: AnyContent = request.body
//      val jsonBody: Option[JsValue] = body.asJson
//      val tag = jsonBody.get.as[Tag]
//      var res: Boolean = false;
//
//      //Tag.update(tag, success, fail)
//
//      Tag.createOrUpdate(tag,
//        success => {
//          res = true
//        },
//        fail => {
//          res = false
//        })
//      if (res) {
//        Ok(Json.toJson(
//          Map("status" -> "success",
//            "data" -> (tag.tagname))))
//      } else {
//        Ok(Json.toJson(
//          Map("status" -> "fail", "message" -> ("BadRequest"))))
//      }
//  }
//
//  def show(id: Long) = Action { request =>
//    Ok(Json.toJson(
//      Map("status" -> "OK", "message" -> ("Hello " + "name"))))
//  }
//
//  //parse.json
//
//  //val json = Json.parse("""{"tagname":"myfile.avi", "id":12345, "lngt":"qua", "lat":"diqui", "deleted": false}""") // Your WS result
//  //
//  //
//  //val tag = json.as[Tag]
//
//  def create() = Action {
//    request =>
//      val (tag, res) = analyzeRequest(request)
//      if (res) {
//        Ok(Json.toJson(
//          Map("status" -> "success",
//            "data" -> (tag.tagname))))
//      } else {
//        Ok(Json.toJson(
//          Map("status" -> "fail", "message" -> ("BadRequest"))))
//      }
//  }
//
//  def matching() = Action {
//    request =>
//
//      val body: AnyContent = request.body
//      val jsonBody: Option[JsValue] = body.asJson
//      val _tagname: String = jsonBody.get.\("tagname").toString
//      val _id: Long = jsonBody.get.\("id").toString.toLong
//      val _lngt: String = jsonBody.get.\("lngt").toString
//      val _lat: String = jsonBody.get.\("lat").toString
//      val _maxDistInMt: Long = jsonBody.get.\("max").toString.toLong
//
//      val tag: Tag = new Tag(id = _id, tagname = _tagname, lngt = _lngt, lat = _lat)
//
//      var res: Boolean = false;
//      Tag.createOrUpdate(tag,
//        success => {
//          res = true
//        },
//        fail => {
//          res = false
//        })
//
//      if (res == false) {
//        Ok(Json.toJson(
//          Map("status" -> "fail", "message" -> ("BadRequest"))))
//      }
//
//      val neighborhood = Tag.getNeighbourList(tag, Tag.all, _maxDistInMt, result => {}, fail => {})
//      val jsonArray = Json.toJson(neighborhood)
//
//      val jsonObject = Json.toJson(
//        Map(
//          "data" -> jsonArray,
//          "message" -> Json.toJson(sys.env.get("MESSAGE"))
//        )
//      )
//
//      Ok(jsonObject)
//
//  }
//
//  private def analyzeRequest(request: play.api.mvc.Request[play.api.mvc.AnyContent]): (models.Tag, Boolean) = {
//    val body: AnyContent = request.body
//    val jsonBody: Option[JsValue] = body.asJson
//    val _tagname: String = jsonBody.get.\("tagname").toString
//    val _id: Long = jsonBody.get.\("id").toString.toLong
//    val _lngt: String = jsonBody.get.\("lngt").toString
//    val _lat: String = jsonBody.get.\("lat").toString
//
//    val tag: Tag = new Tag(id = _id, tagname = _tagname, lngt = _lngt, lat = _lat)
//
//    var res: Boolean = false;
//    Tag.createOrUpdate(tag,
//      success => {
//        res = true
//      },
//      fail => {
//        res = false
//      })
//    (tag, res)
//  }
}