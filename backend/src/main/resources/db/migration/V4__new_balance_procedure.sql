CREATE PROCEDURE insert_new_balance()
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO balance (debit, credit, amount)
    SELECT SUM(debit), SUM(credit), SUM(debit - credit)
    FROM operations
    WHERE create_date BETWEEN LOCALTIMESTAMP - INTERVAL '1 month' AND  LOCALTIMESTAMP AND balance_id IS NULL;

    UPDATE operations
    SET balance_id = (SELECT MAX(id) FROM balance)
    WHERE create_date BETWEEN LOCALTIMESTAMP - INTERVAL '1 month' AND  LOCALTIMESTAMP AND balance_id IS NULL;
END $$;
