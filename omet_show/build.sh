javac -verbose -d classes -classpath $CLASSPATH:../omet-common/omet-common.jar ometshow/*.java
cd classes
echo "creating jar"
jar -cvfm ../omet_show.jar ../Manifest.mf ometshow/*.class
echo "jar created"
cd ..
jar i omet_show.jar
