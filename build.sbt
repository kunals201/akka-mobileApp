name := "akka-app1"

version := "1.0"

//scalaVersion := "2.12.1"
scalaVersion := "2.11.8"

val akkaVersion = "2.4.17"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion
)
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"


libraryDependencies += "log4j" % "log4j" % "1.2.14"

