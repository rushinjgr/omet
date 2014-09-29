javac -verbose -d classes -classpath $CLASSPATH:../omet-common/omet-common.jar ometlist/*.java
cd classes
echo "creating jar"
jar -cvfm ../omet_list.jar ../Manifest.mf ometlist/*.class
echo "jar created"
cd ..
jar i omet_list.jar
