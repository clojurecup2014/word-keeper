SELECT vocabularies.*, languages.lang, words.word
FROM vocabularies, languages, words
WHERE vocabularies.orig_lang_id = languages.id 
  AND words.id = vocabularies.word_id
  AND vocabularies.uid = :uid
  AND languages.lang = :lang;
