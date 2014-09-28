SELECT english_russian.id, uid, wid, word, translation FROM
       english_russian JOIN english ON (wid = english.id)
       WHERE uid = :uid;
