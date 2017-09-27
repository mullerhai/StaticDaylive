name := "StaticDaylive"

version := "1.0"

scalaVersion := "2.12.1"

fork := true
libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-common" % "2.8.1",
  "org.apache.hadoop" % "hadoop-hdfs" % "2.8.1",
  "org.apache.hadoop" % "hadoop-client" % "2.8.1",
  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "2.8.1",

  "org.apache.hadoop" % "hadoop-mapreduce-client-common" % "2.8.1"
  // "or.apache.hadoop" % "adoop-mapreduce-client-jobclient" % "2.8.1"
)


val exeShelldaylive=TaskKey[Unit]("exedaylive","Execute the hadoop shell",KeyRanks.ATask)

exeShelldaylive :={
  println("Excute the shell script for run hadoop mapreduce on AWS ")

  "./src/main/resources/deployDay.sh"!
}
        