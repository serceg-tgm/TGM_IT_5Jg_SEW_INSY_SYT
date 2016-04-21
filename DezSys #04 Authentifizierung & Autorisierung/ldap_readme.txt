Installation LDAP:
------------------
sudo apt-get update
sudo apt-get install slapd ldap-utils

sudo dpkg-reconfigure slapd
> DNS domain name: nodomain.com
> Organization name: nodomain
> Administrator password: user
> Database backend: hdb

Installation phpLDAPadmin:
--------------------------
sudo apt-get install phpldapadmin

Configuration phpLDAPadmin:
---------------------------
sudo gedit /etc/phpldapadmin/config.php

$servers->setValue('server','host','localhost');
$servers->setValue('server','base',array('dc=nodomain,dc=com'));
$servers->setValue('login','bind_id','cn=admin,dc=nodomain,dc=com');
$config->custom->appearance['hide_template_warning'] = true;

SSL configuration not performed!

Configuration Apache:
---------------------
/etc/apache2/mods-enabled/alias.conf: following line added
Alias /ldap /usr/share/phpldapadmin/htdocs

Link to phpLDAPadmin:
---------------------
http://localhost/ldap

Modify LDAP Directory:
----------------------
Add new Posix Group: group.default
Add new Posix Group: group.service1
Add new Generic User Account: max.mustermann
Add max.mustermann to group.service1

LDAPSEARCH Commandline Tool / Local:
------------------------------------
ldapsearch -h 127.0.0.1 -p 389 -D "cn=admin,dc=nodomain,dc=com" -W
ldapsearch -h 127.0.0.1 -p 389 -D "cn=max.mustermann,dc=nodomain,dc=com" -W

LDAPSEARCH Commandline Tool / Remote:
-------------------------------------
ldapsearch -h 192.168.0.8 -p 389 -D "cn=admin,dc=nodomain,dc=com" -W
ldapsearch -h 192.168.0.8 -p 389 -D "cn=max.mustermann,dc=nodomain,dc=com" -W -b "dc=nodomain,dc=com"
ldapsearch -h 192.168.0.8 -p 389 -D "cn=max.mustermann,dc=nodomain,dc=com" -W -b "cn=group.service2,dc=nodomain,dc=com" memberUid
ldapsearch -h 192.168.0.8 -p 389 -D "cn=max.mustermann,dc=nodomain,dc=com" -W -b "dc=nodomain,dc=com" "cn=group.*" memberUid
ldapsearch -h 192.168.0.8 -p 389 -D "cn=max.mustermann,dc=nodomain,dc=com" -W -b "dc=nodomain,dc=com" "(objectclass=PosixGroup)"

LDAPMODIFY Commandline Tool / Remote:
-------------------------------------
ldapmodify -h 192.168.0.8 -p 389 -D "cn=admin,dc=nodomain,dc=com" -W  
dn: cn=group.service1,dc=nodomain,dc=com             
changetype: modify
replace: description
description: test


Links:
------
http://docs.oracle.com/javase/tutorial/jndi/index.html
http://www.stefan-seelmann.de/media/presentations/JUGM2008_JavaUndLDAP.pdf
https://www.digitalocean.com/community/tutorials/how-to-install-and-configure-openldap-and-phpldapadmin-on-an-ubuntu-14-04-server





