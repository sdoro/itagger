package scalaJUnitTest

import org.specs2.mutable._
import play.api.db.DB
import play.api.Play.current
import anorm._
import play.api.test._
import play.api.test.Helpers._
import play.api.db.evolutions.Evolutions
import models.User
import play.mvc.Controller
import controllers.UsersController
import controllers.Application
import play.api.mvc._
import play.libs.Scala
import scala.util.Random

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class UsersSpec extends Specification {

  //  "users controller" should {
  //    "create new user" in {
  //      implicit val app = FakeApplication()
  //      running(app) {
  //        val reqContent = """{"name": "New Group", "collabs": "foo"}"""
  //        val fakeRequest = FakeRequest(POST,
  //          "/user/new")
  //          .withHeaders(
  //            Scala.Tuple("content-type", "application/json"))
  //          .withBody(reqContent)
  //
  //        val result = await(UsersController.create()(fakeRequest).run)
  //        status(result) must equalTo(OK)
  //        contentType(result) must beSome("application/json")
  //        //      charset(result) must beSome("utf-8")
  //        contentAsString(result) must beEqualTo(reqContent)
  //      }
  //    }
  //  }
  "users model" should {
    "be created without errors" in {
      running(FakeApplication(additionalConfiguration = Map(
        "db.default.driver" -> "org.h2.Driver",
        "db.default.url" -> "jdbc:h2:bin/db/test/h2",
        "db.default.user" -> "sa"))) {
        val numUser = User.all().count(p => true)
        val username = Random.alphanumeric.take(8).mkString
        User.create(new User(0, username, "56.67", "34.567"),
          success => {
            assert(true)
          },
          fail => {
            assert(false)
          })
        assert(User.all().count(p => true) == (numUser + 1))
        assert(User.all().count(p => p.username.equalsIgnoreCase(username)) == 1)
      }
      success
    }

     "update without errors" in {
      running(FakeApplication(additionalConfiguration = Map(
        "db.default.driver" -> "org.h2.Driver",
        "db.default.url" -> "jdbc:h2:bin/db/test/h2",
        "db.default.user" -> "sa"))) {
        val numUser = User.all().count(p => true)
        assert(numUser > 0)
        val firstUser = User.all()(0)
        val userUpdated = firstUser.copy(
          lat = Random.nextDouble().toString(),
          lngt = Random.nextDouble().toString())
        User.update(userUpdated,
          success => {
            assert(true)
          },
          fail => {
            assert(false)
          })
        assert(User.all().count(p => true) == (numUser))
      }
    }
    
    "createdOrUpdated update without errors" in {
      running(FakeApplication(additionalConfiguration = Map(
        "db.default.driver" -> "org.h2.Driver",
        "db.default.url" -> "jdbc:h2:bin/db/test/h2",
        "db.default.user" -> "sa"))) {
        val numUser = User.all().count(p => true)
        assert(numUser > 0)
        val firstUser = User.all()(0)
        val userUpdated = firstUser.copy(
          lat = Random.nextDouble().toString(),
          lngt = Random.nextDouble().toString())
        User.createOrUpdate(userUpdated,
          success => {
            assert(true)
          },
          fail => {
            assert(false)
          })
        assert(User.all().count(p => true) == (numUser))
      }
    }

    "createdOrUpdated create without errors" in {
      running(FakeApplication(additionalConfiguration = Map(
        "db.default.driver" -> "org.h2.Driver",
        "db.default.url" -> "jdbc:h2:bin/db/test/h2",
        "db.default.user" -> "sa"))) {
        val numUser = User.all().count(p => true)
        assert(numUser > 0)

        val userNew = new User(
          0,
          Random.alphanumeric.take(8).mkString,
          Random.nextDouble().toString(),
          Random.nextDouble().toString())
        User.createOrUpdate(userNew,
          success => {
            assert(true)
          },
          fail => {
            assert(false)
          })
        assert(User.all().count(p => true) == (numUser + 1))
      }
      success
    }
  }
}