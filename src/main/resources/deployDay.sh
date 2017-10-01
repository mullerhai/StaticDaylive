#!/bin/sh

echo "deploy  staticdaylive jar"

AWS_PEM='/Users/linkedmemuller/Documents/AWS_Consel/hadoopCluster.pem'
AWS_SERVER=ec2-user@ec2-52-80-67-242.cn-north-1.compute.amazonaws.com.cn
LOCAL_JAR=./out/artifacts/StaticDaylive_jar/StaticDaylive.jar
RUN_SCRIPT=./src/main/resources/runday.sh

sleep 1
echo "wait for some minutes to upload file to aws "
#scp -i $AWS_PEM $LOCAL_JAR $RUN_SCRIPT  $AWS_SERVER:/hadoopJars/
scp -i $AWS_PEM  $RUN_SCRIPT  $AWS_SERVER:/hadoopJars/
#sleep 2
echo "grant right for the  hadoop script "
ssh -i $AWS_PEM $AWS_SERVER  'sudo -i chmod 777 /hadoopJars/runday.sh'

sleep 2
echo "execute hadoop map reduce script"
ssh -i $AWS_PEM $AWS_SERVER 'sudo -i /hadoopJars/runday.sh'
