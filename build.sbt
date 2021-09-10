name := "json-logic-scala"

organization := "com.celadari"

homepage := Some(url("https://jsonlogicscala.com"))

developers := List(Developer(
  "celadari",
  "Charles",
  "charles@jsonlogicscala.com",
  url("https://github.com/celadari")
))

licenses += ("MIT", url("https://mit-license.org/"))

version := "1.9.14"

scalaVersion := "2.13.2"

crossScalaVersions := Seq("2.11.12", "2.12.13", "2.13.2")

resolvers ++= Seq(
  "sonatype-snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots",
  "sonatype-releases"  at "https://s01.oss.sonatype.org/content/repositories/releases",
)

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

// scalastyle:off magic.number
def resolveVersion(scalaV: String, versionsResolver: Map[String, String]): String = versionsResolver(scalaV.slice(0, 4))

val typeSafeVersions = Map("2.11" -> "2.7.4", "2.12" -> "2.8.1", "2.13" -> "2.8.1")

libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-json" % resolveVersion(scalaVersion.value, typeSafeVersions),
    "org.apache.xbean" % "xbean-finder" % "4.20",
    "org.apache.xbean" % "xbean-reflect" % "4.20",
    "org.scalatest" %% "scalatest" % "3.2.9" % Test
)

versionScheme := Some("early-semver")

scalacOptions ++= Seq(
  "-encoding",
  "utf8",
  "-deprecation",
  "-feature",
  "-language:higherKinds",
  if (Set("2.12", "2.13").contains(scalaVersion.value.slice(0, 4))) "-Ywarn-unused:imports" else "-Ywarn-unused-import",
  if (Set("2.11", "2.12").contains(scalaVersion.value.slice(0, 4))) "-Xmax-classfile-name" else "",
  if (Set("2.11", "2.12").contains(scalaVersion.value.slice(0, 4))) "128" else "",
  "-language:existentials",
  if (scalaVersion.value.slice(0, 4) == "2.11") "-Xfatal-warnings"
  else if (scalaVersion.value.slice(0, 4) == "2.12") ""
  else "-Werror"
)

Test / testOptions += Tests.Argument("-oGK")

// scalastyle unit test configuration
Test / scalastyleConfig := baseDirectory.value / "scalastyle-test-config.xml"

// Publishing stuff for sonatype
sonatypeBundleDirectory := baseDirectory.value / target.value.getName / s"scala-${scalaBinaryVersion.value}" / "sonatype-staging" / version.value
publishTo := sonatypePublishToBundle.value
sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

crossPaths := true

publishConfiguration := publishConfiguration.value.withOverwrite(true)

credentials += sys.env.get("SONATYPE_USERNAME").zip(sys.env.get("SONATYPE_PASSWORD"))
  .headOption
  .map{case (username, password) => Credentials("Sonatype Nexus Repository Manager", "s01.oss.sonatype.org", username, password)}
  .getOrElse(Credentials(Path.userHome / ".sbt" / ".credentials"))

Test / publishArtifact := false

publishMavenStyle := true

pomIncludeRepository := { _ => false }

scmInfo := Some(ScmInfo(url("https://github.com/celadari/json-logic-scala"),
                            "git@github.com:celadari/json-logic-scala.git"))
