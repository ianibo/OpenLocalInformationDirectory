#!/bin/bash


#clear down mongo
# mongo <<!!!
# use dname
# db.dropDatabase();
# db.coll({"proop": 1});
# !!!
# 
# Clear down ES indexes
curl -XDELETE 'http://localhost:9200/olid'

curl -X PUT "localhost:9200/olid" -d '{
  "settings" : {}
}'

curl -X PUT "localhost:9200/olid/entry/_mapping" -d '{
  "entry" : {
    "properties" : {
      "title" : {
        type : "string",
        analyzer : "snowball"
      }
      "description" : {
        type : "string",
        analyzer : "snowball"
      }
      "sessions": {
        "loc": {
          "type" 'geo_point'
        }
      }
    }
  }
}'
