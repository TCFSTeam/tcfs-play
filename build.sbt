import play.Project._

name := "tcfs-play"

version := "1.0"

playJavaSettings

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean
)

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)

libraryDependencies += "postgresql" % "postgresql" % "9.1-901-1.jdbc4"