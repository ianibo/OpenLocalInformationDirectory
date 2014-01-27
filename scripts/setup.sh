#!/bin/bash

curl -X POST -u admin:admin --data "vocabCode=IPSV&vocabBaseUrl=http://localhost&vocabPath=/ipsv.xml" "http://localhost:8080/admin/api/loadVocabulary"
