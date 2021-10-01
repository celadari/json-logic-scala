// Copyright 2019 celadari. All rights reserved. MIT license.

resolvers += "jgit-repo" at "https://download.eclipse.org/jgit/maven"

addSbtPlugin("com.beautiful-scala" % "sbt-scalastyle" % "1.5.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.8.2")

addSbtPlugin("com.github.sbt" % "sbt-pgp" % "2.1.2")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.7")
