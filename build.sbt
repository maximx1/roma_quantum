name := """roma_quantum"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
	jdbc,
	anorm,
	cache,
	ws,
	"com.typesafe.slick" %% "slick" % "2.1.0",
	"com.typesafe.play" %% "play-slick" % "0.8.1",
	"org.postgresql" % "postgresql" % "9.4-1200-jdbc41",
	"org.scalatest" %% "scalatest" % "2.2.1" % "test",
	"org.pegdown" % "pegdown" % "1.2.1",
	"com.github.tototoshi" %% "play-json4s-jackson" % "0.3.1",
	"com.github.tototoshi" %% "play-json4s-test-jackson" % "0.3.1" % "test",
	"org.mindrot" % "jbcrypt" % "0.3m",
	"org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
	"com.nulab-inc" %% "play2-oauth2-provider" % "0.13.2",
    "joda-time" % "joda-time" % "2.4",
    "org.joda" % "joda-convert" % "1.6",
    "com.github.tototoshi" %% "slick-joda-mapper" % "1.2.0",
    "com.github.nscala-time" %% "nscala-time" % "1.6.0"
)

ScoverageSbtPlugin.ScoverageKeys.coverageMinimum := 80

ScoverageSbtPlugin.ScoverageKeys.coverageFailOnMinimum := true

ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := "<empty>;controllers.*;views.*"
