// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.1.1")

//addSbtPlugin( "org.scalatest" % "scalatest_2.10" % "1.9.1")

addSbtPlugin( "net.debasishg" % "sjson_2.9.1" % "0.17")

