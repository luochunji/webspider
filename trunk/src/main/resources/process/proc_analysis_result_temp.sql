create or replace procedure proc_analysis_result_temp (firetime in timestamp) is
BEGIN
  insert into T_PRODUCT_TEMP_RESULT (SELECT * from t_product p where p.price<p.low_price and p.taskType ='TEMP' and p.timeStamp>=firetime);
END;