package models

import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.Play.current
import org.omg.CORBA.UserException
import java.util.ArrayList
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer

case class User(id: Long,
  username: String,
  lngt: String = "",
  lat: String = "",
  deleted: Boolean = false)

object User {
  val user = {
    get[Long]("id") ~ get[String]("username") ~
      get[String]("lngt") ~ get[String]("lat") ~
      get[Boolean]("deleted") map {
        case id ~ username ~ lngt ~ lat ~ deleted => User(id, username, lngt, lat, deleted)
      }
  }

  def all(): List[User] = DB.withConnection {
    implicit c => SQL("select * from my_user where deleted=FALSE").as(user *)
  }

  def create(user: User, success: User => Unit, fail: Exception => Unit) {
    try {
      DB.withConnection { implicit c =>
        SQL("insert into my_user (username, lngt, lat, deleted) values ({username},{lngt},{lat},FALSE)")
          .on('username -> user.username)
          .on('lngt -> user.lngt)
          .on('lat -> user.lat)
          .executeUpdate()
      }
      success(user)
    } catch {
      case ex: Exception => {
        fail(ex)
      }
    }
  }

  def createOrUpdate(user: User, success: User => Unit, fail: Exception => Unit) {
    try {
      val userExists = User.all()
        .exists(p => p.username.equalsIgnoreCase(user.username))
      if (userExists) {
        val userFound = User.all().filter(p => p.username.equalsIgnoreCase(user.username))(0)
        val userUpdated = userFound.copy(
          lat = userFound.lat, lngt = userFound.lngt)
        User.update(userUpdated, success, fail)
      } else {
        User.create(user, success, fail)
      }
    } catch {
      case ex: Exception => {
        fail(ex)
      }
    }
  }

  
  def update(user: User, success: User => Unit, fail: Exception => Unit) {
    try {
      DB.withConnection { implicit c =>
        SQL("update my_user set lat={lat}, lngt={lngt} where id = {id}").on(
          'id -> user.id,
          'lat -> user.lat,
          'lngt -> user.lngt)
          .executeUpdate()
      }
      success(user)
    } catch {
      case ex: Exception => {
        fail(ex)
      }
    }
  }

  def delete(id: Long, success: Long => Unit, fail: Exception => Unit) {
    try {
      DB.withConnection { implicit c =>
        SQL("update my_user set deleted=FALSE where id = {id}").on(
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

  def getNeighbourList(user: User, users:List[User], maxDistInMt: Double, result: ListBuffer[User] => Unit, fail: Exception => Unit): ListBuffer[User] = {
    val output = new ListBuffer[User]
    try {
      users.foreach(u=> {
        var dist = maxDistInMt+1
        User.calcDistInMt(user, u, 
            calcDist=>{
              dist=calcDist
            } , 
            fail=>{
              
            })
        if(dist<=maxDistInMt){
          output.append(u) //only u
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

  def calcDistInMt(user1: User, user2: User, result: Double => Unit, fail: Exception => Unit) {
    try {
      val lat1 = user1.lat.toDouble
      val lat2 = user2.lat.toDouble
      val dLat = (lat2 - lat1).toRadians

      val lon1 = user1.lngt.toDouble
      val lon2 = user2.lngt.toDouble
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