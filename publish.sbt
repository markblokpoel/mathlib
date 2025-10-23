/*
Main steps to publish this project, use sbt from terminal to allow for password input:
1. sonatypePrepare
2. publishSigned
3. sonatypeBundleRelease
*/
import xerial.sbt.Sonatype.sonatypeCentralHost

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

ThisBuild / description := "mathlib is a library supporting functional programming that closely resembles mathematical notation."
ThisBuild / licenses := List(
  "GNU General Public License v3.0" -> new URL("https://github.com/markblokpoel/mathlib/blob/main/LICENSE")
)
ThisBuild / homepage := Some(url("https://github.com/markblokpoel/mathlib"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
Global / excludeLintKeys += pomIncludeRepository

ThisBuild / sonatypeCredentialHost := sonatypeCentralHost
ThisBuild / publishTo := sonatypePublishToBundle.value
ThisBuild / sonatypeProfileName := "com.markblokpoel"
ThisBuild / sonatypeRepository := "https://oss.sonatype.org/service/local"
ThisBuild / sonatypeSessionName := "[sbt-sonatype] ${name.value}-${scalaBinaryVersion.value}-${version.value}"

ThisBuild / publishMavenStyle := true
Global / excludeLintKeys += publishMavenStyle

ThisBuild / versionScheme := Some("early-semver")
