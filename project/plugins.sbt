resolvers += "jgit-repo" at "https://download.eclipse.org/jgit/maven"

addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "latest.integration")  // https://github.com/sbt/sbt-ghpages

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "latest.integration")