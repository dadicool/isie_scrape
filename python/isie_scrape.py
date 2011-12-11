#!/usr/bin/python

from BeautifulSoup import BeautifulSoup
import re
import urllib2
from urllib import urlencode

urlResult='http://www.isie.tn/Ar/rsfinal.php'
urlDelegation='http://www.isie.tn/Ar/ws_delegation_ajax.php'
urlCentre='http://www.isie.tn/Ar/ws_centre_vote_ajax.php'
urlBureau='http://www.isie.tn/Ar/ws_bureau_vote_ajax.php'

def getCirc():
    params = {
    }
    encoded_params = urlencode(params)
    
    res = urllib2.urlopen(urlResult, encoded_params)
    soup = BeautifulSoup(''.join(res.read()))
    optList = soup.find("select",{ 'id' : 'circonscription'})
    optTags = optList.findAll('option')[1:]
    return map(lambda x: {x['value'] : x.contents[0]}, optTags)
    
def getDelegation(circonscription):
    params = {
        'circonscription' : circonscription
    }
    encoded_params = urlencode(params)
    
    res = urllib2.urlopen(urlDelegation, encoded_params)
    soup = BeautifulSoup(''.join(res.read()))
    optTags = soup.findAll('option')[1:]
    return map(lambda x: {x['value'] : x.contents[0]}, optTags)

def getCentre(circonscription, delegation):
    params = {
        'circonscription' : circonscription,
        'delegation':delegation,
    }
    encoded_params = urlencode(params)
    
    res = urllib2.urlopen(urlCentre, encoded_params)
    soup = BeautifulSoup(''.join(res.read()))
    optTags = soup.findAll('option')[1:]
    return map(lambda x: {x['value'] : x.contents[0]}, optTags)

def getBureau(circonscription, delegation, centre):
    params = {
        'circonscription' : circonscription,
        'delegation':delegation,
        'centre_vote':centre,
    }
    encoded_params = urlencode(params)
    
    res = urllib2.urlopen(urlBureau, encoded_params)
    soup = BeautifulSoup(''.join(res.read()))
    optTags = soup.findAll('option')[1:]
    return map(lambda x: {x['value'] : x.contents[0]}, optTags)

# create the request object and set some headers
def getResult(circonscription, delegation, centre, bureau):
    params = {
        'circonscription':circonscription,
        'delegation':delegation,
        'centre_vote':centre,
        'bureau_vote':bureau,
        'button':'+++%D8%A8%D8%AD%D8%AB++',
    }
    encoded_params = urlencode(params)
  #  print encoded_params
    
    res = urllib2.urlopen(urlResult, encoded_params)
    soup = BeautifulSoup(''.join(res.read()))
    return soup.findAll('table')[4:]

#print getCirc()
#print getDelegation('130')
#print getCentre('130', '61')
#print getBureau('130', '61', '091')
print getResult('111', '52', '001', '01')
