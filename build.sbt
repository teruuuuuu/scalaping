
name := "scalaping"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.7.2",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc4",
  "org.scalikejdbc" %% "scalikejdbc"       % "3.1.0",
  "org.scalikejdbc" %% "scalikejdbc-interpolation" % "2.5.0",
  "org.scalikejdbc" %% "scalikejdbc-config"        % "2.5.0",
  "ch.qos.logback"  %  "logback-classic"           % "1.2.3"
)

lazy val assemblySettings = Seq(
  test in assembly := {},
  assemblyMergeStrategy in assembly := {
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }
)

lazy val app = (project in file(".")).
  settings(assemblySettings: _*).
  settings(resourceDirectory in Compile := baseDirectory.value / "src" / "main" / "resources")