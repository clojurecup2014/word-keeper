SELECT * FROM vocabularies JOIN languages ON orig_lang_id = languages.id WHERE uid = :uid AND lang = :lang;
