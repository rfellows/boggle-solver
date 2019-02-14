name := """boggle-solver"""
organization := "com.robfellows"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "com.google.guava" % "guava" % "27.0.1-jre"
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.5"

libraryDependencies += "org.webjars" %% "webjars-play" % "2.7.0"
libraryDependencies += "org.webjars.npm" % "lodash" % "4.17.11"
libraryDependencies += "org.webjars.npm" % "axios" % "0.18.0"
libraryDependencies += "org.webjars.npm" % "vue" % "2.6.6"
libraryDependencies += "org.webjars" % "bootstrap" % "4.2.1"