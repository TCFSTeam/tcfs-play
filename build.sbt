import com.github.play2war.plugin._
import play.Project._
import sbt.Keys._



version := "1.0-SNAPSHOT"

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

Play2WarPlugin.play2WarSettings

Play2WarKeys.servletVersion := "3.0"