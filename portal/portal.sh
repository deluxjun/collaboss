#!/bin/sh
#

JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.252.b09-2.el7_8.x86_64/bin
APP_NAME=sbp_portal

JVM_OPT="-Du=$APP_NAME -d64 -server -Xms1024m -Xmx2048m"

case "$1" in
    'start')  # 
	echo "Starting Service...: "

	PROC=$(ps -ef | grep Du=$APP_NAME | grep -v grep | wc -l)
 	if [ "$PROC" -ge 1 ]; then
 		echo "=> LNTR already running "
 		exit 1
 	fi
	
	nohup $JAVA_HOME/java $JVM_OPT -jar portal-0.0.1-SNAPSHOT.war > system.out 2>&1 &
	
	RVAL=$?
	echo $RVAL
	exit $RVAL
	    ;;
		
		
    'stop')   # Stop 
	echo "Stopping Service...: "
        for proc_id in `ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}'`
        do
        echo "PID :" $proc_id
                echo "Killing..."
                /usr/bin/kill -9 $proc_id
        done
	exit $RVAL
	;;

    *)
	echo Usage: `basename $0` "{start|stop}"
	exit 1
	;;
esac
