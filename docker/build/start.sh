#!/bin/bash   
echo Executing Muss   
rabbitmq-server &
java -jar /etc/muss.jar