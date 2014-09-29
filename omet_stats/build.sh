javac -verbose -d classes -classpath $CLASSPATH:../omet-common/omet-common.jar:../omet_list/omet_list.jar ometstats/*.java
cd classes
echo "creating jar"
jar -cvfm ../omet_stats.jar ../Manifest.mf ometstats/*.class
echo "jar created"
cd ..
