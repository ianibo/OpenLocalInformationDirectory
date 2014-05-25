#!/bin/bash

mysqldump -u root -p --databases olid common olidLive > olid_bkp.sql
