ThisBuild / version := "0.9.2-SNAPSHOT"
ThisBuild / organization := "com.markblokpoel"
ThisBuild / scalaVersion := "2.13.9"

lazy val runCoherenceDemo = taskKey[Unit]("Run Coherence demonstration.")
lazy val runVertexCoverDemo = taskKey[Unit]("Run VertexCover demonstration.")
lazy val runGraphsDemo = taskKey[Unit]("Run GraphsDemo demonstration.")
lazy val runSubsetChoiceDemo = taskKey[Unit]("Run SubsetChoice demonstration.")

lazy val mathlib = (project in file("."))
  .settings(
    // Shared settings
    name := "mathlib",
    autoAPIMappings := true,
    fullRunTask(runCoherenceDemo, Compile, "mathlib.demos.Coherence", "arg1", "arg2"),
    fullRunTask(runVertexCoverDemo, Compile, "mathlib.demos.VertexCover", "arg1", "arg2"),
    fullRunTask(runGraphsDemo, Compile, "mathlib.demos.GraphsDemo", "arg1", "arg2"),
    fullRunTask(runSubsetChoiceDemo, Compile, "mathlib.demos.SubsetChoice", "arg1", "arg2")
  )


