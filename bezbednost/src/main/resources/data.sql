-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za user - User1234
                -- admin - Admin123

INSERT INTO USERS (username, password, first_name, last_name, email, enabled, last_password_reset_date) VALUES ('user', '$2a$12$RXqktT4alBoBQT/9uVSfRet5tIpCO96GqUm6/eNY7ONIAtMA53Od6', 'Marko', 'Markovic', 'user@example.com', true, '2017-10-01');
INSERT INTO USERS (username, password, first_name, last_name, email, enabled, last_password_reset_date) VALUES ('admin', '$2a$12$VO3ygoEyGYDIDdVTumD99Oguaab9SVqIjqNv3uNDrXqGMkbXJL2A.', 'Nikola', 'Nikolic', 'admin@example.com', true, '2017-10-01');
INSERT INTO USERS (username, password, first_name, last_name, email, enabled, last_password_reset_date) VALUES ('user1', '$2a$12$RXqktT4alBoBQT/9uVSfRet5tIpCO96GqUm6/eNY7ONIAtMA53Od6', 'Nikola', 'Nikolic', 'user1@example.com', true, '2017-10-01');

INSERT INTO ESTATES (address, description, estate_type) VALUES ('VLADIKE CIRICA 5', 'OPIS', 'APARTMENT');
INSERT INTO ESTATES (address, description, estate_type) VALUES ('Bulevar Evrope 5', 'OPIS', 'HOUSE');

INSERT INTO DEVICE (device_type, ip_address, path, estate_id) VALUES
    ('DOOR', '123.114.411.124', 'door1', 1),
    ('DOOR', '123.114.411.125', 'door2', 1),
    ('DOOR', '123.114.411.126', 'door3', 2),
    ('REFRIGERATOR', '123.114.411.127', 'refrigerator1', 2),
    ('AIR_CONDITIONER', '123.114.411.128', 'airconditioner1', 2),
    ('DISHWASHER', '123.114.411.178', 'dishwasher1', 1),
    ('DOORLOCK', '123.114.411.158', 'doorlock1', 1);

INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (name) VALUES ('ROLE_OWNER');
INSERT INTO ROLE (name) VALUES ('ROLE_RESIDENT');

INSERT INTO USER_ESTATE (user_id, estate_id) VALUES (1, 1); -- user-u dodeljujemo rolu USER
INSERT INTO USER_ESTATE (user_id, estate_id) VALUES (1, 2); -- user-u dodeljujemo rolu ADMIN
INSERT INTO USER_ESTATE (user_id, estate_id) VALUES (3, 2); -- user-u dodeljujemo rolu USER

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 2); -- user-u dodeljujemo rolu USER
INSERT INTO USER_ROLE (user_id, role_id) VALUES (3, 2); -- user-u dodeljujemo rolu USER
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 1); -- user-u dodeljujemo rolu ADMIN

INSERT INTO PRIVILEGE (name) VALUES ('READ_USER');
INSERT INTO PRIVILEGE (name) VALUES ('READ_USERS');
INSERT INTO PRIVILEGE (name) VALUES ('FIND_USER');
INSERT INTO PRIVILEGE (name) VALUES ('SAVE_USER');
INSERT INTO PRIVILEGE (name) VALUES ('UPDATE_USER');
INSERT INTO PRIVILEGE (name) VALUES ('DELETE_USER');
INSERT INTO PRIVILEGE (name) VALUES ('REVOKE_CERTIFICATE');
INSERT INTO PRIVILEGE (name) VALUES ('GENERATE_CSR');
INSERT INTO PRIVILEGE (name) VALUES ('SIGN_CSR');
INSERT INTO PRIVILEGE (name) VALUES ('DECODE_CSR');
INSERT INTO PRIVILEGE (name) VALUES ('CSR_EXTENSIONS');
INSERT INTO PRIVILEGE (name) VALUES ('GENERATE_ROOT');
INSERT INTO PRIVILEGE (name) VALUES ('GET_ALL_CERTIFICATES');
INSERT INTO PRIVILEGE (name) VALUES ('GET_ALL_CA_CERTIFICATES');
INSERT INTO PRIVILEGE (name) VALUES ('GET_ALL_REVOKED_CERTIFICATES');
INSERT INTO PRIVILEGE (name) VALUES ('GET_ONE_CERTIFICATE');
INSERT INTO PRIVILEGE (name) VALUES ('GET_CERTIFICATE_EXTENSIONS');
INSERT INTO PRIVILEGE (name) VALUES ('IS_CERTIFICATE_VALID');
INSERT INTO PRIVILEGE (name) VALUES ('GET_CSRS');
INSERT INTO PRIVILEGE (name) VALUES ('LOGOUT');
INSERT INTO PRIVILEGE (name) VALUES ('ACTIVATE_USER');
INSERT INTO PRIVILEGE (name) VALUES ('READ_ESTATES');
INSERT INTO PRIVILEGE (name) VALUES ('READ_ESTATES_USER');
INSERT INTO PRIVILEGE (name) VALUES ('ADD_ESTATE');
INSERT INTO PRIVILEGE (name) VALUES ('READ_LOGS');
INSERT INTO PRIVILEGE (name) VALUES ('TEST_DROOLS');
INSERT INTO PRIVILEGE (name) VALUES ('READ_DEVICE_ALARMS');
INSERT INTO PRIVILEGE (name) VALUES ('READ_SYSTEM_ALARMS');


INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 1);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 2);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 3);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 4);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 5);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 6);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 7);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 8);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 9);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 10);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 11);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 12);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 13);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 14);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 15);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 16);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 17);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 18);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 19);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 20);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (2, 20);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (3, 20);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 21);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 22);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 23);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 24);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 25);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (2, 26);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 27);
INSERT INTO ROLE_PRIVILEGE (role_id, privilege_id) VALUES (1, 28);

