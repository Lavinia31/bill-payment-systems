CREATE TABLE payment_method (
  payment_method_id BIGSERIAL NOT NULL PRIMARY KEY,
  card_digits VARCHAR(4) NOT NULL,
  CONSTRAINT valid_card_digits CHECK (LENGTH(card_digits) = 4),
  expiration_date VARCHAR(5) NOT NULL,
  CONSTRAINT valid_expiration_date CHECK (expiration_date ~ '^../..$'),
  token VARCHAR(50) NOT NULL,
  inserted_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_date TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE client(
  client_id BIGSERIAL NOT NULL PRIMARY KEY,
  payment_method_id BIGINT NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  address VARCHAR(50) NOT NULL,
  phone_number VARCHAR(10) NOT NULL,
  CONSTRAINT valid_phone_number CHECK (LENGTH(phone_number) = 10),
  email VARCHAR(50) NOT NULL,
  type VARCHAR(50) NOT NULL,
  CONSTRAINT valid_type CHECK (type IN ('PRIVATE', 'BUSINESS')),
  balance DECIMAL NOT NULL,
  inserted_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  FOREIGN KEY (payment_method_id) REFERENCES payment_method(payment_method_id)
);


CREATE TABLE invoice (
  invoice_id BIGSERIAL NOT NULL PRIMARY KEY,
  client_id BIGINT NOT NULL,
  invoice_type VARCHAR(50) NOT NULL,
  CONSTRAINT valid_invoice CHECK (invoice_type IN ('B2B', 'B2C')),
  issued_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  due_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  amount DECIMAL NOT NULL,
  services TEXT NOT NULL,
  payment_status VARCHAR(50) NOT NULL,
  CONSTRAINT valid_status CHECK (payment_status IN ('PENDING', 'SUCCESSFUL', 'FAILED')),
  inserted_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  FOREIGN KEY (client_id) REFERENCES client(client_id)
);


CREATE TABLE payment (
  payment_id BIGSERIAL NOT NULL PRIMARY KEY,
  invoice_id BIGINT NOT NULL,
  payment_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  card_digits VARCHAR(50) NOT NULL,
  CONSTRAINT valid_card_digits CHECK (LENGTH(card_digits) = 4),
  status_code VARCHAR(5) NOT NULL,
  status_message VARCHAR(50) NOT NULL,
  inserted_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  FOREIGN KEY (invoice_id) REFERENCES invoice(invoice_id)
);












INSERT INTO payment_method(card_digits, expiration_date, token,inserted_date,updated_date)
values('1111','10/23','321312','2022-11-11 12:21:21','2022-11-22 12:33:33');

INSERT INTO client(payment_method_id, last_name, first_name,address,phone_number,email,type,balance,inserted_date,updated_date)
values('1','vlad','mihai','bucuresti','1234567890','mihai@gmail.com','private','500','2022-11-11 12:21:21','2022-11-22 12:33:33');

INSERT INTO payment_method(card_digits, expiration_date, token,inserted_date,updated_date)
values('2348','04/26','343232','2022-04-19 04:51:31','2022-10-29 10:31:33');

INSERT INTO payment_method(card_digits, expiration_date, token,inserted_date,updated_date)
values('1234','23/21','33312','2023-12-11 10:39:21','2024-01-22 12:33:33');


INSERT INTO client(payment_method_id, last_name, first_name,address,phone_number,email,type,balance,inserted_date,updated_date)
values('2','ionescu','ana','galati','0987654321','ana@gmail.com','business','1000','2010-11-11 13:41:41','2010-11-22 12:33:33');


INSERT INTO client(payment_method_id, last_name, first_name,address,phone_number,email,type,balance,inserted_date,updated_date)
values('2','popescu','gheorghe','ploiesti','0723568709','gheorghe@gmail.com','business','9000','2020-01-05 13:41:41','2020-01-22 18:33:33');

INSERT INTO invoice(client_id, invoice_type, issued_date,due_date,amount,services,payment_status,inserted_date,updated_date)
values('1','B2C','2010-11-22 12:33:33','2010-11-22 12:33:33','300','voce','pending','2022-09-11 12:21:21','2022-11-22 12:33:33');

INSERT INTO invoice(client_id, invoice_type, issued_date,due_date,amount,services,payment_status,inserted_date,updated_date)
values('1','B2B','2010-11-22 12:33:33','2010-11-22 12:33:33','100','voce','pending','2022-11-11 12:21:21','2022-11-22 12:33:33');

INSERT INTO payment(invoice_id, payment_date, card_digits,status_code,status_message ,inserted_date,updated_date)
values('1','2010-11-22 12:33:33','6583','06','error','2022-11-11 12:21:21','2000-11-22 12:33:33');

INSERT INTO payment(invoice_id, payment_date, card_digits,status_code,status_message ,inserted_date,updated_date)
values('1','2010-11-22 12:33:33','3442','06','error','1322-11-11 12:21:21','1233-11-22 12:33:33');

INSERT INTO payment(invoice_id, payment_date, card_digits,status_code,status_message ,inserted_date,updated_date)
values('1','2010-11-22 12:33:33','7623','06','error','2023-11-11 12:21:21','2023-11-22 12:33:33');
