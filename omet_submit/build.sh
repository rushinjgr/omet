javac -verbose -d classes -classpath $CLASSPATH:../omet-common/omet-common.jar ometsubmit/*.java
cd classes
echo "creating jar"
jar -cvfm ../omet_submit.jar ../Manifest.mf ometsubmit/*.class
echo "jar created"
cd ..
jar i omet_submit.jar
