WITH v AS (SELECT :english_word :: text AS word)
    ,s AS (SELECT id FROM English JOIN v USING (word))
    ,i AS (
       INSERT INTO English (word)
       SELECT word
       FROM   v
       WHERE  NOT EXISTS (SELECT * FROM s)
       RETURNING id)
SELECT id FROM i
UNION  ALL
SELECT id FROM s;
