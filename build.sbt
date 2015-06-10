import com.github.play2war.plugin._
import play.Project._
import sbt.Keys._

name := "tcfs-play"

version := "1"

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

libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "2.35.0" % "test"

Play2WarPlugin.play2WarSettings

Play2WarKeys.servletVersion := "3.0"