# tags schema
 
# --- !Ups

CREATE TABLE tags (
    id serial NOT NULL,
    tagname character varying(255) NOT NULL,
    lat varchar(255),
    lngt varchar(255),
    deleted boolean,
    CONSTRAINT tags_pkey PRIMARY KEY (id)
);
 
CREATE INDEX index_tags_on_tagname
  ON tags
  USING btree
  (tagname COLLATE pg_catalog."default");

# --- !Downs
 
DROP TABLE tag;
DROP INDEX index_tags_on_tagname


