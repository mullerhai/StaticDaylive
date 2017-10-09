package com.linkme.hdfs.utils

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.hdfs.protocol.ClientProtocol
import org.apache.hadoop.mapreduce.Job.JobState
import org.apache.hadoop.mapreduce.{Cluster, Job}

/**
  * Created by linkedmemuller on 2017/9/29.
  */
object LinkedmeHdfsUtils {

  def killMapReduceByJobId(jobId:String,job:Job): Unit ={
    val cluster:Cluster=job.getCluster
    if(job.getJobState==JobState.RUNNING){

    }
  }

  //mapreduce 运行成功后 得到 成功后的输出文件
  def getSuccefulOutFile(conf :Configuration,job:Job,outputPath:String,successFilesubfix:String="/part-r-00000",distLocalFilePath:String="/hadoopJars"):Unit={

    if (job.waitForCompletion(true)){
      if(job.isSuccessful){
        val fs =FileSystem.get(conf)
        val srcHdfsFile=outputPath+successFilesubfix
        val srcPath=new Path(srcHdfsFile)
        val job_id:String=job.getJobID.toString
        val distPath=new Path(distLocalFilePath+"/"+job.getJobName.toString+"/"+job_id)
        if (fs.exists(srcPath)){
          fs.copyToLocalFile(srcPath,distPath)
      }

      }

    }

  }
  //检查hdfs 是否有同名的输出文件目录，若有则删除
  def checkOuputExist(conf:Configuration,pathStr:String):Unit={
    val fs =FileSystem.get(conf)
    val path=new Path(pathStr)
    if (fs.exists(path)){
      try{
        fs.delete(new Path(pathStr),true)
      }

      println("delete the ouput path successfully")
    }

  }

}
