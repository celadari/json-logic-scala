name := "json-logic-scala"

organization := "com.github.celadari"

homepage := Some(url("https://github.com/celadari/json-logic-scala"))

version := "1.1.0"

scalaVersion := "2.10.7"

crossScalaVersions := Seq("2.10.7", "2.11.12", "2.12.6", "2.13.1")

resolvers ++= Seq(
  "sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "sonatype-releases"  at "https://oss.sonatype.org/content/repositories/releases"
)

val typeSafeVersions = Map("2.10" -> "2.6.14", "2.11" -> "2.7.4", "2.12" -> "2.8.1", "2.13" -> "2.8.1")
def resolveVersion(scalaV: String, versionsResolver: Map[String, String]): String = versionsResolver(scalaV.slice(0, 4))

libraryDependencies ++= {
  Seq(
    "org.scala-lang" % "scala-compiler" % scalaVersion.value,
    "com.typesafe.play" %% "play-json" % resolveVersion(scalaVersion.value, typeSafeVersions)
  )
}

scalacOptions ++= ("-feature" :: "-language:postfixOps" :: "-language:implicitConversions" :: Nil)

// Publishing stuff for sonatype
publishTo := {
  if (version.value.endsWith("SNAPSHOT")) Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
  else Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}

publishConfiguration := publishConfiguration.value.withOverwrite(true)

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

publishArtifact in Test := false

publishMavenStyle := true

pomIncludeRepository := { _ => false }

pomExtra := (
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
 )

licenses += ("MIT", url("http://mit-license.org/"))

// Scaladoc publishing stuff
enablePlugins(GhpagesPlugin, SiteScaladocPlugin)

git.remoteRepo := "git@github.com:celadari/json-logic-scala.git"