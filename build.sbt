import play.Project._

name := "tcfs-play"

version := "1.0"

playJavaSettings

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean
)