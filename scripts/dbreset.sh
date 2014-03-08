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

curl -X PUT "localhost:9200/olid/tli.DirectoryEntry/_mapping" -d '{
  "tli.DirectoryEntry" : {
    "properties" : {
      "title" : {
        "type" : "string",
        "analyzer" : "snowball"
      },
      "description" : {
        "type" : "string",
        "analyzer" : "snowball"
      },
      "sessions": {
        "properties" : {
          "loc": {
            "type":"geo_point"
          }
        }
      },
      "subjects" : {
        "properties" : {
          "subjname" : {
            "type" : "string",
            "index" : "not_analyzed"
          }
        }
      },
      "categories" : {
        "properties" : {
          "catname" : {
            "type" : "string",
            "index" : "not_analyzed"
          }
        }
      },
      "collections" : {
        "properties" : {
          "collname" : {
            "type" : "string",
            "index" : "not_analyzed"
          }
        }
      } } } }'
