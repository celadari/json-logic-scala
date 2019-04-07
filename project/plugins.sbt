resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.5.2")  // https://github.com/sbt/sbt-ghpages

addSbtPlugin("com.typesafe.sbt" % "sbt-osgi" % "0.7.0") // https://github.com/sbt/sbt-osgi/

addSbtPlugin("me.lessis" % "ls-sbt" % "ls(ls-sbt, softprops, ls)")
