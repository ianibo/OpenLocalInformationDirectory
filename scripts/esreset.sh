#!/bin/bash


curl -XDELETE 'http://localhost:9200/olid'

curl -X PUT "localhost:9200/olid" -d '{
  "settings" : {}
}'

# curl -X PUT "localhost:9200/kbplus/com.k_int.kbplus.TitleInstance/_mapping" -d '{
#   "com.k_int.kbplus.TitleInstance" : {
#     "properties" : {
#       "title" : {
#         type : "string",
#         analyzer : "snowball"
#       }
#     }
#   }
# }'

