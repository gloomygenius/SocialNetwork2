INSERT INTO Users (email, password, first_name, last_name, gender, role, telephone, birthday, country, position)
VALUES ('admin@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Василий', 'Бобков', 0, 2, '+79118556688', '1993-12-01','Россия', 1);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('oldmail@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Петя', 'Петров', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user3@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Антон', 'Чехов', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user4@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Александр', 'Пушкин', 0, 1);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user5@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Филипп', 'Преображенский', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user6@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Александр', 'Македонский', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user7@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Елена', 'Троянская', 1, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user8@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Елена', 'Пушкова', 1, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user9@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Степан', 'Калашников', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user10@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Евгений', 'Скребцов', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user11@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Николай', 'Снегирёв', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user12@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Алёна', 'Саксонова', 1, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user13@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Анастасия', 'Полецкая', 1, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user14@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Евгений', 'Юруть', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user15@exam.com', 'e10adc3949ba59abbe56e057f20f883e', 'Леонид', 'Терлецкий', 0, 0);


INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,2,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,6,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,7,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,8,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,9,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,10,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,11,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,12,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,13,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,14,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,15,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,4,1);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (5,1,1);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (3,1,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (4,2,3);


INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 1', '2016-11-15 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 2', '2016-11-13 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 3', '2016-11-10 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 4', '2016-11-11 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 5', '2016-11-14 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 6', '2016-11-12 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 6', '2016-11-11 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 6', '2016-11-10 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 6', '2016-11-09 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 6', '2016-11-08 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 6', '2016-11-07 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 6', '2016-11-06 10:28:42');

INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (1, 2);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (2, 3);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (3, 4);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (4, 5);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (5, 6);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (6, 7);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (7, 8);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (8, 9);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (9, 10);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (10, 11);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (11, 12);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (12, 13);

INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Привет!', '2016-11-15 10:01:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (2, 1, 'Привет!', '2016-11-15 10:02:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Как дела?', '2016-11-15 10:03:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (2, 1, 'Нормльно. Как сам?', '2016-11-15 10:04:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Потихоньку', '2016-11-15 10:05:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (2, 1, 'Чем занимаешься?', '2016-11-15 10:06:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Программирую. А ты?', '2016-11-15 10:07:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (2, 1, 'Я пишу курсач', '2016-11-15 10:08:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Получается?', '2016-11-15 10:09:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (2, 1, 'Пока не очень', '2016-11-15 10:10:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'А по какому предмету?', '2016-11-15 10:11:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (2, 1, 'Российская политика', '2016-11-15 10:12:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Ого :)', '2016-11-15 10:13:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (2, 1, 'Да, бывают такие курсачи', '2016-11-15 10:14:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'И что же там интересного?', '2016-11-15 10:15:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (2, 1, 'Санкции и их влияние на политику', '2016-11-15 10:16:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Понятно', '2016-11-15 10:17:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (2, 1, 'А что ты программируешь?', '2016-11-15 10:18:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Социальную сеть. Буду сдавать скоро экзамен в EPAM', '2016-11-15 10:19:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (2, 1, 'Успехов тебе!', '2016-11-15 10:20:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Спасибо', '2016-11-15 10:21:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (2, 1, 'Ладно, я пойду чаёк пить, пока!', '2016-11-15 10:22:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Приятного аппетита!', '2016-11-15 10:23:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Пока', '2016-11-15 10:24:42');