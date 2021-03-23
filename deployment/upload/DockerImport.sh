#!/bin/sh

echo "Are there any existing images in the following list related to the imports? (y/n)"
docker images
read DECISION_1
if [ $DECISION_1 = "y" ]
then
	echo "Existing running containers in the network will be stopped..."
	cd /home/$USER/deployment
	echo "Please type the name of the corresponding image:"
	read EXISTING_IMAGE
	echo "The image with the name $EXISTING_IMAGE will be deleted!"
	docker image rm "$EXISTING_IMAGE"
fi

echo "\nDo you have imported a new deployment folder? (y/n)"
read DECISION_2
if [ $DECISION_2 = "y" ]
then
	cd /home/$USER
	rm /home/$USER/deployment -r
	mkdir deployment
	tar -xvf /home/$USER/deployment.tar -C /home/$USER/deployment
	rm /home/$USER/deployment.tar
fi

# To import a tar file into Docker Engine the following steps will be done

echo "Which Docker-TAR-directory do you want to import?"
ls -a /home/$USER
read DOCKER_TAR_GZ
DOCKER_TAR_GZ="/home/$USER/$DOCKER_TAR_GZ"

echo "\n\nThe Docker-TAR-directory $DOCKER_TAR_GZ will be unziped first..."
gunzip $DOCKER_TAR_GZ 

DOCKER_TAR=$(ls -a | grep -i *.tar)
echo "\n\nThe unziped Docker-TAR-directory $DOCKER_TAR is now ready for import..."
DOCKER_TAR="/home/$USER/$DOCKER_TAR"


echo "\n\nNow we could import the included docker image..."
docker load <  $DOCKER_TAR

echo "\n\nDocker Image successfully imported!"

# Now we want to start our container and run them with docker-compose

echo "Have you already set up the network and the other containers? (y/n)"
read DECISION_3
if [ $DECISION_3 = "n" ]
then
	# echo "Postgres Container will be pulled and configured..."
	# docker run --name pgres -p 127.0.0.1:5432:5432 -e POSTGRES_PASSWORD=p -d postgres
	# docker exec -it pgres psql -U postgres -c "CREATE DATABASE heicheck;"

	echo "Now the network will be set up..."
	docker network create -d bridge heicheck
	docker network connect heicheck pgres
	
	echo "\n\nInspecting the created network:\n"
	docker network inspect heicheck

	echo "\n\nNow we are starting the containers..."
	cd /home/$USER/deployment
	docker-compose up -d

	# docker exec -it pgres psql -U postgres -c "CREATE DATABASE heicheck;"
else
	echo "Do you want to start the container in the network? (y/n)"
	read DECISION_4
	if [ $DECISION_4 = "y" ]
	then
		cd /home/$USER/deployment
	        docker-compose up -d
	else
		echo "Only containers were imported!"
	fi
fi



