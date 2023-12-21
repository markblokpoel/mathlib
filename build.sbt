ThisBuild / version := "0.9.2-b1"
ThisBuild / organization := "com.markblokpoel"
ThisBuild / scalaVersion := "2.13.9"

lazy val mathlib = (project in file("."))
  .settings(
    // Shared settings
    name := "mathlib",
    autoAPIMappings := true
  )
