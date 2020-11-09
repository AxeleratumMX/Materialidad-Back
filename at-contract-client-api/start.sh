#!/bin/bash
#

nohup java -jar -Xms256m -Xmx512m -Xss256k -DSPRING_PROFILE=test target/americantower-contract-client-api-0.0.1-SNAPSHOT.jar > client-api.log &

