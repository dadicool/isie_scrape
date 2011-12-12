#!/bin/bash

# This script loads the patched json files into a local CouchDB

for f in raw-*.json
do
CONTENT=`cat $f`
curl -X POST "http://127.0.0.1:5984/isie_election_final/" -H 'Content-Type: application/json' -d "$CONTENT"
done
