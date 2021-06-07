name := "json-logic-scala"

organization := "com.github.celadari"

homepage := Some(url("https://github.com/celadari/json-logic-scala"))

version := "2.0.0"

scalaVersion := "2.13.2"

crossScalaVersions := Seq("2.11.12", "2.12.13", "2.13.2")

resolvers ++= Seq(
  "sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "sonatype-releases"  at "https://oss.sonatype.org/content/repositories/releases",
)

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

// scalastyle:off magic.number
def resolveVersion(scalaV: String, versionsResolver: Map[String, String]): String = versionsResolver(scalaV.slice(0, 4))

val typeSafeVersions = Map("2.10" -> "2.6.14", "2.11" -> "2.7.4", "2.12" -> "2.8.1", "2.13" -> "2.8.1")

libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-json" % resolveVersion(scalaVersion.value, typeSafeVersions),
    "org.apache.xbean" % "xbean-finder" % "4.20",
    "org.apache.xbean" % "xbean-reflect" % "4.20",
    "org.scalatest" %% "scalatest" % "3.2.9" % Test
)

// scalacOptions ++= ("-feature" :: "-language:postfixOps" :: "-language:implicitConversions" :: Nil)
scalacOptions ++= Seq(
  "-encoding", "utf8",
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
publishTo := {
  if (version.value.endsWith("SNAPSHOT")) Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
  else Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}

publishConfiguration := publishConfiguration.value.withOverwrite(true)

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

Test / publishArtifact := false

publishMavenStyle := true

pomIncludeRepository := { _ => false }

pomExtra :=
         <scm>
            <url>git@github.com:celadari/json-logic-scala.git</url>
            <connection>scm:git:git@github.com:celadari/json-logic-scala.git</connection>
         </scm>
         <developers>
            <developer>
              <id>celadari</id>
              <name>Charles-Edouard LADARI</name>
              <url>https://github.com/celadari</url>
            </developer>
         </developers>

licenses += ("MIT", url("https://mit-license.org/"))

// Scaladoc publishing stuff