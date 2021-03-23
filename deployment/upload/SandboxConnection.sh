#!/bin/sh

echo "Use this passwort to connect via ssh to the sandbox:"

echo "\n\nNow you'll be prompted to paste your password:\n"

# To connect to our sandbox environment in Heidelberg, you have to use ssh
ssh extuser@129.206.229.126 -p 58096
