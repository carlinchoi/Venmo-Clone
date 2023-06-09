BEGIN TRANSACTION;

DROP TABLE IF EXISTS transfer, tenmo_user, transfer_type, transfer_status;
DROP SEQUENCE IF EXISTS seq_user_id, seq_transfer_id;


CREATE TABLE transfer_type (
	transfer_type_id serial NOT NULL,
	transfer_type_desc varchar(10) NOT NULL,
	CONSTRAINT PK_transfer_type PRIMARY KEY (transfer_type_id)
);

CREATE TABLE transfer_status (
	transfer_status_id serial NOT NULL,
	transfer_status_desc varchar(10) NOT NULL,
	CONSTRAINT PK_transfer_status PRIMARY KEY (transfer_status_id)
);

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) UNIQUE NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(20),
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

--CREATE SEQUENCE seq_account_id
--  INCREMENT BY 1
--  START WITH 2001
--  NO MAXVALUE;

--CREATE TABLE account (
--	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
--	user_id int NOT NULL,
--	balance decimal(13, 2) NOT NULL,
--	CONSTRAINT PK_account PRIMARY KEY (account_id),
--	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
--);

CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TABLE transfer (
	transfer_id int NOT NULL DEFAULT nextval('seq_transfer_id'),
	transfer_type_id int NOT NULL,
	transfer_status_id int NOT NULL,
	account_from int NOT NULL,
	account_to int NOT NULL,
	amount decimal(13, 2) NOT NULL,
	CONSTRAINT PK_transfer PRIMARY KEY (transfer_id),
	CONSTRAINT FK_transfer_account_from FOREIGN KEY (account_from) REFERENCES tenmo_user (user_id),
	CONSTRAINT FK_transfer_account_to FOREIGN KEY (account_to) REFERENCES tenmo_user (user_id),
	CONSTRAINT FK_transfer_transfer_status FOREIGN KEY (transfer_status_id) REFERENCES transfer_status (transfer_status_id),
	CONSTRAINT FK_transfer_transfer_type FOREIGN KEY (transfer_type_id) REFERENCES transfer_type (transfer_type_id),
	CONSTRAINT CK_transfer_not_same_account CHECK (account_from <> account_to),
	CONSTRAINT CK_transfer_amount_gt_0 CHECK (amount > 0)
);

INSERT INTO transfer_status (transfer_status_desc) VALUES ('Approved');
INSERT INTO transfer_status (transfer_status_desc) VALUES ('Pending');
INSERT INTO transfer_status (transfer_status_desc) VALUES ('Rejected');

INSERT INTO transfer_type (transfer_type_desc) VALUES ('Send');
INSERT INTO transfer_type (transfer_type_desc) VALUES ('Request');


INSERT INTO tenmo_user (username,password_hash,role,balance) VALUES ('user1','user1','ROLE_USER',1000); -- 1001
INSERT INTO tenmo_user (username,password_hash,role,balance) VALUES ('user2','user2','ROLE_USER',1000); -- 1002
INSERT INTO tenmo_user (username,password_hash,role,balance) VALUES ('user3','user3','ROLE_USER',1000);

INSERT INTO transfer (account_from, account_to, amount, transfer_type_id, transfer_status_id) VALUES ('1001', '1002', 100.00, 1, 1);
INSERT INTO transfer (account_from, account_to, amount, transfer_type_id, transfer_status_id) VALUES ('1002', '1003', 500.00, 1, 1);
INSERT INTO transfer (account_from, account_to, amount, transfer_type_id, transfer_status_id) VALUES ('1003', '1001', 100.00, 1, 1);


COMMIT TRANSACTION;
