SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `FeedSpeakDB` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;

USE `FeedSpeakDB`;

CREATE  TABLE IF NOT EXISTS `FeedSpeakDB`.`users` (
  `id` INT NOT NULL ,
  `phone_number` VARCHAR(15) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `phone_number_UNIQUE` (`id` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `FeedSpeakDB`.`sources` (
  `source_id` INT NOT NULL ,
  `name` VARCHAR(45) NULL ,
  `url` VARCHAR(45) NULL ,
  `method` VARCHAR(45) NULL ,
  PRIMARY KEY (`source_id`) )
ENGINE = InnoDB;


CREATE  TABLE IF NOT EXISTS `FeedSpeakDB`.`user_sources` (
  `id` INT NOT NULL ,
  `user_id` INT NULL ,
  `source_id` INT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_user_id_idx` (`user_id` ASC) ,
  INDEX `fk_source_id_idx` (`source_id` ASC) ,
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id` )
    REFERENCES `FeedSpeakDB`.`users` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_source_id`
    FOREIGN KEY (`source_id` )
    REFERENCES `FeedSpeakDB`.`sources` (`source_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `FeedSpeakDB` ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

