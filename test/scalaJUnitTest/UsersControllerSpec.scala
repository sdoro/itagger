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
class UsersControllerSpec extends Specification {

  //val json = Json.parse("""{"username":"myfile.avi", "id":12345, "lngt":"qua", "lat":"diqui", "deleted": false}""") // Your WS result
  //
  //
  //val user = json.as[User]

  "users controller" should {
    "create new user" in {
      implicit val app = FakeApplication()
      running(app) {
        val reqContent = """{"username":"myfile.avi", "id":12345, "lngt":"qua", "lat":"diqui", "deleted": false}"""
        val fakeRequest = FakeRequest(POST,
          "/users/new")
          .withHeaders(
            Scala.Tuple("content-type", "application/json"))
          .withBody(reqContent)

        val result = await(UsersController.create()(fakeRequest).run)
        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        //      charset(result) must beSome("utf-8")
        contentAsString(result) must beEqualTo(reqContent)
      }
    }
  }

}