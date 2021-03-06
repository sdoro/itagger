//package scalaJUnitTest
//
//import org.specs2.mutable._
//import play.api.db.DB
//import play.api.Play.current
//import anorm._
//import play.api.test._
//import play.api.test.Helpers._
//import play.api.db.evolutions.Evolutions
//import models.User
//import play.mvc.Controller
//import controllers.UsersController
//import controllers.Application
//import play.api.mvc._
//import play.libs.Scala
//import scala.util.Random
//import scala.collection.mutable.ListBuffer
//
///**
// * Add your spec here.
// * You can mock out a whole application including requests, plugins etc.
// * For more information, consult the wiki.
// */
//class UsersSpec extends Specification {
//
//  //  "users controller" should {
//  //    "create new user" in {
//  //      implicit val app = FakeApplication()
//  //      running(app) {
//  //        val reqContent = """{"name": "New Group", "collabs": "foo"}"""
//  //        val fakeRequest = FakeRequest(POST,
//  //          "/user/new")
//  //          .withHeaders(
//  //            Scala.Tuple("content-type", "application/json"))
//  //          .withBody(reqContent)
//  //
//  //        val result = await(UsersController.create()(fakeRequest).run)
//  //        status(result) must equalTo(OK)
//  //        contentType(result) must beSome("application/json")
//  //        //      charset(result) must beSome("utf-8")
//  //        contentAsString(result) must beEqualTo(reqContent)
//  //      }
//  //    }
//  //  }
//  "users model" should {
//    "be created without errors" in {
//      running(FakeApplication(additionalConfiguration = Map(
//        "db.default.driver" -> "org.h2.Driver",
//        "db.default.url" -> "jdbc:h2:bin/db/test/h2",
//        "db.default.user" -> "sa"))) {
//        val numUser = User.all().count(p => true)
//        val username = Random.alphanumeric.take(8).mkString
//        User.create(new User(0, username, "56.67", "34.567"),
//          success => {
//            assert(true)
//          },
//          fail => {
//            assert(false)
//          })
//        assert(User.all().count(p => true) == (numUser + 1))
//        assert(User.all().count(p => p.username.equalsIgnoreCase(username)) == 1)
//      }
//      success
//    }
//
//    "update without errors" in {
//      running(FakeApplication(additionalConfiguration = Map(
//        "db.default.driver" -> "org.h2.Driver",
//        "db.default.url" -> "jdbc:h2:bin/db/test/h2",
//        "db.default.user" -> "sa"))) {
//        val numUser = User.all().count(p => true)
//        assert(numUser > 0)
//        val firstUser = User.all()(0)
//        val userUpdated = firstUser.copy(
//          lat = Random.nextDouble().toString(),
//          lngt = Random.nextDouble().toString())
//        User.update(userUpdated,
//          success => {
//            assert(true)
//          },
//          fail => {
//            assert(false)
//          })
//        assert(User.all().count(p => true) == (numUser))
//      }
//    }
//
//    "createdOrUpdated update without errors" in {
//      running(FakeApplication(additionalConfiguration = Map(
//        "db.default.driver" -> "org.h2.Driver",
//        "db.default.url" -> "jdbc:h2:bin/db/test/h2",
//        "db.default.user" -> "sa"))) {
//        val numUser = User.all().count(p => true)
//        assert(numUser > 0)
//        val firstUser = User.all()(0)
//        val userUpdated = firstUser.copy(
//          lat = Random.nextDouble().toString(),
//          lngt = Random.nextDouble().toString())
//        User.createOrUpdate(userUpdated,
//          success => {
//            assert(true)
//          },
//          fail => {
//            assert(false)
//          })
//        assert(User.all().count(p => true) == (numUser))
//      }
//    }
//
//    "createdOrUpdated create without errors" in {
//      running(FakeApplication(additionalConfiguration = Map(
//        "db.default.driver" -> "org.h2.Driver",
//        "db.default.url" -> "jdbc:h2:bin/db/test/h2",
//        "db.default.user" -> "sa"))) {
//        val numUser = User.all().count(p => true)
//        assert(numUser > 0)
//
//        val userNew = new User(
//          0,
//          Random.alphanumeric.take(8).mkString,
//          Random.nextDouble().toString(),
//          Random.nextDouble().toString())
//        User.createOrUpdate(userNew,
//          success => {
//            assert(true)
//          },
//          fail => {
//            assert(false)
//          })
//        assert(User.all().count(p => true) == (numUser + 1))
//      }
//      success
//    }
//
//    "calc distance between 2 points" in {
//      val user1 = new User(id=0, username="u1", lat=36.12.toString, lngt= -86.67.toString)
//      val user2 = new User(id=0, username="u2", lat=33.94.toString, lngt= -118.40.toString)
//      var calcDist: Double = 0.0
//      User.calcDistInMt(user1, user2,
//        result => {
//          calcDist = result
//        },
//        fail => {
//          assert(false)
//        })
//        val estimatedValueInMt = 2887259.9506071106
//        calcDist must be equalTo estimatedValueInMt//(12848.696 * 1000)
//    }
//
//     "calc neighbours within N meters" in {
//       val users = new ListBuffer[User]
//       users.append(new User(id=0, username="u1", lat=33.94231.toString, lngt= -118.40121.toString))
//       users.append(new User(id=0, username="u2", lat=33.94232.toString, lngt= -118.40122.toString))
//       users.append(new User(id=0, username="u3", lat=33.94233.toString, lngt= -118.40123.toString))
//       users.append(new User(id=0, username="u4", lat=33.94234.toString, lngt= -118.40124.toString))
//       users.append(new User(id=0, username="u5", lat=33.94235.toString, lngt= -118.40125.toString))
//       
//       val user = users(2)
//       
//       val output = User.getNeighbourList(user, users.toList, 10, 
//           result=>{
//             assert(result.count(p=>true)>0)
//           },
//           fail=>{})
//       
//     }
//    
//  }
//}