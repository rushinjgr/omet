#!/bin/bash

javac -verbose -d classes -classpath $CLASSPATH:/afs/pitt.edu/public/dept/cs/oracle/product/11.1.0.6/ojdbc6.jar ometcommon/*.java
cd classes
jar cf ../omet-common.jar ometcommon/*.class
cd ..
jar i omet-common.jar

