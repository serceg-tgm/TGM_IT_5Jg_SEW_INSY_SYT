#!/bin/bash

echo "### START default.sh ###"

# Updating dependencies and installing mongodb
apt-key adv --keyserver keyserver.ubuntu.com --recv 7F0CEB10
echo "deb http://repo.mongodb.org/apt/debian wheezy/mongodb-org/3.0 main" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.0.list
apt-get update
apt-get install -y mongodb-org

# configure binding of mongodb from every IP-address (0.0.0.0)
sed -i "s/\b\([0-9]\{1,3\}\.\)\{1,3\}[0-9]\{1,3\}\b/0.0.0.0/g" /etc/mongod.conf
service mongod stop
service mongod start

# Installing 7zip
apt-get install -y p7zip p7zip-full

# Unpacking the generated testdata
7z e /home/vagrant/db-dump/data.7z -o/home/vagrant/db-dump/ -aoa

# import the testdata into the database
mongo PersonWiki --eval "db.person.drop()"
mongoimport --db PersonWiki --collection personRecord --type json --file /home/vagrant/db-dump/persons.json --jsonArray

echo "### END default.sh ###"
