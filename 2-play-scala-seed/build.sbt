
name := "2-play-scala-seed"
organization := "2-play-scala-seed"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, JavaAgent)

scalaVersion := "2.11.12"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1" % Test

resolvers += "DD snapshots" at "https://oss.jfrog.org/oss-snapshot-local"

val datadogAgentVersion = "0.13.0-SNAPSHOT"
libraryDependencies ++= Seq(
  "org.asynchttpclient" % "async-http-client" % "2.0.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "io.opentracing" % "opentracing-api" % "0.31.0",
  "io.opentracing" % "opentracing-util" % "0.31.0",
  "com.datadoghq" % "dd-trace-ot" % datadogAgentVersion,
  "com.datadoghq" % "dd-trace-api" % datadogAgentVersion,
  "com.lightstep.tracer" % "lightstep-tracer-jre" % "0.14.5",
  "com.lightstep.tracer" % "tracer-grpc" % "0.15.6",
  "io.grpc" % "grpc-okhttp" % "1.15.0",
  ws
)

javaAgents += "com.datadoghq" % "dd-java-agent" % datadogAgentVersion

fork in run := true
javaOptions in run := {
  Seq(
    "-Ddd.service.name=datadog_test_app",
    "-Ddd.trace.span.tags=env:dev",
    "-Ddd.integration.netty.enabled=true",
    "-Ddd.writer.type=LoggingWriter",
    "-Ddd.priority.sampling=true"
  ) ++ resolvedJavaAgents.value.map("-javaagent:" + _.artifact)
}

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "2-play-scala-seed.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "2-play-scala-seed.binders._"
