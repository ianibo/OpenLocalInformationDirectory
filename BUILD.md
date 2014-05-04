

To build

1. Checkout, build and install the shared auth model plugin from https://github.com/ianibo/CommonAuthModelPlugin

git clone git@github.com:ianibo/CommonAuthModelPlugin.git

cd CommonAuthModelPlugin/authmodel
grails package-plugin
grails maven-install

2. Checkout this project from https://github.com/ianibo/OpenLocalInformationDirectory

git clone git@github.com:ianibo/OpenLocalInformationDirectory.git

2.1. Build the shared data model

cd OpenLocalInformationDirectory/oliddm
grails package-plugin
grails maven-install

2.2. Build the admin system was

cd OpenLocalInformationDirectory/admin
grails prod war


2.3. Build the front end search app

cd OpenLocalInformationDirectory/search
grails prod war



