# Oracle DB


START DB

==== my db
* `sqlplus / as sysdba`
* `STARTUP`
* `alter session set container = orclpdb2;`
* `alter pluggable database open;`
* `alter user hr identified by hr account unlock;`

==== official version
* `sqlplus / as sysdba`
* `STARTUP`
* `select count(*) from hr.employees;`
* `EXIT`