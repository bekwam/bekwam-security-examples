#!/bin/sh

##
## handy command for creating Authorization: Basic header
##

echo -n 'cwalker:somepassword' | openssl base64
