-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: attendoo
-- ------------------------------------------------------
-- Server version	8.0.39

--
-- Table structure for table `proposal`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(50) NOT NULL,
                        PRIMARY KEY (`id`)
);
LOCK TABLES `role` WRITE;
INSERT INTO `role` VALUES (1,'admin'),(2,'user'),(3,'visitor');
UNLOCK TABLES;


DROP TABLE IF EXISTS `user_status`;
CREATE TABLE `user_status` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `name` varchar(50) NOT NULL,
                               PRIMARY KEY (`id`)
);
LOCK TABLES `user_status` WRITE;
INSERT INTO `user_status` VALUES (1,'In Office'),(2,'Home Office'),(3,'Vacation'),(4,'Illness'),(5,'Training'),(6,'active');
UNLOCK TABLES;

DROP TABLE IF EXISTS `user_department`;
CREATE TABLE `user_department` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `name` varchar(50) NOT NULL,
                                   PRIMARY KEY (`id`)
);
LOCK TABLES `user_department` WRITE;
INSERT INTO `user_department` VALUES (1,'Management'),(2,'Network'),(3,'DevOps team'),(4,'Team Director'),(5,'HR'),(6,'Sys Admins');
UNLOCK TABLES;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `first_name` varchar(255) NOT NULL,
                        `last_name` varchar(255) NOT NULL,
                        `attendoo_username` varchar(255) NOT NULL,
                        `attendoo_password` varchar(255) NOT NULL,
                        `email` varchar(255) NOT NULL,
                        `phone_number` varchar(255) NOT NULL,
                        `role_id` int NOT NULL,
                        `default_user_status_id` int NOT NULL,
                        `user_department_id` int NOT NULL,
                        PRIMARY KEY (`id`),
                        KEY `fk_user_role_id__id` (`role_id`),
                        KEY `fk_user_default_user_status_id__id` (`default_user_status_id`),
                        KEY `fk_user_user_department_id__id` (`user_department_id`),
                        CONSTRAINT `fk_user_default_user_status_id__id` FOREIGN KEY (`default_user_status_id`) REFERENCES `user_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                        CONSTRAINT `fk_user_role_id__id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                        CONSTRAINT `fk_user_user_department_id__id` FOREIGN KEY (`user_department_id`) REFERENCES `user_department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);
LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,'Lukáš','Friedl','lukasfriedl','b9b676b941e81ea27a5e636e6c64580814a069394d8b3f3764bef535f1dd82f0e7010a47ac93253088e95790e1eadf2c','lukys3036@gmail.com','604414555',1,6,6);
UNLOCK TABLES;


DROP TABLE IF EXISTS `user_attendances`;
CREATE TABLE `user_attendances` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `user_id` int NOT NULL,
                                    `user_status_id` int NOT NULL,
                                    `start_date` date NOT NULL,
                                    `end_date` date DEFAULT NULL,
                                    PRIMARY KEY (`id`),
                                    KEY `fk_user_attendances_user_id__id` (`user_id`),
                                    KEY `fk_user_attendances_user_status_id__id` (`user_status_id`),
                                    CONSTRAINT `fk_user_attendances_user_id__id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                    CONSTRAINT `fk_user_attendances_user_status_id__id` FOREIGN KEY (`user_status_id`) REFERENCES `user_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);
LOCK TABLES `user_attendances` WRITE;
UNLOCK TABLES;


DROP TABLE IF EXISTS `user_department_mapping`;
CREATE TABLE `user_department_mapping` (
                                           `id` int NOT NULL AUTO_INCREMENT,
                                           `user_id` int NOT NULL,
                                           `user_department_id` int NOT NULL,
                                           PRIMARY KEY (`id`),
                                           KEY `fk_user_department_mapping_user_id__id` (`user_id`),
                                           KEY `fk_user_department_mapping_user_department_id__id` (`user_department_id`),
                                           CONSTRAINT `fk_user_department_mapping_user_department_id__id` FOREIGN KEY (`user_department_id`) REFERENCES `user_department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                           CONSTRAINT `fk_user_department_mapping_user_id__id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);
LOCK TABLES `user_department_mapping` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
                       `id` int NOT NULL AUTO_INCREMENT,
                       `tag_name` varchar(50) NOT NULL,
                       PRIMARY KEY (`id`)
);
LOCK TABLES `tag` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `proposal`;
CREATE TABLE `proposal` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) NOT NULL,
                            `description` text NOT NULL,
                            `proposer_id` int NOT NULL,
                            `proposed_id` int NOT NULL,
                            `created_at` datetime(6) NOT NULL,
                            `resolved_at` datetime(6) DEFAULT NULL,
                            `current_user_status_id` int NOT NULL,
                            `proposed_user_status_id` int NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `fk_proposal_proposer_id__id` (`proposer_id`),
                            KEY `fk_proposal_proposed_id__id` (`proposed_id`),
                            KEY `fk_proposal_current_user_status_id__id` (`current_user_status_id`),
                            KEY `fk_proposal_proposed_user_status_id__id` (`proposed_user_status_id`),
                            CONSTRAINT `fk_proposal_current_user_status_id__id` FOREIGN KEY (`current_user_status_id`) REFERENCES `user_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `fk_proposal_proposed_id__id` FOREIGN KEY (`proposed_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `fk_proposal_proposed_user_status_id__id` FOREIGN KEY (`proposed_user_status_id`) REFERENCES `user_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `fk_proposal_proposer_id__id` FOREIGN KEY (`proposer_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);
LOCK TABLES `proposal` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `proposal_tag`;
CREATE TABLE `proposal_tag` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `proposal_id` int NOT NULL,
                                `tag_id` int NOT NULL,
                                PRIMARY KEY (`id`),
                                KEY `fk_proposal_tag_proposal_id__id` (`proposal_id`),
                                KEY `fk_proposal_tag_tag_id__id` (`tag_id`),
                                CONSTRAINT `fk_proposal_tag_proposal_id__id` FOREIGN KEY (`proposal_id`) REFERENCES `proposal` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT `fk_proposal_tag_tag_id__id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);
LOCK TABLES `proposal_tag` WRITE;
UNLOCK TABLES;
