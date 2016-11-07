CREATE TABLE Users (
  id         BIGINT PRIMARY KEY AUTO_INCREMENT,
  email      VARCHAR(255) NOT NULL UNIQUE,
  password   VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  gender     INT          NOT NULL,
  telephone  VARCHAR(20),
  birthday   DATE,
  country    VARCHAR(20),
  city       VARCHAR(25),
  university VARCHAR(50),
  team       INT,
  position   INT,
  about      VARCHAR(255),
);
INSERT INTO Users (email, password, first_name, last_name, gender)
VALUES ('admin@exam.com', '123456', 'Василий', 'Бобков', 1);

INSERT INTO Users (email, password, first_name, last_name, gender)
VALUES ('oldmail@exam.com', '123456', 'Петя', 'Петров', 1);