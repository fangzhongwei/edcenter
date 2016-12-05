lazy val commonSettings = Seq(
  javacOptions ++= Seq("-encoding", "UTF-8"),
  organization := "com.lawsofnature.edcenter",
  version := "1.0",
  scalaVersion := "2.11.8",
  libraryDependencies ++= Seq(
    "commons-codec" % "commons-codec" % "1.10",
    "net.codingwell" % "scala-guice_2.11" % "4.1.0",
    "com.lawsofnature.common" % "common-edecrypt_2.11" % "1.0"
  )
)

lazy val edclient = (project in file("edclient")).settings(commonSettings: _*).settings(
  name := """edclient""",
  libraryDependencies ++= Seq(
    "com.lawsofnature.common" % "common-ice_2.11" % "1.0"
  )
)

lazy val edserver = (project in file("edserver")).settings(commonSettings: _*).settings(
  name := """edserver""",
  libraryDependencies ++= Seq(
    "mysql" % "mysql-connector-java" % "5.1.36",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0-M1",
    "com.typesafe.slick" %% "slick" % "3.2.0-M1",
    "com.lawsofnature.common" % "common-mysql_2.11" % "1.0",
    "com.lawsofnature.edcenter" % "edclient_2.11" % "1.0",
    "com.lawsofnature.common" % "common-utils_2.11" % "1.0",
    "com.lawsofnature.common" % "common-error_2.11" % "1.0"
  )
)