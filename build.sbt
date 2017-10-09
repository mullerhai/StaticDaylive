name := "StaticDaylive"

version := "1.0"

scalaVersion := "2.12.1"

fork := true
libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-common" % "2.8.1",
  "org.apache.hadoop" % "hadoop-hdfs" % "2.8.1",
  "org.apache.hadoop" % "hadoop-client" % "2.8.1",
  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "2.8.1",

  "org.apache.hadoop" % "hadoop-mapreduce-client-common" % "2.8.1",
    // https://mvnrepository.com/artifact/org.apache.hive/hive-exec
    "org.apache.hive" % "hive-exec" % "2.3.0" exclude("org.pentaho" , "pentaho-aggdesigner-algorithm") ,
  // https://mvnrepository.com/artifact/org.apache.hive/hive-metastore
   "org.apache.hive" % "hive-metastore" % "2.3.0",
  // https://mvnrepository.com/artifact/org.apache.hive/hive-common
   "org.apache.hive" % "hive-common" % "2.3.0",
  // https://mvnrepository.com/artifact/org.apache.hive/hive-serde
  "org.apache.hive" % "hive-serde" % "2.3.0",
  "org.apache.hive" % "hive-service" % "2.3.0" exclude("org.pentaho" , "pentaho-aggdesigner-algorithm") ,
  // https://mvnrepository.com/artifact/org.apache.hive/hive-jdbc
  "org.apache.hive" % "hive-jdbc" % "2.3.0" exclude("org.pentaho" , "pentaho-aggdesigner-algorithm") ,
  // https://mvnrepository.com/artifact/org.apache.hive/hive-shims
   "org.apache.hive" % "hive-shims" % "2.3.0",

  // https://mvnrepository.com/artifact/org.apache.hbase/hbase-client
   "org.apache.hbase" % "hbase-client" % "2.0.0-alpha3",
  // https://mvnrepository.com/artifact/org.apache.hbase/hbase-common
   "org.apache.hbase" % "hbase-common" % "2.0.0-alpha3",
  // https://mvnrepository.com/artifact/org.apache.hbase/hbase-server
  "org.apache.hbase" % "hbase-server" % "2.0.0-alpha3",
  // https://mvnrepository.com/artifact/org.apache.hbase/hbase-protocol
"org.apache.hbase" % "hbase-protocol" % "2.0.0-alpha3",
  // https://mvnrepository.com/artifact/org.apache.hbase/hbase-hadoop-compat
"org.apache.hbase" % "hbase-hadoop-compat" % "2.0.0-alpha3",
  // https://mvnrepository.com/artifact/org.apache.hbase/hbase
 //"org.apache.hbase" % "hbase" % "2.0.0-alpha3",
  // https://mvnrepository.com/artifact/log4j/log4j
  // https://mvnrepository.com/artifact/org.pentaho/pentaho-aggdesigner-algorithm
 //"org.pentaho" % "pentaho-aggdesigner-algorithm" % "5.1.5-jhyde" % ,

"log4j" % "log4j" % "1.2.17",
  // https://mvnrepository.com/artifact/mysql/mysql-connector-java
  "mysql" % "mysql-connector-java" % "8.0.7-dmr",
  // https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-hdfs-nfs
   "org.apache.hadoop" % "hadoop-hdfs-nfs" % "2.8.1"




)


resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"

resolvers += "geotools" at "http://download.osgeo.org/webdav/geotools"

resolvers += "maven" at "https://repo.maven.apache.org/maven2"

resolvers += "twitter" at "https://maven.twttr.com"

resolvers += "jnegre" at "https://bintray.com/artifact/download/jnegre/maven"

resolvers += "con-jars" at "http://conjars.org/repos"

resolvers += "clojars" at "http://clojars.org/repo"

resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/",
  "cdh.repo" at "https://repository.cloudera.com/artifactory/cloudera-repos",
"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
"ClouderaRepo" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
"Pentaho" at "http://repository.pentaho.org/artifactory/pentaho",
"Thrift" at "http://people.apache.org/~rawson/repo/",
"lessis" at "http://repo.lessis.me",
"coda" at "http://repo.codahale.com",
Resolver.url("sbt-plugin-releases", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-relea"))(Resolver.ivyStylePatterns)
)

val exeShelldaylive=TaskKey[Unit]("daylive","Execute the hadoop shell",KeyRanks.ATask)

exeShelldaylive :={
  println("Excute the shell script for run hadoop mapreduce on AWS ")

  "./src/main/resources/deployDay.sh"!
}
        