package com.linkme.statistics

import java.io.{DataInput, DataOutput}

import org.apache.hadoop.io.{ LongWritable, Text, WritableComparable}

/**
  * Created by linkedmemuller on 2017/9/27.
  */
class DayUserPair(val day:Text,val user :Text,val perline:LongWritable) extends  WritableComparable[DayUserPair]{
  //def this()=this(new Text(),new Text(),new IntWritable())
  def this()={
    this(new Text(),new Text(),new LongWritable())
  }

  override def compareTo(o: DayUserPair): Int = {

   user.compareTo(o.user)
  }


  override def readFields(in: DataInput): Unit = {

    day.readFields(in)
    user.readFields(in)
    perline.readFields(in)
  }

  override def write(out: DataOutput): Unit ={

    day.write(out)
    user.write(out)
    perline.write(out)

  }

  override def toString: String = {

    day.toString+"\t"+user.toString+"\t"+perline.toString
  }


  override def equals(obj: scala.Any): Boolean = {
    if(obj.isInstanceOf[DayUserPair]){
      val duObj:DayUserPair=obj.asInstanceOf[DayUserPair]
      val comUser:Boolean=user.compareTo(duObj.user).>(0)
      val comDay:Boolean=day.compareTo(duObj.day).>(0)
      if( comUser && comDay ){
        return true
      }else{
        return false
      }

    }else{
      return false
    }
  }

  override def hashCode(): Int = super.hashCode()
}

//object DayUserPair{
//
//  //  def apply (val day:Text,val user:Text,val perline:IntWritable):DayUserPair={
////
////    val dayUserPair=new DayUserPair(day,user,perline)
////    return dayUserPair
////  }
////def apply( day: Text, user: Text, perline: IntWritable): DayUserPair ={
////      val dayUserPair=new DayUserPair(day,user,perline)
////      return dayUserPair
////  }
//}
