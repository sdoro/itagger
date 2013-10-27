package models

import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.Play.current
import org.omg.CORBA.UserException
import java.util.ArrayList
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer

case class Tag(
  id: Long,
  tagname: String,
  lngt: String = "",
  lat: String = "",
  deleted: Boolean = false)

object Tag {
  val tag = {
    get[Long]("id") ~ get[String]("tagname") ~
      get[String]("lngt") ~ get[String]("lat") ~
      get[Boolean]("deleted") map {
        case id ~ tagname ~ lngt ~ lat ~ deleted => Tag(id, tagname, lngt, lat, deleted)
      }
  }

  def all(): List[Tag] = DB.withConnection {
    implicit c => SQL("select * from tags where deleted=FALSE").as(tag *)
  }

  def create(tag: Tag, success: Tag => Unit, fail: Exception => Unit) {
    try {
      DB.withConnection { implicit c =>
        SQL("insert into tags (tagname, lngt, lat, deleted) values ({tagname},{lngt},{lat},FALSE)")
          .on('tagname -> tag.tagname)
          .on('lngt -> tag.lngt)
          .on('lat -> tag.lat)
          .executeUpdate()
      }
      success(tag)
    } catch {
      case ex: Exception => {
        fail(ex)
      }
    }
  }

  def createOrUpdate(tag: Tag, success: Tag => Unit, fail: Exception => Unit) {
    try {
      val tagExists = Tag.all()
        .exists(p => p.tagname.equalsIgnoreCase(tag.tagname))
      if (tagExists) {
        val tagFound = Tag.all().filter(p => p.tagname.equalsIgnoreCase(tag.tagname))(0)
        val tagUpdated = tagFound.copy(
          lat = tag.lat, lngt = tag.lngt)
        Tag.update(tagUpdated, success, fail)
      } else {
        Tag.create(tag, success, fail)
      }
    } catch {
      case ex: Exception => {
        fail(ex)
      }
    }
  }

  def update(tag: Tag, success: Tag => Unit, fail: Exception => Unit) {
    try {
      DB.withConnection { implicit c =>
        SQL("update tags set lat={lat}, lngt={lngt} where id = {id}").on(
          'id -> tag.id,
          'lat -> tag.lat,
          'lngt -> tag.lngt)
          .executeUpdate()
      }
      success(tag)
    } catch {
      case ex: Exception => {
        fail(ex)
      }
    }
  }

  def delete(id: Long, success: Long => Unit, fail: Exception => Unit) {
    try {
      DB.withConnection { implicit c =>
        SQL("update tags set deleted=FALSE where id = {id}").on(
          'id -> id).executeUpdate()
      }
      success(id)
    } catch {
      case ex: Exception => {
        fail(ex)
      }
    }
  }

  def show(id: Long, success: Long => Unit, fail: Exception => Unit) {
    try {
      throw new Exception("fail")
    } catch {
      case ex: Exception => {
        fail(ex)
      }
    }
  }

  def getNeighbourList(lat1: Double, lon1: Double, maxDistInMt: Double, result: ListBuffer[Tag] => Unit, fail: Exception => Unit): ListBuffer[Tag] = {
    val output = new ListBuffer[Tag]
    try {
      Tag.all.foreach(u => {
        if (u.deleted != true) {
          var dist = maxDistInMt + 1
          Tag.calcDistInMt(lat1, lon1, u.lat.toDouble, u.lngt.toDouble,
            calcDist => {
              dist = calcDist
            },
            fail => {

            })
          if (dist <= maxDistInMt) {
            output.append(u) //only u
          }
        }
      })
      result(output)
    } catch {
      case ex: Exception => {
        fail(ex)
      }
    }
    output
  }

  def calcDistInMt(lat1: Double, lon1: Double, lat2: Double, lon2: Double, result: Double => Unit, fail: Exception => Unit) {
    try {
      val dLat = (lat2 - lat1).toRadians
      val dLon = (lon2 - lon1).toRadians

      val a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1.toRadians) * Math.cos(lat2.toRadians)
      val c = 2 * Math.asin(Math.sqrt(a))
      val R = 6372.8 //radius in km
      val d = R * c * 1000
      result(d)
    } catch {
      case ex: Exception => {
        fail(ex)
      }
    }

  }
}