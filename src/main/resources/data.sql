INSERT INTO  customer (customer_id,name,fathers_name,address,password,nominee,email,phone) VALUES
  ('CUS060eab98', 'Harry Potter', 'J K Rowling','Chennai','harry@2020','Mother','harry@gmail.com',9345678094);
  
INSERT INTO  loan (loan_id,customer_id,loan_amount,trade_date,start_date,maturity_date,loan_duration,payment_frequency,payment_schedule,interest_rate,payment_term,projected_interest)  VALUES
  ('FINZ3937e3cd', 'CUS4176c2f8', 2000, '21-09-1996','21-09-1996','21-03-1997',6,'Quarterly',2, 10, 'Even Principal', 200),
  ('FINZb42ba627', 'CUS4176c2f8', 5000, '31-12-1990','31-12-1990','31-12-1991',12,'Monthly',12 ,9.5, 'Interest Only', 475),
  ('FINZ691d8457', 'CUS060eab98', 5000, '31-12-1990','31-12-1990','31-12-1991',8,'Monthly',16 ,9.5, 'Interest Only', 475),
  ('FINZ3f4f0951', 'CUS060eab98', 8000, '31-12-2006','31-12-2006','31-12-2008',12,'Half Yearly',14 ,9.5, 'Even Principal', 475);

 INSERT INTO payment_schedule (payment_id,loan_id,payment_date,principal,projected_interest,payment_status,payment_amount) VALUES
  ('PAYZ35aeed17','FINZ691d8457', '21-12-1996', 2000, 120,'PAID',2100),
  ('PAYZ5547f524','FINZ691d8457', '21-03-1997', 1000, 100,'PROJECTED',1100),
  ('PAYZ4f09ad25','FINZ3f4f0951', '21-03-1997', 1000, 100,'PROJECTED',1100),
  ('PAYZ24242812','FINZ3f4f0951', '21-03-1997', 1000, 100,'PROJECTED',1100);
  
