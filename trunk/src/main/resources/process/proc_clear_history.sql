create or replace procedure proc_clear_history (days in integer) is
nowdate timestamp;
BEGIN
  select sysdate-days into nowdate from dual;
  delete from t_product_history where timeStamp<=nowdate;
END;
