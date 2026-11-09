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
    IN date_from TIMESTAMP WITHOUT TIME ZONE,
    IN date_to TIMESTAMP WITHOUT TIME ZONE,
    IN article_ids BIGINT[],
    INOUT cur_res REFCURSOR DEFAULT 'res_cur'
)
    LANGUAGE plpgsql
AS $$
BEGIN
    OPEN cur_res FOR
        SELECT
            o.create_date::DATE AS date,
            SUM(o.debit) AS debit,
            SUM(o.credit) AS credit
        FROM operations o
        WHERE o.create_date >= date_from
          AND o.create_date < date_to + INTERVAL '1 day'
          AND (
            article_ids IS NULL
                OR o.article_id = ANY(article_ids)
            )
        GROUP BY o.create_date::DATE
        ORDER BY date;
END;
$$;

CREATE OR REPLACE PROCEDURE get_net_profit_dynamics(
    IN date_from TIMESTAMP WITHOUT TIME ZONE,
    IN date_to TIMESTAMP WITHOUT TIME ZONE,
    IN article_ids BIGINT[],
    INOUT cur_res REFCURSOR DEFAULT 'res_cur'
)
    LANGUAGE plpgsql
AS $$
BEGIN
    OPEN cur_res FOR
        SELECT
            o.create_date::DATE AS date,
            SUM(o.debit) - SUM(o.credit) AS net_profit
        FROM operations o
        WHERE o.create_date >= date_from
          AND o.create_date < date_to + INTERVAL '1 day'
          AND (
            article_ids IS NULL
                OR o.article_id = ANY(article_ids)
            )
        GROUP BY o.create_date::DATE
        ORDER BY date;
END;
$$;