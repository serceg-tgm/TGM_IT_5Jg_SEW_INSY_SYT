Source: http://www.postgresql.org/docs/9.4/static/postgres-fdw.html

==============================================
=== Change Settings on Local Host ============
==============================================

CREATE EXTENSION postgres_fdw;
==============================================

CREATE SERVER foreign_server                                                                                              FOREIGN DATA WRAPPER postgres_fdw                                                                                       OPTIONS (host '10.5.105.87', port '5432', dbname 'ds2');

==============================================

CREATE USER MAPPING FOR ds2                                                                                               SERVER foreign_server                                                                                                   OPTIONS (user 'ds2', password 'ds2');

==============================================

CREATE FOREIGN TABLE horizontal.remote_orders_q12 (
  ORDERID SERIAL, 
  ORDERDATE DATE NOT NULL, 
  CUSTOMERID INT, 
  NETAMOUNT NUMERIC NOT NULL, 
  TAX NUMERIC NOT NULL, 
  TOTALAMOUNT NUMERIC NOT NULL
)
SERVER foreign_server
OPTIONS (schema_name 'horizontal', table_name 'orders_q12');


==============================================
=== Change Settings on Remote Host ===========
==============================================

postgresql.conf
/etc/postgresql/9.4/main

	#------------------------------------------------------------------------------
	# CONNECTIONS AND AUTHENTICATION
	#------------------------------------------------------------------------------
	listen_addresses = '*'
	#listen_addresses = 'localhost'

==============================================

pg_hba.conf
/etc/postgresql/9.4/main

# TYPE  DATABASE        USER            ADDRESS                 
local   all             all                                     peer
host    all             all             127.0.0.1/32            md5
host    all             all             ::1/128                 md5
host    ds2        		 ds2        			 samenet             		md5

==============================================

Note! After you have changed settings you have to restart the postgresql server.

==============================================

