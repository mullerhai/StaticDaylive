package com.linkme.hdfs.utils

/**
  * Created by linkedmemuller on 2017/9/29.
  */
class RecordParseUtils {

}
object  RecordParseUtils{
  // 解析linkpage日志 形成  appOps[install | open] 和 identityId
  def getLinkPageAppOpsandIdentity(logContent: String): Seq[String] = {
    var appOps: String = null
    var identity_Id: String = null
    var flag: Boolean = false
    if (logContent.length > 100) {
      val appOPsIndexCon: String = logContent.substring(0, 100)
      if (appOPsIndexCon.contains("open")) {
        flag = true
        appOps = "open"
      } else if (appOPsIndexCon.contains("install")) {
        flag = true
        appOps = "install"
      }
    }
    if (flag == true) {
      val identiIndex = logContent.indexOf("\"identity_id\":")
      val idfaIndex = logContent.indexOf("\"idfa\":")
      val boolIde:Boolean=identiIndex>0
      val boolidfa:Boolean=idfaIndex>0
      if(boolIde&&boolidfa){
        val identityId = logContent.substring(identiIndex + 14, idfaIndex - 1)
        if (identityId.contains("\n")) {
          val sp = identityId.split("\n")
          identity_Id = sp(0) + sp(1)
          println(identity_Id)
        }else{
          identity_Id=identityId
          println(identity_Id)
        }
      }
    }
    val logField = Seq(appOps, identity_Id)
    return logField
  }
}
