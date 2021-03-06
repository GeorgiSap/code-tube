-- MySQL Script generated by MySQL Workbench
-- 10/28/16 19:16:43
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema codetube
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema codetube
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `codetube` DEFAULT CHARACTER SET utf8 ;
USE `codetube` ;

-- -----------------------------------------------------
-- Table `codetube`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `codetube`.`users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(30) NOT NULL,
  `last_name` VARCHAR(30) NOT NULL,
  `user_name` VARCHAR(30) NOT NULL,
  `email` VARCHAR(30) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `password` (`password` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `codetube`.`video_clips`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `codetube`.`video_clips` (
  `video_clip_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `path` VARCHAR(45) NOT NULL,
  `performer` VARCHAR(45) NOT NULL,
  `user_id` INT(11) NULL DEFAULT NULL,
  `view_count` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`video_clip_id`),
  INDEX `fk_video_clips_users_idx` (`user_id` ASC),
  CONSTRAINT `fk_video_clips_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `codetube`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `codetube`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `codetube`.`comments` (
  `comment_id` INT(11) NOT NULL AUTO_INCREMENT,
  `message` VARCHAR(45) NOT NULL,
  `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `video_clip_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `rating` INT(11) NOT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `fk_comments_video_clips1_idx` (`video_clip_id` ASC),
  INDEX `fk_comments_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_comments_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `codetube`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comments_video_clips1`
    FOREIGN KEY (`video_clip_id`)
    REFERENCES `codetube`.`video_clips` (`video_clip_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `codetube`.`playlists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `codetube`.`playlists` (
  `playlist_id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `user_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`playlist_id`),
  INDEX `fk_play_lists_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_play_lists_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `codetube`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `codetube`.`playlist_has_video_clips`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `codetube`.`playlist_has_video_clips` (
  `play_list_id` INT(11) NOT NULL,
  `video_clip_id` INT(11) NOT NULL,
  `number_in_playlist` INT(11) NOT NULL,
  PRIMARY KEY (`play_list_id`, `video_clip_id`),
  INDEX `fk_play_lists_has_video_clips_video_clips1_idx` (`video_clip_id` ASC),
  INDEX `fk_play_lists_has_video_clips_play_lists1_idx` (`play_list_id` ASC),
  CONSTRAINT `fk_play_lists_has_video_clips_play_lists1`
    FOREIGN KEY (`play_list_id`)
    REFERENCES `codetube`.`playlists` (`playlist_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_play_lists_has_video_clips_video_clips1`
    FOREIGN KEY (`video_clip_id`)
    REFERENCES `codetube`.`video_clips` (`video_clip_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `codetube`.`tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `codetube`.`tags` (
  `tag_id` INT(11) NOT NULL AUTO_INCREMENT,
  `keyword` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`tag_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `codetube`.`user_has_subscribers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `codetube`.`user_has_subscribers` (
  `user_id` INT(11) NOT NULL,
  `subscriber_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `subscriber_id`),
  INDEX `fk_users_has_users_users2_idx` (`subscriber_id` ASC),
  INDEX `fk_users_has_users_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_users_has_users_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `codetube`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_users_users2`
    FOREIGN KEY (`subscriber_id`)
    REFERENCES `codetube`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `codetube`.`user_has_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `codetube`.`user_has_history` (
  `video_clip_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `last_viewed` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`video_clip_id`, `user_id`),
  INDEX `fk_video_clips_has_users_users1_idx` (`user_id` ASC),
  INDEX `fk_video_clips_has_users_video_clips1_idx` (`video_clip_id` ASC),
  CONSTRAINT `fk_video_clips_has_users_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `codetube`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_video_clips_has_users_video_clips1`
    FOREIGN KEY (`video_clip_id`)
    REFERENCES `codetube`.`video_clips` (`video_clip_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `codetube`.`video_clip_has_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `codetube`.`video_clip_has_tags` (
  `tag_id` INT(11) NOT NULL,
  `video_clip_id` INT(11) NOT NULL,
  PRIMARY KEY (`tag_id`, `video_clip_id`),
  INDEX `fk_tags_has_video_clips_video_clips1_idx` (`video_clip_id` ASC),
  INDEX `fk_tags_has_video_clips_tags1_idx` (`tag_id` ASC),
  CONSTRAINT `fk_tags_has_video_clips_tags1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `codetube`.`tags` (`tag_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_tags_has_video_clips_video_clips1`
    FOREIGN KEY (`video_clip_id`)
    REFERENCES `codetube`.`video_clips` (`video_clip_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
