ThisBuild / scalaVersion := "2.13.4"

lazy val root = project.in(file(".")).
  aggregate(mathlib.js, mathlib.jvm).
  settings(
    publish := {},
    publishLocal := {},
  )

lazy val mathlib = crossProject(JSPlatform, JVMPlatform).in(file(".")).
  settings(
    // Shared settings
    name := "mathlib",
    version := "0.8.1",
    libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.12.0",
  ).
  jvmSettings(
    // Add JVM-specific settings here
    libraryDependencies += "org.scala-js" %% "scalajs-stubs" % "1.0.0" % "provided",
  ).
  jsSettings(
    // Add JS-specific settings here
    scalaJSUseMainModuleInitializer := true,
  )