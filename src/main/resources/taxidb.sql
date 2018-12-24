DROP TABLE IF EXISTS `Order`;
DROP TABLE IF EXISTS `User`;


CREATE TABLE `User` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`login` VARCHAR(255) NOT NULL,
	`pwd` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NOT NULL,
	`phone` VARCHAR(255) NOT NULL,
	`authenticated` BOOLEAN NOT NULL,
	`isBusy` BOOLEAN NOT NULL,
	`rating` FLOAT NOT NULL,
	`TypeUser` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Order` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`sourceAddr` VARCHAR(255) NOT NULL,
	`destAddr` VARCHAR(255) NOT NULL,
	`creationDate` DATETIME NOT NULL,
	`executionDate` DATETIME NOT NULL,
	`driverId` INT NOT NULL,
	`passId` INT NOT NULL,
	`operatorId` INT NOT NULL,
	`routeLength` FLOAT NOT NULL,
	`waitingTime` FLOAT NOT NULL,
	`totalCost` FLOAT NOT NULL,
	`isPayed` BOOLEAN NOT NULL,
	`complaint` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `Order` ADD CONSTRAINT `Order_fk0` FOREIGN KEY (`driverId`) REFERENCES `User`(`id`);

ALTER TABLE `Order` ADD CONSTRAINT `Order_fk1` FOREIGN KEY (`passId`) REFERENCES `User`(`id`);

ALTER TABLE `Order` ADD CONSTRAINT `Order_fk2` FOREIGN KEY (`operatorId`) REFERENCES `User`(`id`);

INSERT INTO `User`(login, pwd, name, email, phone, authenticated, isBusy, rating, TypeUser) VALUES
   ("op1","111","Pavel","a@yandex.ru","12345",false, false, 0.0, 0 ),
   ("us1","111","Olga", "b@yandex.ru","12346", false, false, 0.0,1 ),
   ("us2","111","Sergei", "c@yandex.ru","12347", false, false, 0.0,1 ),
   ("dr1","111","Anton", "d@yandex.ru","12348", false, false, 1.0,2 ),
   ("dr2","111","Lex", "e@yandex.ru","12349", false, false, 2.0,2 );
   
   