//  
//import play.api.libs.json._
//import play.api.libs.functional.syntax._
//
//
////http://stackoverflow.com/questions/14291666/play-2-1-rc2-converting-jsvalue-to-scala-value
//
//case class User(id: Long, username: String, lngt: String, lat:String, deleted: Boolean = false)
//implicit val userFormat = Json.format[User]
//
//
//val json = Json.parse("""{"username":"myfile.avi", "id":12345, "lngt":"qua", "lat":"diqui", "deleted": false}""") // Your WS result
//
//
//val user = json.as[User]
//
//
//
//user.lngt
//user.id
//user.username
//user.lat
//user.deleted
