CREATE OR REPLACE PROCEDURE get_article_percentages(
    IN article_ids BIGINT[],
    INOUT cur_res REFCURSOR DEFAULT 'res_cur'
)
    LANGUAGE plpgsql
AS $$
BEGIN
    OPEN cur_res FOR
        WITH article_totals AS (
            SELECT
                o.article_id,
                SUM(o.debit) AS debit_sum,
                SUM(o.credit) AS credit_sum,
                SUM(o.debit) - SUM(o.credit) AS amount_sum
            FROM operations o
                     JOIN balance b ON b.id = o.balance_id
            WHERE o.article_id = ANY(article_ids)
            GROUP BY o.article_id
        )
        SELECT
            article_id,

            ROUND(
                    100.0 * debit_sum /
                    NULLIF(SUM(debit_sum) OVER (), 0),
                    2
            ) AS debit,

            ROUND(
                    100.0 * credit_sum /
                    NULLIF(SUM(credit_sum) OVER (), 0),
                    2
            ) AS credit,

            ROUND(
                    100.0 * amount_sum /
                    NULLIF(SUM(amount_sum) OVER (), 0),
                    2
            ) AS amount

        FROM article_totals
        ORDER BY article_id;
END;
$$;

CREATE OR REPLACE PROCEDURE get_income_expense_dynamics(
    IN date_from DATE,
    IN date_to DATE,
    IN article_ids BIGINT[],
    INOUT cur_res REFCURSOR DEFAULT 'res_cur'
)
    LANGUAGE plpgsql
AS $$
BEGIN
    OPEN cur_res FOR
        SELECT
            b.create_date::DATE AS operation_date,
            SUM(o.debit) AS expenses,
            SUM(o.credit) AS income
        FROM operations o
                 JOIN balance b ON b.id = o.balance_id
        WHERE b.create_date >= date_from
          AND b.create_date < date_to + INTERVAL '1 day'
          AND (
            article_ids IS NULL
                OR o.article_id = ANY(article_ids)
            )
        GROUP BY b.create_date::DATE
        ORDER BY operation_date;
END;
$$;

CREATE OR REPLACE PROCEDURE get_net_profit_dynamics(
    IN date_from DATE,
    IN date_to DATE,
    IN article_ids BIGINT[],
    INOUT cur_res REFCURSOR DEFAULT 'res_cur'
)
    LANGUAGE plpgsql
AS $$
BEGIN
    OPEN cur_res FOR
        SELECT
            b.create_date::DATE AS operation_date,
            SUM(o.credit) - SUM(o.debit) AS net_profit
        FROM operations o
                 JOIN balance b ON b.id = o.balance_id
        WHERE b.create_date >= date_from
          AND b.create_date < date_to + INTERVAL '1 day'
          AND (
            article_ids IS NULL
                OR o.article_id = ANY(article_ids)
            )
        GROUP BY b.create_date::DATE
        ORDER BY operation_date;
END;
$$;