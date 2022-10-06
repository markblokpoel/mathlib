ThisBuild / organization := "com.markblokpoel"
ThisBuild / organizationName := "markblokpoel"
ThisBuild / organizationHomepage := Some(url("https://www.markblokpoel.com"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/markblokpoel/mathlib"),
    "scm:git@github.com:markblokpoel/mathlib.git"
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

ThisBuild / publishTo := Some(
  {
    if (isSnapshot.value) Opts.resolver.sonatypeSnapshots
    else Opts.resolver.sonatypeStaging
  }
)

ThisBuild / publishMavenStyle := true

ThisBuild / versionScheme := Some("early-semver")

Global / excludeLintKeys += publishMavenStyle
Global / excludeLintKeys += pomIncludeRepository