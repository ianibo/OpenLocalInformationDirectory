#!/bin/bash

exec < ../vocs/libs.txt
while IFS=$'\t' read -r -a myArray
do
  org_url=${myArray[0]}
  org_name=${myArray[1]}
  opac_url=${myArray[2]}

  shortcode=`echo $org_name|tr ' [:upper:]' '_[:lower:]'`
  echo "insert into tli_party(version,display_name,class,shortcode,status_id,url) values (0,'$org_name','tli.TliOrg','$shortcode',4,'$org_url');"
done
