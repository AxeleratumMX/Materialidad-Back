#!/bin/bash
#

nohup java -jar -Xms256m -Xmx512m -Xss256k -DSPRING_PROFILE=test target/americantower-contract-swagger-0.0.1-SNAPSHOT.jar > documentation-api.log &

