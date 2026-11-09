INSERT INTO articles(name)
VALUES ('supermarkets');

INSERT INTO articles(name)
VALUES ('haircut');

INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (1, 100, 0, date_trunc('minute', LOCALTIMESTAMP), null);
INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (1, 100, 0, date_trunc('minute', LOCALTIMESTAMP), null);
INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (1, 100, 0, date_trunc('minute', LOCALTIMESTAMP), null);
INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (1, 100, 0, date_trunc('minute', LOCALTIMESTAMP), null);
INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (1, 100, 0, date_trunc('minute', LOCALTIMESTAMP), null);
INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (1, 100, 0, date_trunc('minute', LOCALTIMESTAMP), null);
INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (1, 100, 0, date_trunc('minute', LOCALTIMESTAMP), null);
INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (1, 100, 0, date_trunc('minute', LOCALTIMESTAMP), null);
INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (1, 100, 0, date_trunc('minute', LOCALTIMESTAMP), null);
INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (1, 100, 0, date_trunc('minute', LOCALTIMESTAMP), null);


INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (2, 100, 0, date_trunc('minute', LOCALTIMESTAMP) - INTERVAL '2 days', null);
INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (2, 100, 0, date_trunc('minute', LOCALTIMESTAMP) - INTERVAL '2 days', null);
INSERT INTO operations(article_id, debit, credit, create_date, balance_id)
VALUES (2, 100, 0, date_trunc('minute', LOCALTIMESTAMP) - INTERVAL '2 days', null);


INSERT INTO balance(debit, credit, amount)
VALUES (100, 100, 0);
INSERT INTO balance(debit, credit, amount)
VALUES (100, 100, 0);
INSERT INTO balance(debit, credit, amount)
VALUES (100, 100, 0);
INSERT INTO balance(debit, credit, amount)
VALUES (100, 100, 0);