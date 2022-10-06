ThisBuild / organization := "io.github.markblokpoel"
ThisBuild / organizationName := "markblokpoel"
ThisBuild / organizationHomepage := Some(url("https://www.markblokpoel.com"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/markblokpoel/mathlib"),
    "scm:git@github.markblokpoel/mathlib.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id    = "markblokpoel",
    name  = "Mark Blokpoel",
    email = "mark.blokpoel@gmail.com",
    url   = url("https://www.markblokpoel.com")
  )
)

ThisBuild / description := "mathlib is a supporting library for programming computer simulations for theoretical models. Models are expected to be formally defined using math (e.g., set, graph or probability theory)."
ThisBuild / licenses := List("GNU General Public License v3.0" -> new URL("https://github.com/markblokpoel/mathlib/blob/master/LICENSE"))
ThisBuild / homepage := Some(url("https://github.com/markblokpoel/mathlib"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }

ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

ThisBuild / publishMavenStyle := true

ThisBuild / versionScheme := Some("early-semver")

Global / excludeLintKeys += publishMavenStyle
Global / excludeLintKeys += pomIncludeRepository