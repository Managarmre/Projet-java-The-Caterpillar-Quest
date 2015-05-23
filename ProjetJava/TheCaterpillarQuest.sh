#!/bin/bash
# auteur : Maxime Pineau

# vérification de l'installation Java.

# on vérifie si Java est dans PATH
if ! type -p java > /dev/null; then
    
    # on vérifie si java est dans JAVA_HOME
	if ! [[ -n "$JAVA_HOME" ]] || ! [[ -x "$JAVA_HOME/bin/java" ]];  then
		echo "Java n'est pas installé sur votre machine."
		exit -1
	fi

fi


java -jar ./TheCaterpillarQuest.jar $1 $2 
