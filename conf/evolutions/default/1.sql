# Users schema
 
# --- !Ups

CREATE SEQUENCE my_user_id_seq;
CREATE TABLE user (
    id integer NOT NULL DEFAULT nextval('my_user_id_seq'),
    username varchar(255),
    deleted boolean
);
 
# --- !Downs
 
DROP TABLE user;
DROP SEQUENCE user_id_seq;