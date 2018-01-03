CREATE DATABASE scalaping
  WITH OWNER = ***
  ENCODING = 'UTF8'
  TABLESPACE = pg_default
  CONNECTION LIMIT = -1;


CREATE TABLE "scrape_top" (
"id" SERIAL PRIMARY KEY,
"url" varchar(255) NOT NULL,
"title" varchar(255),
"description" varchar(255),
"create_date" timestamp NOT NULL
);

CREATE TABLE "top_link" (
"id" SERIAL PRIMARY KEY,
"top_id" int NOT NULL,
"url" text NOT NULL,
"text" text NOT NULL,
"add_date" timestamp
);

