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
  
  
}