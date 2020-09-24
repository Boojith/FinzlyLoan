INSERT INTO  customer (customer_id,name,fathers_name,address,password,nominee,email,phone) VALUES
  ('CUS8743', 'Harry Potter', 'J K Rowling','Chennai','Customer@8743','Mother','customer@gmail.com',9345678094),
  ('CUS8345', 'Boojith', 'Kathikeyan','Chennai','Customer@8345','Mother','boojith@gmail.com',9500376088);
  
INSERT INTO  loan (loan_id,customer_id,loan_amount,trade_date,start_date,maturity_date,loan_duration,payment_frequency,payment_schedule,interest_rate,payment_term,projected_interest)  VALUES
  ('LAS8743', 'CUS8743', 2000, '21-09-1996','21-09-1996','21-03-1997',6,'Quarterly',2, 10, 'Even Principal', 200),
  ('LCR8343', 'CUS8743', 5000, '31-12-1990','31-12-1990','31-12-1991',12,'Monthly',12 ,9.5, 'Interest Only', 475);

 INSERT INTO payment_schedule (payment_id,loan_id,payment_date,principal,projected_interest,payment_status,payment_amount) VALUES
  (1,'LAS8743', '21-12-1996', 2000, 120,'PAID',2100),
  (2,'LAS8743', '21-03-1997', 1000, 100,' PROJECTED',1100);
  
