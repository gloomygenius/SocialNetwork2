INSERT INTO Users (email, password, first_name, last_name, gender, role, telephone, birthday, country)
VALUES ('admin@exam.com', '123456', 'Василий', 'Бобков', 0, 2, '+79118556688', '1993-12-01','Россия');
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('oldmail@exam.com', '123456', 'Петя', 'Петров', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user3@exam.com', '123456', 'Антон', 'Чехов', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('pushkin@exam.com', '123456', 'Александр', 'Пушкин', 0, 1);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('philipp@exam.com', '123456', 'Филипп', 'Преображенский', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('makedonskiy@exam.com', '123456', 'Александр', 'Македонский', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('elena@exam.com', '123456', 'Елена', 'Троянская', 1, 0);


INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,2,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,4,1);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (5,1,1);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (3,1,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (4,2,3);