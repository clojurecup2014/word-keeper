SELECT * FROM users JOIN twitter_users ON (users.id = uid) WHERE uid = :uid;
