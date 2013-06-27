# my_users schema
 
# --- !Ups

CREATE SEQUENCE my_user_id_seq;
CREATE TABLE my_user (
    id integer NOT NULL DEFAULT nextval('my_user_id_seq'),
    username varchar(255),
    deleted boolean
);
 
# --- !Downs
 
DROP TABLE my_user;
DROP SEQUENCE my_user_id_seq;