ThisBuild / scalaVersion := "2.13.9"

lazy val mathlib = project.in(file(".")).
  settings(
    // Shared settings
    name := "mathlib",
    version := "0.8.1",
    libraryDependencies += "org.typelevel" %% "spire" % "0.18.0"
  )