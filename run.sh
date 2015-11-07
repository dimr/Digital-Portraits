#!/bin/bash

export CLASSPATH=./src/:./libs/*:./out/production/DigitalPortraits/
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/home/dimitris/workspace/java/random/DigitalPortraits/linux64:/home/dimitris/workspace/java/random/DigitalPortraits/libs/cascade-files

#if [ "$(ls -A out/production/Di)" ];then 
#rm bin/*; 
#echo "Deleting .class files"
#fi

javac -d out/production/DigitalPortraits/ src/CVMain.java ; optirun java CVMain
