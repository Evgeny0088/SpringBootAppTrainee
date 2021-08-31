#!/usr/bin/env bash

mvn clean package

echo 'Copy files'

scp -i ~/.ssh/spring-security.pem \
      target/SpringBootApp-0.0.1-SNAPSHOT.jar ec2-user@ec2-35-178-90-227.eu-west-2.compute.amazonaws.com:/home/ec2-user/

echo 'Restart server'

ssh -i ~/.ssh/spring-security.pem ec2-user@ec2-35-178-90-227.eu-west-2.compute.amazonaws.com:/home/ec2-user/ << EOF

pgrep java | xargs kill -9
nohup java -jar SpringBootApp-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'