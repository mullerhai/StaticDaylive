#!/bin/sh

APP_JAR=/hadoopJars/StaticDaylive.jar
APP_NAME=stNewSubDaylive
APP_DIR=/hadoopJars/$APP_NAME

RUN_SCRIPT=/hadoopJars/runday.sh

echo "check  raw  data or directory"
if [-d '$APP_DIR'];then

    echo "delete last rabbish same name directory and make new dir"
    sudo -i rm -rf $APP_DIR
fi


mkdir -p $APP_DIR

echo "move file and script to the  app dir"
cd  /hadoopJars
sudo -i  mv  $RUN_SCRIPT  $APP_DIR/
sudo -i  mv  $APP_JAR    $APP_DIR/
sudo -i  chown -R hadoop:hadoop  $APP_DIR


echo "Beginning configuration the mapReduce task"
HADOOP_BIN_HOME=/usr/local/hadoop-2.8.1/bin/hadoop

JAR_PATH=$APP_DIR/StaticDaylive.jar
INPUT_PATH=/linkpage/201709/11/
#INPUT_PATH=/testpage
MAIN_CLASS=com.linkme.statistics.DayliveStati


OUTPUT_PATH=/daylive091100biz05/
#OUTPUT_PATH=/testoutpage11
OUTPUT_FILE=$OUTPUT_PATH/
FAILD_JOBID=job_1506514332722_0033

sleep 2
echo "run mapreduce program  please view the execute result from console and hdfs output"

#sudo su hadoop  $HADOOP_BIN_HOME fs  -rm -r  $OUTPUT_PATH
sudo su hadooop  $HADOOP_BIN_HOME job -kill $FAILD_JOBID
sudo su hadoop $HADOOP_BIN_HOME jar $JAR_PATH $MAIN_CLASS $INPUT_PATH $OUTPUT_PATH
#sudo su hadoop  $HADOOP_BIN_HOME jar /hadoopJars/sbtAwsHadoop/SbtAwsHadoop.jar  com.linkme.hadoop.ReadSnappyLog  $INPUT_PATH  $OUTPUT_PATH
echo "view the hdfs output result text"