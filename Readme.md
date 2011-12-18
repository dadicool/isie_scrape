# This repo has moved to [OpenGovTN isie_tnac_collection](https://github.com/OpenGovTN/isie_tnac_collection)

Intro
-----

This is a set of scripts and tools to extract the detailed results of Tunisian Constitutional Assembly from the ISIE website
There are two versions: Python (deprecated) and Java

The Database choice to host the data and make it available through a REST API is CouchDB

Java
----
- Start Eclipse 
- Import Existing Project from TnElectionData directory
- Run target (GetAllMetadata) as "Java Application" - You can specify the path to where the data is going to be dumped by adding the path as the sole argument
- Run target (GetRawData) as "Java Application" - You can specify the path to where the data is going to be dumped by adding the path as the sole argument
- Watch the .csv files pile up :)

Database Import
---------------
- Run the website scraper (TBD)
- Run patch.sh in the directory where the raw .json files are located - This script adds an "_id" field to every document
- Run upload.sh to import the .json files into your local CouchDB (for testing/debugging)
- To make the data available publicly, replicate your database onto a public Hosted CouchDB instance


Python (deprecated)
-------------------

Pre-requisites
--------------
- Python 2.6+

Steps:
------
Go into the python

Install BeautifulSoup
---------------------
Run inside the BeautifulSoup directory :

     python setup.py build

     python setup.py install

Run the script
--------------
python isie_scrape.py

Result 
------
- HTML corresponding to the election results for the first voting booth
- Election_results.csv (Future)

