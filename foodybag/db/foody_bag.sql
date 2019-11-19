-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema foody_bag
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `foody_bag` ;

-- -----------------------------------------------------
-- Schema foody_bag
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `foody_bag` DEFAULT CHARACTER SET latin1 ;
USE `foody_bag` ;

-- -----------------------------------------------------
-- Table `foody_bag`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `foody_bag`.`users` ;

CREATE TABLE IF NOT EXISTS `foody_bag`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(30) NOT NULL,
  `last_name` VARCHAR(30) NOT NULL,
  `address` VARCHAR(50) NOT NULL,
  `contact` BIGINT(20) NOT NULL,
  `email` VARCHAR(30) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `status` ENUM('active', 'deactivated') NOT NULL,
  `role` ENUM('admin', 'customer') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `foody_bag`.`customer_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `foody_bag`.`customer_order` ;

CREATE TABLE IF NOT EXISTS `foody_bag`.`customer_order` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `location` VARCHAR(30) NOT NULL,
  `status` ENUM('pending', 'delivered', 'canceled') NOT NULL DEFAULT 'pending',
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC),
  CONSTRAINT `orders_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `foody_bag`.`users` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `foody_bag`.`bills`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `foody_bag`.`bills` ;

CREATE TABLE IF NOT EXISTS `foody_bag`.`bills` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `order_id` INT(11) NOT NULL,
  `total_amount` DECIMAL(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC),
  INDEX `order_id` (`order_id` ASC),
  CONSTRAINT `bills_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `foody_bag`.`users` (`id`),
  CONSTRAINT `bills_ibfk_2`
    FOREIGN KEY (`order_id`)
    REFERENCES `foody_bag`.`customer_order` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `foody_bag`.`food_types`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `foody_bag`.`food_types` ;

CREATE TABLE IF NOT EXISTS `foody_bag`.`food_types` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `foody_bag`.`foods`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `foody_bag`.`foods` ;

CREATE TABLE IF NOT EXISTS `foody_bag`.`foods` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `type_id` INT(30) NOT NULL,
  `size` ENUM('small', 'medium', 'large') NULL DEFAULT NULL,
  `price` DECIMAL(10,0) NOT NULL,
  `img` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `foods_ibfk_1`
    FOREIGN KEY (`type_id`)
    REFERENCES `foody_bag`.`food_types` (`id`),
  CONSTRAINT `foods_ibfk_2`
    FOREIGN KEY (`type_id`)
    REFERENCES `foody_bag`.`food_types` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `foody_bag`.`messages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `foody_bag`.`messages` ;

CREATE TABLE IF NOT EXISTS `foody_bag`.`messages` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sender_id` INT(11) NOT NULL,
  `text` TEXT NOT NULL,
  `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` ENUM('unread', 'read') NOT NULL DEFAULT 'unread',
  `deleted` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `foody_bag`.`ratings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `foody_bag`.`ratings` ;

CREATE TABLE IF NOT EXISTS `foody_bag`.`ratings` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `food_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `rating` ENUM('1', '2', '3', '4', '5') NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC),
  CONSTRAINT `ratings_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `foody_bag`.`users` (`id`),
  CONSTRAINT `ratings_ibfk_3`
    FOREIGN KEY (`food_id`)
    REFERENCES `foody_bag`.`foods` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `foody_bag`.`restaurants`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `foody_bag`.`restaurants` ;

CREATE TABLE IF NOT EXISTS `foody_bag`.`restaurants` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `contact` BIGINT(20) NOT NULL,
  `website` VARCHAR(50) NOT NULL,
  `status` ENUM('open', 'closed') NOT NULL DEFAULT 'open',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `foody_bag`.`items_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `foody_bag`.`items_order` ;

CREATE TABLE IF NOT EXISTS `foody_bag`.`items_order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NOT NULL,
  `amount` DOUBLE NOT NULL,
  `orders_id` INT(11) NOT NULL,
  `foods_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_items_order_orders1_idx` (`orders_id` ASC),
  INDEX `fk_items_order_foods1_idx` (`foods_id` ASC),
  CONSTRAINT `fk_items_order_orders1`
    FOREIGN KEY (`orders_id`)
    REFERENCES `foody_bag`.`customer_order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_items_order_foods1`
    FOREIGN KEY (`foods_id`)
    REFERENCES `foody_bag`.`foods` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
