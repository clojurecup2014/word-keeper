CREATE TABLE languages (
       id serial PRIMARY KEY,
       lang text NOT NULL
);

INSERT INTO TABLE languages (lang) VALUES ('english');
INSERT INTO TABLE languages (lang) VALUES ('russian');

CREATE TABLE words(
       id serial PRIMARY KEY,
       lang_id integer REFERENCES languages(id),
       word text NOT NULL
);

CREATE TABLE vocabularies (
       id serial PRIMARY KEY,
       uid integer REFERENCES users(id) NOT NULL,
       orig_lang_id integer REFERENCES languages(id) NOT NULL,
       translation_lang_id integer REFERENCES languages(id) NOT NULL,
       word_id integer REFERENCES words(id) NOT NULL,
       translation text
);

DROP TABLE russian;
DROP TABLE english;
DROP TABLE english_russian;
DROP TABLE russian_english;
