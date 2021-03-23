#!/bin/sh

echo "Which image would you like to zip?"

read IMAGE

docker save -o "/home/$USER/Desktop/heicheck-backend.tar" "$IMAGE"

gzip "/home/$USER/Desktop/heicheck-backend.tar"

TAR_FILE="/home/$USER/Desktop/heicheck-backend.tar.gz"

echo "\nDoes a deployment folder just exists on the sandbox or would you add an updated one? (y/n)"
read DECISION
if [ $DECISION = "y" ]
then
	echo "\nNow you have to tell me where your local deployment directory is located:"
	echo "(Please insert the path like 'Desktop/.../heicheck-backend' !)"
	read DEV_DIRECTORY

	DEV_FOLDER="/home/$USER/$DEV_DIRECTORY/deployment"
	
	echo "\n\nThe development folder will now be compressed..."
	tar -cvf /home/$USER/Desktop/deployment.tar -C $DEV_FOLDER .

	DEV_FOLDER_TAR="/home/$USER/Desktop/deployment.tar"

	echo "\nFolder $DEV_FOLDER_TAR will be added to the file transfer..."

	echo "\nInitiate file upload: \n$TAR_FILE \n$DEV_FOLDER_TAR \n"

	echo "For connection to the sandbox use this password = mbZ2pABd7ASuKuebpCax"

	# New command for DEV_FOLDER and TAR_FILE
	scp -P 58025 $TAR_FILE $DEV_FOLDER_TAR  extuser@129.206.229.119:~/
else
	echo "Initiate file upload: $TAR_FILE \n"

	scp -P 58025 $TAR_FILE  extuser@129.206.229.119:~/
fi

echo "\nFile upload finished!"
rm -r $DEV_FOLDER_TAR $TAR_FILE
