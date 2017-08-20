lazy val commonSettings = Seq(
  javacOptions ++= Seq("-encoding", "UTF-8"),
  organization := "com.jxjxgo.edcenter",
  version := "1.0",
  scalaVersion := "2.12.2",
  libraryDependencies ++= Seq(
    "commons-codec" % "commons-codec" % "1.10",
    "net.codingwell" % "scala-guice_2.12" % "4.1.0",
    "org.scala-lang" % "scala-library" % "2.12.2",
    "com.trueaccord.scalapb" % "scalapb-runtime_2.12" % "0.6.0-pre5",
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.12" % "2.8.4",
    "com.jxjxgo.common" % "common-finagle-thrift_2.12" % "1.1",
    "com.jxjxgo.common" % "common-edecrypt_2.12" % "1.1"
  )
)

lazy val edclient = (project in file("edclient")).settings(commonSettings: _*).settings(
  name := """edclient""",
  libraryDependencies ++= Seq(
  )
)

lazy val edserver = (project in file("edserver")).settings(commonSettings: _*).settings(
  name := """edserver""",
  mainClass in (Compile, run) := Some("com.jxjxgo.edcenter.rpc.RpcService"),
  libraryDependencies ++= Seq(
    "com.jxjxgo.edcenter" % "edclient_2.12" % "1.0",
    "com.jxjxgo.common" % "common-db_2.12" % "1.2",
    "com.jxjxgo.common" % "common-utils_2.12" % "1.2",
    "com.jxjxgo.common" % "common-cache_2.12" % "1.1",
    "com.jxjxgo.common" % "common-redis_2.12" % "1.1",
    "com.jxjxgo.common" % "common-error_2.12" % "1.6"
  )
)