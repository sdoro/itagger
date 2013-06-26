package scalatest.models

import org.scalatest.FlatSpec
import scala.collection.mutable.Stack
import controllers.JsonResponse
import models.User

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */

class UserSpec extends FlatSpec {

  "user" should "create and count" in {
    val newUser = new User(0, "pippo")
    User.create(newUser,
      success => { assert(true) },
      fail => { assert(false) }
      )
    val users = User.all()
    assert(users.count(p => true) > 0)
  }

  
}