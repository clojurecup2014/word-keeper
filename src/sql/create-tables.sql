CREATE TABLE users (
       id serial PRIMARY KEY
);

CREATE TABLE english (
       id serial PRIMARY KEY,
       word text UNIQUE NOT NULL
);

CREATE TABLE russian (
       id serial PRIMARY KEY,
       word text UNIQUE NOT NULL
);

CREATE TABLE english_russian (
       id serial PRIMARY KEY,
       uid integer references users(id),
       wid integer references english(id),
       translation text
);

CREATE TABLE russian_english (
       id serial PRIMARY KEY,
       uid integer references users(id),
       wid integer references russian(id),
       translation text
);
