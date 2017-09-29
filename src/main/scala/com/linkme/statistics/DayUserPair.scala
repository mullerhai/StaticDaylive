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

    val x = this.day.compareTo(o.day)
    if (x == 0) {
      val y = this.user.compareTo(o.user)
      if (y == 0) {
        0
      } else {
        y
      }
    } else {
      x
    }

  }


  override def readFields(in: DataInput): Unit = {

    this.day.readFields(in)
    this.user.readFields(in)
    this.perline.readFields(in)
  }

  override def write(out: DataOutput): Unit ={

    this.day.write(out)
    this.user.write(out)
    this.perline.write(out)

  }

  override def toString: String = {

    this.day.toString+"\t"+this.user.toString+"\t"+this.perline.toString
  }


  override def equals(obj: scala.Any): Boolean = {
    if(obj.isInstanceOf[DayUserPair]){
      val duObj:DayUserPair=obj.asInstanceOf[DayUserPair]
      val comUser=this.user.compareTo(duObj.user)
      val comDay=this.day.compareTo(duObj.day)
      val comu=comUser==0
      if( comUser==0 ){
        return true
      }else{
        return false
      }

    }else{
      return false
    }
  }

  override def hashCode(): Int = {
    return this.day.hashCode()+this.user.hashCode()
  }
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
