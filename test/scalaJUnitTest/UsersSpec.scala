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
    "db.default.driver" -> "org.h2.Driver", 
    "db.default.url"    -> "jdbc:h2:bin/db/test/h2",
    "db.default.user"    -> "sa"))){
        val numUser = User.all().count(p=>true)
        
        User.create(new User(0,"abdul"), 
            success=>{
              assert(true)
              }, 
            fail=>{
              assert(false)
            })
        assert(User.all().count(p=>true)==(numUser+1))
      }
      success
    }
  }
}