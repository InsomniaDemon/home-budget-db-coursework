CREATE FUNCTION trg_op_upd_fn()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    IF OLD.balance_id IS NOT NULL AND (NEW.debit IS DISTINCT FROM OLD.debit OR NEW.credit IS DISTINCT FROM OLD.credit)
        THEN RAISE EXCEPTION 'Debit and credit cannot be changed: operation is already included in a balance'
            USING ERRCODE = 'HB001';
    END IF;

    RETURN NEW;
END$$;

CREATE TRIGGER trg_op_upd
    BEFORE UPDATE ON operations
    FOR EACH ROW
    EXECUTE FUNCTION trg_op_upd_fn();

CREATE FUNCTION trg_op_del_fn()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS $$
BEGIN
    IF OLD.balance_id IS NOT NULL
        THEN RAISE EXCEPTION 'Operation is already included in a balance and cannot be deleted'
        USING ERRCODE = 'HB002';
    END IF;

    RETURN OLD;
END$$;

CREATE TRIGGER trg_op_del
    BEFORE DELETE ON operations
    FOR EACH ROW
EXECUTE FUNCTION trg_op_del_fn();

CREATE FUNCTION trg_balance_ins_fn()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS $$
BEGIN
    IF COALESCE(NEW.debit, 0) <= 0 AND COALESCE(NEW.credit, 0) <= 0
        THEN RAISE EXCEPTION 'Balance must have a positive debit or credit'
        USING ERRCODE = 'HB003';
    END IF;

    RETURN NEW;
END$$;

CREATE TRIGGER trg_balance_ins
    BEFORE INSERT ON balance
    FOR EACH ROW
EXECUTE FUNCTION trg_balance_ins_fn();