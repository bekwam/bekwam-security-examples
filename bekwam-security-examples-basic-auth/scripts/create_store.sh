#!/bin/sh

##
## shell commands that generate files for the Basic Auth Demo
##

# cd server.config
cd "/media/carl/Extra Drive 1/opt/wildfly/wildfly-23.0.2.Final/standalone/configuration"

# create roles file
echo -n 'cwalker=user' > basic-auth-roles.properties

# create users file
echo '#$REALM_NAME=BasicAuthRealm$ This line is used by the add-user utility to identify the realm name already used in this file.' > basic-auth-users.properties
echo -n 'cwalker=' >> basic-auth-users.properties
echo -n 'cwalker:BasicAuthRealm:somepassword' | openssl dgst -md5 -binary | xxd -p >> basic-auth-users.properties
