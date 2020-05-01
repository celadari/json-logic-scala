resolvers += "jgit-repo" at "https://download.eclipse.org/jgit/maven"

addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.6.3")  // https://github.com/sbt/sbt-ghpages

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "2.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")
