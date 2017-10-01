package com.linkme.statistics


import java.io.{DataOutput, IOException}
import java.{io, lang}
import java.util.regex.{Matcher, Pattern}

import com.linkme.hdfs.utils.{LinkedmeHdfsUtils, RecordParseUtils}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, LocalDirAllocator, Path}
import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.{FileInputFormat, FileSplit, MultipleInputs}
import org.apache.hadoop.mapreduce.{InputSplit, Job, Mapper, Reducer}
import org.apache.hadoop.mapreduce.lib.output.{FileOutputFormat, TextOutputFormat}

import scala.collection.mutable.Set
/**
  * Created by linkedmemuller on 2017/9/26.
  */
class DayliveStati {
}
object  DayliveStati{


  class DayMapper extends  Mapper[LongWritable,Text,Text,DayUserPair]{


    val day=new Text()
    val user=new Text()
    val perLine=new LongWritable(1)

    @throws[IOException]
    @throws[InterruptedException]
    override def map(key: LongWritable, value: Text, context: Mapper[LongWritable, Text, Text,DayUserPair]#Context): Unit = {

      if (value.toString==null){
        return
      }
      val filepath:FileSplit=context.getInputSplit.asInstanceOf[FileSplit]
      val  dayfile:String=filepath.getPath().toString
      val dayStr = dayfile.substring(33,43)
      println("begin map +"+dayStr)
      day.set(dayStr)

      val word= RecordParseUtils.getLinkPageAppOpsandIdentity(value.toString)
      if (word(1)==null){
        return
      }
      user.set(word(1))
//      user.set(value.toString)
      //println(word(1))
     // println(key.toString+value.toString)
      val dayUserPair=new DayUserPair(day,user,perLine)
     // println(FileOutputFormat.getWorkOutputPath(context))
      context.write(day, dayUserPair)

    }
  }
  class DayReducer extends  Reducer[Text,DayUserPair,Text,LongWritable]{
    @throws[IOException]
    @throws[InterruptedException]
    override def reduce(key: Text, values: lang.Iterable[DayUserPair], context: Reducer[Text, DayUserPair, Text, LongWritable]#Context): Unit = {
      val daySet:Set[String]=Set()
      val iter=values.iterator()
      while (iter.hasNext){
        daySet.add(iter.next().user.toString)
      }
      val dayCount=daySet.size
     val sum =new LongWritable(dayCount)
      context.write(key,sum)
    }
  }

  def main(args: Array[String]): Unit = {

    val conf =new Configuration()
     conf.set("mapreduce.task.output.dir","/data1/mapred/taging")
     conf.setInt("mapreduce.input.lineinputformat.linespermap",15)
     conf.set("mapreduce.cluster.local.dir","/data1/mapred/local/")

    conf.setBoolean("mapreduce.map.output.compress", true)
    conf.set("mapreduce.output.fileoutputformat.outputdir","/data1/mapred/taging")
   // println("begin debug")
    //conf.set("mapred.local.dir","/hadoopJars/")
    val job =new Job(conf,"weekDaylive")

    job.setJarByClass(classOf[DayliveStati])
    job.setMapperClass(classOf[DayMapper])

    //job.setCombinerClass(classOf[DayReducer])
    job.setReducerClass(classOf[DayReducer])

    job.setMapOutputKeyClass(classOf[Text])
    job.setMapOutputValueClass(classOf[DayUserPair])

//    val contextCfgItemName:String="mapreduce.cluster.local.dir"
//     val  mapOutDir=new LocalDirAllocator(contextCfgItemName)
//     val ps=  mapOutDir.getLocalPathForWrite(contextCfgItemName,conf)
//    println(ps.toString)
    job.setOutputKeyClass(classOf[Text])
   // job.setOutputValueClass(classOf[DayUserPair])
    job.setOutputValueClass(classOf[Text])
   // job.setInputFormatClass(classOf[TomcatLogParserInputFormat])
    //job.setOutputFormatClass(classOf[TextOutputFormat[Text,LongWritable]])

    FileInputFormat.addInputPath(job,new Path (args(0)))
    FileInputFormat.setInputDirRecursive(job,true)
    LinkedmeHdfsUtils.checkOuputExist(conf,args(1))
    FileOutputFormat.setOutputPath(job,new Path(args(1)))
    LinkedmeHdfsUtils.getSuccefulOutFile(conf,job,args(1))


    System.exit(if(job.waitForCompletion(true)) 1 else 0)

  }



}
