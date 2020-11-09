#!/bin/bash
#

nohup java -jar -Xms256m -Xmx512m -Xss256k  target/americantower-eureka-server-0.0.1-SNAPSHOT.jar > eureka-server.log &

