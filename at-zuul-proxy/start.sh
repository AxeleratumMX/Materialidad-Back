#!/bin/bash
#

nohup java -jar -Xms256m -Xmx512m -Xss256k  target/americantower-zuul-proxy-server-0.0.1-SNAPSHOT.jar > zuul-proxy.log &

