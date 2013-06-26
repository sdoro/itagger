package models

import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.Play.current

case class User(id: Long,
  username: String,
  deleted: Boolean = false) {

}

object User {

  val user = {
    get[Long]("id") ~
      get[String]("username") map {
        case id ~ username => User(id, username)
      }
  }

  def all(): List[User] = DB.withConnection {
    implicit c => SQL("select * from user where deleted='0'").as(user *)
  }

  def create(user: User, success: User => Unit, fail: Exception => Unit) {
    try {
      DB.withConnection { implicit c =>
        SQL("insert into user (username) values ({username})").on(
          'username -> user.username).executeUpdate()
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
        SQL("update user set deleted='0' where id = {id}").on(
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