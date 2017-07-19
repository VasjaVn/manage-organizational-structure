# manage-organizational-structure

STEP BY STEP INSTRUCTIONS HOW TO RUN APP ON LOCAL MACHINE.

1. Install JRE (build 1.8.0_121-b13).

2. Install Data Base:
   PostgreSQL-9.6.2-1a.

3. Execute database script:
   psql -U postgres -f {path to [db_mng_org_struct.sql]}.

4. Install Web Server:
   apache-tom-cat-9.0.0.M17.

5. Deploy Web Application on Web Server TomCat:
   copy war-file [mng-org-struct.war] to folder [appache-tom-cat-9.0.0.M17/webapps].

6. Run Web Server TomCat:
   run file [startup.bat] for Windows OS or file [startup.sh] for Mac/Linux OS.

7. Run Web Browser.

8. Enter into string of request of Web Browser this url:
   http://localhost:8080/mng-org-struct/