#!/bin/bash
#

nohup java -jar -Xms256m -Xmx512m -Xss256k -DSPRING_PROFILE=test target/americantower-contract-origin-api-0.0.1-SNAPSHOT.jar > contracts-api.log &

