delete from mp_users;
--TestPassword12345
INSERT INTO mp_users(id, active, creation_date, email, first_name, last_name, password, phone, username)
	VALUES (10, true, '2022-08-16 15:54:51.471804', 'email@email.ru', 'Name',
	'Familia', '$2a$10$xwvCvrOPrIGTaO2buYmAZe5ACLgzXBnFdb7irj1y9D4zNQWQ5tEYe', '89998488888', 'User');