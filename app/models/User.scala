package models

case class User(id: Long, email: String)

object User {
  
  def all(): List[User] = Nil
  
  def create(email: String) {}
  
  def show(id: Long) = {}
    
  def delete(id: Long) {}
  
}