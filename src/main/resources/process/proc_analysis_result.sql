create or replace procedure proc_analysis_result (firetime in timestamp) is
BEGIN
  insert into T_PRODUCT_HISTORY select * from t_product_result;
  delete from t_product_result;
  insert into t_product_result (SELECT * from t_product p where p.price<p.low_price and p.taskType ='NORMAL' and p.timeStamp>=firetime);
END;