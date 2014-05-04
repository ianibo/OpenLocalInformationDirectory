#!/bin/bash

set basedir=`pwd`
curl -X POST -u admin:admin --data "vocabCode=IPSV&vocabBaseUrl=https://raw.githubusercontent.com&vocabPath=/ianibo/OpenLocalInformationDirectory/release/vocs/ipsv.xml" "http://localhost:8080/olid/api/loadVocabulary"
curl -i -X POST -u admin:admin -F orgscsv=@$basedir/testdata/orgs.csv "http://localhost:8080/olid/api/loadOrgs"
curl -i -X POST -u admin:admin -F orgscsv=@$basedir/testdata/mla_orgs.csv "http://localhost:8080/olid/api/loadOrgs"
