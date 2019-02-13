name := """boggle-solver"""
organization := "com.robfellows"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "com.google.guava" % "guava" % "27.0.1-jre"
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.5"