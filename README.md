Open Local Information Directory
================================

This is the readme for the _The Open Local Information Directory_ (OLID) project. OLID is a platform for any organisation [But initially targetted at public libraries and local authorities in the UK] wishing to collate, manage and share directories of civic information and then publish/share/search that information in a spatially enabled and semantically meaningful way. Examples of such collections might include Citizens Information Datatabases managed by local library authorities, Childcare provision registers maintained by Family Service Units, Social Care Registers and Databases of Clubs and Events maintained by civic marketing departments.

Our goal is to provide a best-of-breed information delivery portal which is free to use in local government and the third sector. The platform combines administrative functions with local search portal and APIs as well as publishing data streams to third party applications. Support for inclusion in local and hyperlocal systems is of high importance as well as the "Big Player" search engines and local portals. 

The platform itself is Open Source, and freely downloadable and usable. It is maintained by the Sheffield Open Data group (An activity under the Better With Data Society banner). Sheffield Open Data consists of a number of industry professionals and is supported and attended by representatives from local universities (Sheffield Hallam, and UoS) and the local authority (Sheffield City Council) as well as the wider community in and around south yorkshire. The system is not intended to be geographically constrained however, and should be portable globally.

The driving principle behind OLID is that information collated and maintained by public bodies should be freely available to the public whos taxes pay for that service to reuse and innovate with openly.

The system itself is split into two major components - 

1) An administrative application which suports federated authentication of multiple authorities and/or departments. The system could be used to create a national database, and delegate the management of regional collections to local staff. Within these authorities, seperate hierarchical structures can be set up. The primary goal is to create a network-effect of public information maintainers whereby the workload of keeping information up to date is shared fairly amongst information owners and maintainers. Loaded records are cross-referenced with controlled vocabularies like the UK IPSV/GCL

The administrative system exposes an open data interface which publishes the maintained records as linked open data.

An agent consumes the open data feed and uses this data to populate (The system "eats it's own dogfood"):

2) An end user information portal which allows the public a faceted search the collections using keyword and spatal search.

APIs are provided for bulk data loading and setup of controlled vocabularies. The system is distributed with a tested  copy of the IPSV and uploaded records are automatically cross-referenced with IPSV where they control subject headings and/or categories.

--

It's a bit like "Ask a library" but for maintaining directories of civic and citizens information.

Future Developments

* More granular control of information publishing

* Development of a national federated identity service which can underpin an evolving culture of information stewardship in public services.

# Some screenshots - Front end search

![OLID - Search Front Page](https://raw.github.com/ianibo/tli/dev/images/search/olid-search-front.png)

![OLID - Postcode Search - Text Results](https://raw.github.com/ianibo/tli/dev/images/search/olid-postcode-search-text-results.png)

![OLID - Search - Map Results](https://raw.github.com/ianibo/tli/dev/images/search/olid-postcode-search-map-results.png)

![OLID - Postcode Search - Details](https://raw.github.com/ianibo/tli/dev/images/search/olid-search-details.png)

![OLID - Show All on Map](https://raw.github.com/ianibo/tli/dev/images/search/olid-map-show-all.png)


# Some screenshots - Back office system

## Welcome page - Administrative system

![OLID - Welcome Page](https://raw.github.com/ianibo/tli/dev/images/admin/olid-welcome.png)

## Register

In the default configuration any user can register, but will be unable to edit any records until an organisational affiliation is approved. New users
can, however, suggest creation of an authority (And subsequently a collection)

![OLID - Welcome Page](https://raw.github.com/ianibo/tli/dev/images/admin/olid-register.png)

## Login Page

![OLID - Login](https://raw.github.com/ianibo/tli/dev/images/admin/olid-login.png)

## User home page

The users home page lists all the organisations the user is affiliated with. A user can be affiliated with multiple organisations. The page also shows
all collections the user has access to via their organisational affiliation. This information is replicated on the menu. The page also allows the user to
request affiliation to a known organisation, or propose a new organisation. In both cases an administrator can approve the request to accept the proposal.
In the future, local administrative accounts will be able to approve new user requests for afilliation. This is to ease the administrative burden on
central admins. Users can navigate to a collection home page by clicking the link.

![OLID - User Home Page](https://raw.github.com/ianibo/tli/dev/images/admin/olid-user-home.png)

## The collection page

Allows a user to search a particular collection - to maintain existing records - Or to create a new record via the link top right.

![OLID - Search a collection](https://raw.github.com/ianibo/tli/dev/images/admin/olid-coll-search.png)

## Record Details

Displays the headline information about an entry - usually a description of the club/service/organisation. The system makes a distinction between orgaisations
which have a description and administrative details and sessions or activity. When searching spatially users don't usually want to find the head office of an
organisation, but the point of delivery for a service. Individual services and/or activities are presented on the next page..

![OLID - Record Details](https://raw.github.com/ianibo/tli/dev/images/admin/olid-details.png)

## Session details and adding a session.

A record in the database can have multiple sessions. Sessions fix an activity in time and space. This screen lists current sessions and allows the user to
add new ones. The recurrence section allows the users to specify times in a human readable way, for example "Every Monday" or "The Second Wednesday of the month"
If the system is able to understand the string, a parsed version is shown to the right. This is critical for modern information discovery systems, and can also be used to feed into local events databases with calendar entries. The end user search system will mark up this information using schema.org microformats, as understood by google and other search engines. This gives your information the best possible chance of being discovered in both your portal, and via internet search engines.

![OLID - Add Sessions to a Record](https://raw.github.com/ianibo/tli/dev/images/admin/olid-sessions.png)
![OLID - Add a session](https://raw.github.com/ianibo/tli/dev/images/admin/olid-add-session.png)

## Side by side view

If you have a bigger screen, side by side view can be easier to work with.

![OLID - Add a session](https://raw.github.com/ianibo/tli/dev/images/admin/olid-side-by-side.png)


There are a few videos demonstrating different aspects of this system here (Part 1 - Search interface)
http://youtu.be/ILzhW6eMBaE  and here (Part 2 - Administrative Interface): http://youtu.be/yXZ5VQogQOY.

    Copyright (C) 2013  Ian Ibbotson, Open Data Sheffield, Some Parts Knowledge Integration Ltd.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
