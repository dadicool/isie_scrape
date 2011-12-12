#/bin/bash
# This script adds an "_id" field to the .json files dumped by the scraper

for f in raw-*.json
do
id=${f:4:13}
perl -p -i -e "s/^{/{\"_id\":\"$id\",/g" $f
done  
