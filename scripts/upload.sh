#!/bin/bash

# This script loads the patched json files into a local CouchDB

if [ $# -ne  1 ];
then
  echo "Usage : upload.sh DATABASE_NAME"
fi

DB_NAME=$1
for f in raw-*.json
do
CONTENT=`cat $f`
curl -X POST "http://127.0.0.1:5984/$DB_NAME/" -H 'Content-Type: application/json' -d "$CONTENT"
done
