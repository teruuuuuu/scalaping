
name := "scalaping"

version := "0.1"

scalaVersion := "2.12.4"
// scalaVersion := "2.11.12"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.8",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.8" % Test,
  "com.enragedginger" %% "akka-quartz-scheduler" % "1.6.1-akka-2.5.x",
  "org.jsoup" % "jsoup" % "1.7.2",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc4",
  "org.scalikejdbc" %% "scalikejdbc"       % "3.1.0",
  "org.scalikejdbc" %% "scalikejdbc-interpolation" % "2.5.0",
  "org.scalikejdbc" %% "scalikejdbc-config"        % "2.5.0",
  "ch.qos.logback"  %  "logback-classic"           % "1.2.3",
  "com.github.gilbertw1" %% "slack-scala-client" % "0.2.2",
  "org.apache.httpcomponents" % "httpclient" % "4.5.4"
)

lazy val assemblySettings = Seq(
  test in assembly := {},
  assemblyMergeStrategy in assembly := {
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  },
  mainClass in assembly := Some("com.teruuu.scalaping.scheduler.TaskScheduler")
)

lazy val app = (project in file(".")).
  settings(assemblySettings: _*).
  settings(resourceDirectory in Compile := baseDirectory.value / "src" / "main" / "resources")