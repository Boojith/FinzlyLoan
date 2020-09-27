INSERT INTO  customer (customer_id, address, email, fathers_name, name, nominee, password, phone) VALUES
  ('CUSfcd29f61', 'Chennai', 'boojith22@gmail.com', 'Karthikeyan', 'Boojith', 'Mother', 'Boojith@1940', '9500376088');
  
INSERT INTO  loan (loan_id, customer_id, interest_rate, loan_amount, loan_duration, maturity_date, payment_frequency, payment_schedule, payment_term, projected_interest, start_date, trade_date)  VALUES
  ('FINZc629b67b', 'CUSfcd29f61', '10', '10000', '5', '27-09-2025', 'Yearly', '5', 'Interest Only', '3000', '27-09-2020', '27-09-2020'),
  ('FINZ5b266059', 'CUSfcd29f61', '10', '20000', '5', '30-09-2025', 'Yearly', '5', 'Even Principal', '6000', '30-09-2020', '28-09-2020');

 INSERT INTO payment_schedule (payment_id, loan_id, payment_amount, payment_date, payment_status, principal, projected_interest) VALUES
  ('PAY3ed72317', 'FINZc629b67b', '400', '27-09-2024', 'PROJECTED', '0', '400'),
  ('PAYa23c9d5f', 'FINZc629b67b', '10200', '27-09-2025', 'PROJECTED', '10000', '200'),
  ('PAYc37603e4', 'FINZc629b67b', '600', '27-09-2023', 'PROJECTED', '0', '600'),
  ('PAYe317fb53', 'FINZc629b67b', '1000', '27-09-2021', 'PROJECTED', '0', '1000'),
  ('PAYfe2606d4', 'FINZc629b67b', '800', '27-09-2022', 'PROJECTED', '0', '800'),
  ('PAY2b3d1380', 'FINZ5b266059', '6000', '30-09-2021', 'PROJECTED', '20000', '2000'),
  ('PAY887cb4b1', 'FINZ5b266059', '4400', '30-09-2025', 'PROJECTED', '4000', '400'),
  ('PAY9ae68076', 'FINZ5b266059', '4800', '30-09-2024', 'PROJECTED', '8000', '800'),
  ('PAYabf0d8c7', 'FINZ5b266059', '5200', '30-09-2023', 'PROJECTED', '12000', '1200'),
  ('PAYfb261016', 'FINZ5b266059', '5600', '30-09-2022', 'PROJECTED', '16000', '1600');
  
  
  
