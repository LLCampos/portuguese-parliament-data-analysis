scalaVersion := "2.13.6"


name := "data-processor"
organization := "io.luiscampos"
version := "1.0"


val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

