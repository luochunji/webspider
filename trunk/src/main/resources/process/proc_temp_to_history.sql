create or replace procedure proc_temp_to_history (days in integer)is
nowdate timestamp;
BEGIN
  select sysdate-days into nowdate from dual;
  insert into t_product_history (SELECT * from t_product_temp_result p where p.timeStamp<=nowdate);
  delete from t_product_temp_result where timeStamp<=nowdate;
END;
