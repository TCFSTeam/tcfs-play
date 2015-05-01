// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.4")

addSbtPlugin("com.jamesward" % "play-auto-refresh" % "0.0.11")// Use the Play sbt plugin for Play projects

addSbtPlugin("com.github.play2war" % "play2-war-plugin" % "1.2-beta4")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")