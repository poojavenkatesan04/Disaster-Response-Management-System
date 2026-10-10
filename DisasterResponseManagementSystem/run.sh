#!/bin/bash
rm -rf out; mkdir -p out
find src/main/java -name "*.java" > sources.txt
javac -d out @sources.txt || exit 1
rm sources.txt
java -cp out com.disaster.Main
