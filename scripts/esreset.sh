#!/bin/bash


curl -XDELETE 'http://localhost:9200/olid'

curl -X PUT "localhost:9200/olid" -d '{
  "settings" : {}
}'
