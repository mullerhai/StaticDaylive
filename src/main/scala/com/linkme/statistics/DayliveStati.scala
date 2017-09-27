package com.linkme.statistics


import java.io.{DataOutput, IOException}
import java.{io, lang}
import java.util.regex.{Matcher, Pattern}

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat

import scala.collection.mutable.Set
/**
  * Created by linkedmemuller on 2017/9/26.
  */
class DayliveStati {
}
object  DayliveStati{

  class DayMapper extends  Mapper[Object,Text,Text,DayUserPair]{

    //val patter:Pattern=Pattern.compile("(\"identity_id\":)([0-9]{0,16}){1},")
   // val  patter:Pattern=Pattern.compile("(open|install).*?(\"identity_id\":)([0-9]{0,16}){1},(.+?)")
    //val  patterDay:Pattern=Pattern.compile("(^20.*?)(T.*?)(ip-.*?)")
    //val patternLine:Pattern=Pattern.compile("(^20.*?)(T.*?)(ip-.*?)(open|install).*?(\"identity_id\":)([0-9]{0,16}){1},(.+?)")
    val patternLine:Pattern=Pattern.compile("(open|install).*?(\"identity_id\":)([0-9]{0,16}){1},(.+?)")
    val day=new Text()
    val user=new Text()
    val perLine=new LongWritable(1)

    @throws[IOException]
    @throws[InterruptedException]
    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text,DayUserPair]#Context): Unit = {
      val  text=value.toString()
      val matchs:Matcher=patternLine.matcher(text)
      if (matchs.find()==true) {

        val dayStr = matchs.group(1)
        val identityid = matchs.group(3)
        day.set(dayStr)
        user.set(identityid)
        val dayUserPair=new DayUserPair(day,user,perLine)
        context.write(day, dayUserPair)
        }else{
          return
        }

    }
  }
  class DayReducer extends  Reducer[Text,DayUserPair,Text,LongWritable]{
    @throws[IOException]
    @throws[InterruptedException]
    override def reduce(key: Text, values: lang.Iterable[DayUserPair], context: Reducer[Text, DayUserPair, Text, LongWritable]#Context): Unit = {
      import collection.JavaConversions._

     val daySet=Set()
     val  daySets=daySet++values
      val dayCount=daySets.size
      //context.getCounter(IdentityEnum.normalIdentity).increment(1)
     // sum.set(context.getCounter(IdentityEnum.normalIdentity).getValue)
     val sum =new LongWritable()
      sum.set(dayCount)
      context.write(key,sum)
    }
  }

  def main(args: Array[String]): Unit = {

    val conf =new Configuration()
    val job =new Job(conf,"DayliveStatistic")
    job.setJarByClass(classOf[DayliveStati])
    job.setMapperClass(classOf[DayMapper])
    job.setCombinerClass(classOf[DayReducer])
    job.setReducerClass(classOf[DayReducer])
    job.setMapOutputKeyClass(classOf[Text])
    job.setMapOutputValueClass(classOf[DayUserPair])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[LongWritable])
    FileInputFormat.addInputPath(job,new Path (args(0)))
    FileInputFormat.setInputDirRecursive(job,true)
    FileOutputFormat.setOutputPath(job,new Path(args(1)))
    System.exit(if(job.waitForCompletion(true)) 1 else 0)


  }
}
