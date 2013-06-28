package models

import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.Play.current
import org.omg.CORBA.UserException

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

  def getNeighborhood() : Array[User] = {
    
    var z = new Array[User](3)
    z
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

  def calculateMatching(id: Long, success: User => Unit, fail: Exception => Unit) {
    try {
      throw new Exception("fail")
    } catch {
      case ex: Exception => {
        fail(ex)
      }
    }

  }
}