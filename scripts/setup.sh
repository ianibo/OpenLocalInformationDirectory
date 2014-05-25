#!/bin/bash

set basedir=`pwd`

echo Before running this script, do a clean database install - Update password for your local install
echo Command: mysql -u olid -polid olid -e "\. ../dbdumps/clean_olid.sql"

echo reset ES
./dbreset.sh

echo Upload IPSV
curl -X POST -u admin:admin --data "vocabCode=IPSV&vocabBaseUrl=https://raw.githubusercontent.com&vocabPath=/ianibo/OpenLocalInformationDirectory/release/vocs/ipsv.xml" "http://localhost:8080/olid/api/loadVocabulary"

echo Upload test orgs
curl -i -X POST -u admin:admin -F orgscsv=@../testdata/orgs.csv "http://localhost:8080/olid/api/loadOrgs"

echo Upload MLA orgs
curl -i -X POST -u admin:admin -F orgscsv=@../testdata/mla_orgs.csv "http://localhost:8080/olid/api/loadOrgs"
