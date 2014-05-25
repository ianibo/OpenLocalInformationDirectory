#!/bin/bash

set basedir=`pwd`

echo Before running this script, do a clean database install - Update password for your local install
echo Command: mysql -u olid -polid olid -e "\. ../dbdumps/clean_olid.sql"

echo reset ES
./dbreset.sh

echo Upload IPSV
## done away with --cookie and --cookie-jar
curl -X POST -u admin:admin --data "vocabCode=IPSV&vocabBaseUrl=https://raw.githubusercontent.com&vocabPath=/ianibo/OpenLocalInformationDirectory/release/vocs/ipsv.xml" "http://localhost:8080/olid/api/loadVocabulary"

echo Upload test orgs
curl -i -X POST -u admin:admin -F orgscsv=@../testdata/orgs.csv "http://localhost:8080/olid/api/loadOrgs"

echo Upload MLA orgs
curl -i -X POST -u admin:admin -F orgscsv=@../testdata/mla_orgs.csv "http://localhost:8080/olid/api/loadOrgs"

echo Affiliate admin user with Collections Trust
curl -i -X POST -u admin:admin -F user="admin" -F org="UKCT" -F role="Administrator" "http://localhost:8080/olid/api/registerAffiliation"

echo Affiliate admin user with Grow Sheffield
curl -i -X POST -u admin:admin -F user="admin" -F org="GrowSheffield" -F role="Administrator" "http://localhost:8080/olid/api/registerAffiliation"

echo Affiliate admin user with Sheffield Central Library
curl -i -X POST -u admin:admin -F user="admin" -F org="sheffield_central_library" -F role="Administrator" "http://localhost:8080/olid/api/registerAffiliation"

echo Affiliate admin user with ODS1
curl -i -X POST -u admin:admin -F user="admin" -F org="ODS" -F role="Administrator" "http://localhost:8080/olid/api/registerAffiliation"

echo Create Collection for SFN
curl -i -X POST -u admin:admin -F org="GrowSheffield" -F name="SFN" -F description="SFN" "http://localhost:8080/olid/api/createCollection"

echo Create Collection for HYS
curl -i -X POST -u admin:admin -F org="sheffield_central_library" -F name="Sheffield Help Yourself" -F description="Sheffield Help Yourself" "http://localhost:8080/olid/api/createCollection"

echo Create Collection for MLA Institutions
curl -i -X POST -u admin:admin -F org="UKCT" -F name="mlainst" -F description="MLA Institutions Register" "http://localhost:8080/olid/api/createCollection"

