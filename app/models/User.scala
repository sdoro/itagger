package models

import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.Play.current

case class User(id: Long,
  username: String,
  long:Double=0.0,
  lat:Double=0.0,
  deleted: Boolean = false) {
}

object User {

  val user = {
    get[Long]("id") ~ get[String]("username") ~ 
    get[Double]("long") ~ get[Double]("lat") ~ 
    get[Boolean]("deleted") map {
        case id ~ username ~ long ~ lat ~ deleted => User(id, username, long,lat,deleted)
      }
  }

  def all(): List[User] = DB.withConnection {
    implicit c => SQL("select * from user where deleted=FALSE").as(user *)
  }

  def create(user: User, success: User => Unit, fail: Exception => Unit) {
    try {
      DB.withConnection { implicit c =>
        SQL("insert into user (username, long, lat, deleted) values ({username},{long},{lat},FALSE)")
        .on('username -> user.username)
        .on('long -> user.long)
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

  def delete(id: Long, success: Long => Unit, fail: Exception => Unit) {
    try {
      DB.withConnection { implicit c =>
        SQL("update user set deleted=FALSE where id = {id}").on(
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