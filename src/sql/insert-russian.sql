WITH v AS (SELECT :word::text AS word)
    ,s AS (SELECT id FROM Russian JOIN v USING (word))
    ,i AS (
       INSERT INTO Russian (word)
       SELECT word
       FROM   v
       WHERE  NOT EXISTS (SELECT * FROM s)
       RETURNING id)
SELECT id FROM i
UNION  ALL
SELECT id FROM s;
