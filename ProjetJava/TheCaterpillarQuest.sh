#!/bin/bash
#@author Maxime Pineau


if ! [[type -p java] || [[ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]]]
then 
	echo "Vous n'avez pas Java d'installé sur votre machine."
	exit -1
fi

java -jar ./TheCaterpillarQuest.jar $1 $2 