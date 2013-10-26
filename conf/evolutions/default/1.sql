# my_users schema
 
# --- !Ups

CREATE TABLE my_user (
    id serial NOT NULL,
    username character varying(255) NOT NULL,
    lat varchar(255),
    lngt varchar(255),
    deleted boolean,
    CONSTRAINT my_user_pkey PRIMARY KEY (id)
);
 
CREATE INDEX index_user_on_username
  ON my_user
  USING btree
  (username COLLATE pg_catalog."default");


# --- !Downs
 
DROP TABLE my_user;
DROP INDEX my_user_id_seq;

