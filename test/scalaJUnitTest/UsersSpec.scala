package scalaJUnitTest

import org.specs2.mutable._
import play.api.db.DB
import play.api.Play.current
import anorm._
import play.api.test._
import play.api.test.Helpers._
import play.api.db.evolutions.Evolutions
import models.User

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class UsersSpec extends Specification {
  
  "Evolutions" should {
    "be applied without errors" in {
      running( FakeApplication( additionalConfiguration = Map( 
    "db.default.driver" -> "org.sqlite.JDBC", 
    "db.default.url"    -> "jdbc:sqlite:/D:/Download/hackathon/sandragon/test/scalaJUnitTest" ))){
        User.create(new User(0,"milo"), 
            success=>{
              assert(true)
              }, 
            fail=>{
              assert(false)
            })
//        assert(User.all().count(p=>true)>0)
      }
      success
    }
  }
}