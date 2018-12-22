#!/bin/bash
#
#  Auto build shell script for iboxpay-mars-example-parent
#
if [ $# -lt 1 ]; then
  echo "Usage: $0 [p|pre|t|d]";
  exit;
fi

# get PRGDIR
PRG="$0"
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done
PRGDIR=$(cd $(dirname $PRG); pwd)

case $1 in
  p | production)
    profile="production"
    ;;
  pre | pre_production)
    profile="staging"
    ;;
  t | test)
    profile="test"
    ;;
  d | develop)
    profile="develop"
    ;;
  *)
  echo "Error! unknown parameter."
    exit 1
    ;;
esac


# 此处需要设置或修改MAVEN_OPTS，否则在执行mvn install命令时可能会出现OutOfMemoryError错误
export MAVEN_OPTS="-Xmx512m"

mvn clean install -Dmaven.test.skip=true -Dmaven.compile.fork=true -U -P${profile}
