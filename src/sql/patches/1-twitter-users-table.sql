CREATE TABLE twitter_users (
       twitter_id integer PRIMARY KEY,
       twitter_name text NOT NULL,
       uid integer REFERENCES users(id)
)
