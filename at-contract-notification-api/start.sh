#!/bin/bash
#

nohup java -jar -Xms256m -Xmx512m -Xss256k -DSPRING_PROFILE=test target/americantower-contract-notification-api-0.0.1-SNAPSHOT.jar > notifications-api.log &

