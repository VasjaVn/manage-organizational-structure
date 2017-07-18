-- --------------------------- --
-- Database: db_mng_org_struct --
-- --------------------------- --
-- DROP DATABASE db_mng_org_struct;
CREATE DATABASE db_mng_org_struct
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Russian_Russia.1251'
       LC_CTYPE = 'Russian_Russia.1251'
       CONNECTION LIMIT = -1;


-- ------------------------------------- --
-- CONNECT TO DATABASE db_mng_org_struct --
-- ------------------------------------- --
\connect db_mng_org_struct;


-- ------------------------- --
-- Table: public.tbl_company --
-- ------------------------- --
-- DROP TABLE public.tbl_company;
CREATE TABLE public.tbl_company
(
  id character varying(36) NOT NULL,
  parent_id character varying(36),
  name character varying(50),
  earnings bigint,
  estimated_earnings bigint,
  CONSTRAINT tbl_company_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

ALTER TABLE public.tbl_company
  OWNER TO postgres;


-- ------------------------------ --
-- INSERT INTO public.tbl_company --
-- ------------------------------ --
INSERT INTO public.tbl_company(id, earnings, estimated_earnings, name, parent_id)
    VALUES ('bd746966-707f-44bc-b02b-f5bb2e6f21ab', 15000, 0, 'Company6', '9a6806c1-5b5e-4955-b233-a7abbd7f21e8');

INSERT INTO public.tbl_company(id, earnings, estimated_earnings, name, parent_id)
    VALUES ('9a6806c1-5b5e-4955-b233-a7abbd7f21e8', 10000, 30000, 'Company5', '#');

INSERT INTO public.tbl_company(id, earnings, estimated_earnings, name, parent_id)
    VALUES ('fcc4feb2-a442-41e4-8bce-b807035a78fc', 5000, 0, 'Company7', '9a6806c1-5b5e-4955-b233-a7abbd7f21e8');

INSERT INTO public.tbl_company(id, earnings, estimated_earnings, name, parent_id)
    VALUES ('c5047950-ae64-4e71-bb34-31e0fa62e1da', 13000, 18000, 'Company2', '3980e4e8-0212-41fd-9deb-33ddfd5b066e');

INSERT INTO public.tbl_company(id, earnings, estimated_earnings, name, parent_id)
    VALUES ('81186ccc-092b-4a34-9c0d-212c6400cb5a', 5000, 0, 'Company3', 'c5047950-ae64-4e71-bb34-31e0fa62e1da');

INSERT INTO public.tbl_company(id, earnings, estimated_earnings, name, parent_id)
    VALUES ('3980e4e8-0212-41fd-9deb-33ddfd5b066e', 25000, 53000, 'Company1', '#');

INSERT INTO public.tbl_company(id, earnings, estimated_earnings, name, parent_id)
    VALUES ('3ce56362-f3c5-448a-b37b-7928e99205b9', 10000, 0, 'Company4', '3980e4e8-0212-41fd-9deb-33ddfd5b066e');
