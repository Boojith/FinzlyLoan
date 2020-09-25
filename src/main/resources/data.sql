INSERT INTO  customer (customer_id,name,fathers_name,address,password,nominee,email,phone) VALUES
  ('CUS03907364', 'Harry Potter', 'J K Rowling','Chennai','customer@2020','Mother','customer@gmail.com',9345678094),
  ('CUS03950388', 'Boojith', 'Kathikeyan','Chennai','boojith@1996','Mother','boojith22@gmail.com',9500376088);
  
INSERT INTO  loan (loan_id,customer_id,loan_amount,trade_date,start_date,maturity_date,loan_duration,payment_frequency,payment_schedule,interest_rate,payment_term,projected_interest)  VALUES
  ('FINZ03842499', 'CUS03907364', 2000, '21-09-1996','21-09-1996','21-03-1997',6,'Quarterly',2, 10, 'Even Principal', 200),
  ('FINZ03907372', 'CUS03907364', 5000, '31-12-1990','31-12-1990','31-12-1991',12,'Monthly',12 ,9.5, 'Interest Only', 475),
  ('FINZ03907882', 'CUS03950388', 5000, '31-12-1990','31-12-1990','31-12-1991',8,'Monthly',16 ,9.5, 'Interest Only', 475),
  ('FINZ03907852', 'CUS03950388', 8000, '31-12-2006','31-12-2006','31-12-2008',12,'Half Yearly',14 ,9.5, 'Even Principal', 475);

 INSERT INTO payment_schedule (payment_id,loan_id,payment_date,principal,projected_interest,payment_status,payment_amount) VALUES
  (1,'FINZ03842499', '21-12-1996', 2000, 120,'PAID',2100),
  (2,'FINZ03842499', '21-03-1997', 1000, 100,' PROJECTED',1100),
  (3,'FINZ03907852', '21-03-1997', 1000, 100,' PROJECTED',1100),
  (4,'FINZ03907852', '21-03-1997', 1000, 100,' PROJECTED',1100);
  
