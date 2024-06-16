#!/bin/bash

mkdir out 2> /dev/null
rm -r out/** 2> /dev/null

javac -d out `find ./src -type f`
