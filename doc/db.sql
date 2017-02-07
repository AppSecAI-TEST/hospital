/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.29 : Database - hs
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `app_notification` */

CREATE TABLE `app_notification` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `message` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `receiver` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sender` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sender_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `state` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_user` */

CREATE TABLE `app_user` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `dept_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL,
  `org_id` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_apply` */

CREATE TABLE `domain_apply` (
  `DTYPE` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `execute_date` datetime DEFAULT NULL,
  `goal` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `plan_execute_date` datetime DEFAULT NULL,
  `order_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKatw6e7qp0sxr6qe7ieos1woii` (`order_id`),
  KEY `FKpn62rvs7of7f4epbn7wpvyy67` (`visit_id`),
  CONSTRAINT `FKatw6e7qp0sxr6qe7ieos1woii` FOREIGN KEY (`order_id`) REFERENCES `domain_order` (`id`),
  CONSTRAINT `FKpn62rvs7of7f4epbn7wpvyy67` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_assist_material` */

CREATE TABLE `domain_assist_material` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `code` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKldqt5pbtl58mfs310prf6ox21` (`charge_item_id`),
  CONSTRAINT `FKldqt5pbtl58mfs310prf6ox21` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_charge_bill` */

CREATE TABLE `domain_charge_bill` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `balance` float NOT NULL,
  `state` varchar(32) COLLATE utf8_bin NOT NULL,
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8ynu8995utkwuvx05ptaernr` (`visit_id`),
  CONSTRAINT `FK8ynu8995utkwuvx05ptaernr` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_charge_item` */

CREATE TABLE `domain_charge_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `charging_mode` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `code` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `price` float NOT NULL,
  `unit` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_charge_record` */

CREATE TABLE `domain_charge_record` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `amount` float DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `price` float DEFAULT NULL,
  `charge_bill_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `charge_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `cost_record_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `order_execute_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `original_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5wxyp4mi4q6lg6iacd6ssj4ub` (`charge_bill_id`),
  KEY `FKcic44hpsn3a17vibmwcrkl90w` (`charge_dept_id`),
  KEY `FKti75seq7agey41s762a09mv40` (`charge_item_id`),
  KEY `FKsbiyiqqev1y89oksjmxdj1vxp` (`cost_record_id`),
  KEY `FKexxtorggih17esbt4y9ra5npr` (`order_execute_id`),
  KEY `FKls0b83oyalie4has257rih9a2` (`original_id`),
  CONSTRAINT `FK5wxyp4mi4q6lg6iacd6ssj4ub` FOREIGN KEY (`charge_bill_id`) REFERENCES `domain_charge_bill` (`id`),
  CONSTRAINT `FKcic44hpsn3a17vibmwcrkl90w` FOREIGN KEY (`charge_dept_id`) REFERENCES `domain_unit` (`id`),
  CONSTRAINT `FKexxtorggih17esbt4y9ra5npr` FOREIGN KEY (`order_execute_id`) REFERENCES `domain_order_execute` (`id`),
  CONSTRAINT `FKls0b83oyalie4has257rih9a2` FOREIGN KEY (`original_id`) REFERENCES `domain_charge_record` (`id`),
  CONSTRAINT `FKsbiyiqqev1y89oksjmxdj1vxp` FOREIGN KEY (`cost_record_id`) REFERENCES `domain_cost_record` (`id`),
  CONSTRAINT `FKti75seq7agey41s762a09mv40` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_cost_record` */

CREATE TABLE `domain_cost_record` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `cost` float NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_drug_type` */

CREATE TABLE `domain_drug_type` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `stock` int(11) NOT NULL,
  `withhold` int(11) NOT NULL,
  `drug_type_spec_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `pharmacy_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKee69fsocn9hqvuojt1vi0e7sm` (`drug_type_spec_id`),
  KEY `FKkb77563yibi2i5irp7hauxnsl` (`pharmacy_id`),
  CONSTRAINT `FKee69fsocn9hqvuojt1vi0e7sm` FOREIGN KEY (`drug_type_spec_id`) REFERENCES `domain_drug_type_spec` (`id`),
  CONSTRAINT `FKkb77563yibi2i5irp7hauxnsl` FOREIGN KEY (`pharmacy_id`) REFERENCES `domain_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_drug_type_spec` */

CREATE TABLE `domain_drug_type_spec` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `effect` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `is_transport_fluid_charge` bit(1) DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin NOT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8vuacmqfovq07rowsakf08vxt` (`charge_item_id`),
  KEY `FK7u010q1nmq4jjeyl2tennbq1j` (`parent_id`),
  CONSTRAINT `FK7u010q1nmq4jjeyl2tennbq1j` FOREIGN KEY (`parent_id`) REFERENCES `domain_drug_type_spec` (`id`),
  CONSTRAINT `FK8vuacmqfovq07rowsakf08vxt` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_drug_use_mode` */

CREATE TABLE `domain_drug_use_mode` (
  `DTYPE` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `code` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_drug_use_mode_charge_item` */

CREATE TABLE `domain_drug_use_mode_charge_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `charge_mode` varchar(32) COLLATE utf8_bin NOT NULL,
  `code` varchar(32) COLLATE utf8_bin NOT NULL,
  `sign` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `assist_material_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `use_mode_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKae7rnjcin4npt524d5m8tw9mk` (`assist_material_id`),
  KEY `FK7gxuvk4r4urod6n7ont6lnvr4` (`use_mode_id`),
  CONSTRAINT `FK7gxuvk4r4urod6n7ont6lnvr4` FOREIGN KEY (`use_mode_id`) REFERENCES `domain_drug_use_mode` (`id`),
  CONSTRAINT `FKae7rnjcin4npt524d5m8tw9mk` FOREIGN KEY (`assist_material_id`) REFERENCES `domain_assist_material` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_inspect_apply_item` */

CREATE TABLE `domain_inspect_apply_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `execute_date` datetime DEFAULT NULL,
  `plan_execute_date` datetime DEFAULT NULL,
  `state` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `inspect_apply_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `inspect_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `inspect_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsav1gx8jdxosgclf3ta6rtolw` (`inspect_apply_id`),
  KEY `FKnworh4th6t898c0qqi9or5k0r` (`inspect_dept_id`),
  KEY `FK8xu25v8dal2kdu558pvcr31dh` (`inspect_item_id`),
  CONSTRAINT `FK8xu25v8dal2kdu558pvcr31dh` FOREIGN KEY (`inspect_item_id`) REFERENCES `domain_inspect_item` (`id`),
  CONSTRAINT `FKnworh4th6t898c0qqi9or5k0r` FOREIGN KEY (`inspect_dept_id`) REFERENCES `domain_unit` (`id`),
  CONSTRAINT `FKsav1gx8jdxosgclf3ta6rtolw` FOREIGN KEY (`inspect_apply_id`) REFERENCES `domain_apply` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_inspect_item` */

CREATE TABLE `domain_inspect_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `code` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7b1ia1gci8xoke6rr3vdgu8i5` (`charge_item_id`),
  CONSTRAINT `FK7b1ia1gci8xoke6rr3vdgu8i5` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_inspect_result` */

CREATE TABLE `domain_inspect_result` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `result` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `inspect_apply_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `inspect_apply_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `inspect_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh1d8yvtb8jf4b5st6oifdg26v` (`inspect_apply_id`),
  KEY `FK4y4n2vd697s59wd1akjd1h2h6` (`inspect_apply_item_id`),
  KEY `FKjryttwfi2ekkd46v9hkxq15nf` (`inspect_dept_id`),
  KEY `FKof44rnfu8q99a3aklpnmchhb5` (`visit_id`),
  CONSTRAINT `FK4y4n2vd697s59wd1akjd1h2h6` FOREIGN KEY (`inspect_apply_item_id`) REFERENCES `domain_inspect_apply_item` (`id`),
  CONSTRAINT `FKh1d8yvtb8jf4b5st6oifdg26v` FOREIGN KEY (`inspect_apply_id`) REFERENCES `domain_apply` (`id`),
  CONSTRAINT `FKjryttwfi2ekkd46v9hkxq15nf` FOREIGN KEY (`inspect_dept_id`) REFERENCES `domain_unit` (`id`),
  CONSTRAINT `FKof44rnfu8q99a3aklpnmchhb5` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_medical_record_clip` */

CREATE TABLE `domain_medical_record_clip` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `state` varchar(32) COLLATE utf8_bin NOT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK62ax1idgg7ea9yu773p4tcclx` (`visit_id`),
  CONSTRAINT `FK62ax1idgg7ea9yu773p4tcclx` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order` */

CREATE TABLE `domain_order` (
  `DTYPE` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `count` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `last_execute_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(128) COLLATE utf8_bin NOT NULL,
  `plan_start_date` datetime NOT NULL,
  `state` varchar(32) COLLATE utf8_bin NOT NULL,
  `state_desc` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `plan_end_date` datetime DEFAULT NULL,
  `belong_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `compsite_order_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `creator_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `execute_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `frequency_type_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrgwgh8koglhsaiqak9mkxlkfe` (`belong_dept_id`),
  KEY `FKrnshg3c00i6x62hmwv5w2mcaj` (`compsite_order_id`),
  KEY `FK18g5153ega9mhj2lirwy0ao8s` (`creator_id`),
  KEY `FKgufmlwh8cml7kaqng0cu3qkkc` (`execute_dept_id`),
  KEY `FKfubo6tl05053on1owj3j3uxl3` (`visit_id`),
  KEY `FKahjcyqmcn0p3gh7lfgbc1ghch` (`frequency_type_id`),
  CONSTRAINT `FK18g5153ega9mhj2lirwy0ao8s` FOREIGN KEY (`creator_id`) REFERENCES `domain_user` (`id`),
  CONSTRAINT `FKahjcyqmcn0p3gh7lfgbc1ghch` FOREIGN KEY (`frequency_type_id`) REFERENCES `domain_order_frequency_type` (`id`),
  CONSTRAINT `FKfubo6tl05053on1owj3j3uxl3` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKgufmlwh8cml7kaqng0cu3qkkc` FOREIGN KEY (`execute_dept_id`) REFERENCES `domain_unit` (`id`),
  CONSTRAINT `FKrgwgh8koglhsaiqak9mkxlkfe` FOREIGN KEY (`belong_dept_id`) REFERENCES `domain_unit` (`id`),
  CONSTRAINT `FKrnshg3c00i6x62hmwv5w2mcaj` FOREIGN KEY (`compsite_order_id`) REFERENCES `domain_order_team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_execute` */

CREATE TABLE `domain_order_execute` (
  `DTYPE` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `charge_state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `cost_state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `is_last` bit(1) DEFAULT NULL,
  `next_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `plan_end_date` datetime DEFAULT NULL,
  `plan_start_date` datetime DEFAULT NULL,
  `previous_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `send_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin NOT NULL,
  `team_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `type` varchar(32) COLLATE utf8_bin NOT NULL,
  `actual_executor_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `belong_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `compsite_order_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `execute_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `role_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `order_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `inspect_apply_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `drug_type_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtejpa5xo4q3150lvolwix1et8` (`actual_executor_id`),
  KEY `FKgb045joshe2vqq9x68rg0y3j4` (`belong_dept_id`),
  KEY `FK9iiq46wr3xext5vdker6pqfm1` (`compsite_order_id`),
  KEY `FKq777wfkvkw2x6d5v0jjqyjm57` (`execute_dept_id`),
  KEY `FK49jch1qt2v11mx0df9qc50bik` (`role_id`),
  KEY `FKsr8s2hdc8p718gbwont4ig65` (`order_id`),
  KEY `FKdlkmr6k3dlhuyann8era05nd` (`visit_id`),
  KEY `FK5clr298pdt6kxg4oad61csrio` (`inspect_apply_item_id`),
  KEY `FKbym9c34i1ktmfgkh3p8859kco` (`drug_type_id`),
  CONSTRAINT `FK49jch1qt2v11mx0df9qc50bik` FOREIGN KEY (`role_id`) REFERENCES `domain_role` (`id`),
  CONSTRAINT `FK5clr298pdt6kxg4oad61csrio` FOREIGN KEY (`inspect_apply_item_id`) REFERENCES `domain_inspect_apply_item` (`id`),
  CONSTRAINT `FK9iiq46wr3xext5vdker6pqfm1` FOREIGN KEY (`compsite_order_id`) REFERENCES `domain_order_team` (`id`),
  CONSTRAINT `FKbym9c34i1ktmfgkh3p8859kco` FOREIGN KEY (`drug_type_id`) REFERENCES `domain_drug_type` (`id`),
  CONSTRAINT `FKdlkmr6k3dlhuyann8era05nd` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKgb045joshe2vqq9x68rg0y3j4` FOREIGN KEY (`belong_dept_id`) REFERENCES `domain_unit` (`id`),
  CONSTRAINT `FKq777wfkvkw2x6d5v0jjqyjm57` FOREIGN KEY (`execute_dept_id`) REFERENCES `domain_unit` (`id`),
  CONSTRAINT `FKsr8s2hdc8p718gbwont4ig65` FOREIGN KEY (`order_id`) REFERENCES `domain_order` (`id`),
  CONSTRAINT `FKtejpa5xo4q3150lvolwix1et8` FOREIGN KEY (`actual_executor_id`) REFERENCES `domain_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_execute_charge_item` */

CREATE TABLE `domain_order_execute_charge_item` (
  `order_execute_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin NOT NULL,
  KEY `FKj75hagbw5090mfomvqya66oln` (`charge_item_id`),
  KEY `FKe624j8i8x2bbllu038sxvj9vb` (`order_execute_id`),
  CONSTRAINT `FKe624j8i8x2bbllu038sxvj9vb` FOREIGN KEY (`order_execute_id`) REFERENCES `domain_order_execute` (`id`),
  CONSTRAINT `FKj75hagbw5090mfomvqya66oln` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_frequency_type` */

CREATE TABLE `domain_order_frequency_type` (
  `DTYPE` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `code` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_team` */

CREATE TABLE `domain_order_team` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_type` */

CREATE TABLE `domain_order_type` (
  `DTYPE` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `code` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(64) COLLATE utf8_bin NOT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `drug_type_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsw6iguii3aau3790gpclcx9v4` (`charge_item_id`),
  KEY `FK98qj8o4mygc8ej0u8jyuil8gq` (`parent_id`),
  KEY `FK36eoe1612eeyojwfdtvw8atsu` (`drug_type_id`),
  CONSTRAINT `FK36eoe1612eeyojwfdtvw8atsu` FOREIGN KEY (`drug_type_id`) REFERENCES `domain_drug_type` (`id`),
  CONSTRAINT `FK98qj8o4mygc8ej0u8jyuil8gq` FOREIGN KEY (`parent_id`) REFERENCES `domain_order_type` (`id`),
  CONSTRAINT `FKsw6iguii3aau3790gpclcx9v4` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_type_app` */

CREATE TABLE `domain_order_type_app` (
  `DTYPE` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `order_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `order_type_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `drug_use_mode_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm5rkc42mea9345reqf1xnumif` (`order_id`),
  KEY `FKaoyhcfc5sxo7o0kl6vba9ac80` (`order_type_id`),
  KEY `FKjy9jv2lgrbjhdr38781458h7l` (`drug_use_mode_id`),
  CONSTRAINT `FKaoyhcfc5sxo7o0kl6vba9ac80` FOREIGN KEY (`order_type_id`) REFERENCES `domain_order_type` (`id`),
  CONSTRAINT `FKjy9jv2lgrbjhdr38781458h7l` FOREIGN KEY (`drug_use_mode_id`) REFERENCES `domain_drug_use_mode` (`id`),
  CONSTRAINT `FKm5rkc42mea9345reqf1xnumif` FOREIGN KEY (`order_id`) REFERENCES `domain_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_role` */

CREATE TABLE `domain_role` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_unit` */

CREATE TABLE `domain_unit` (
  `DTYPE` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL,
  `parent_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf8q3kxh804rqhpjtuccn2craa` (`parent_id`),
  CONSTRAINT `FKf8q3kxh804rqhpjtuccn2craa` FOREIGN KEY (`parent_id`) REFERENCES `domain_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_user` */

CREATE TABLE `domain_user` (
  `DTYPE` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL,
  `dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `superior_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb5wuk20apx80uc6v4vxme655` (`dept_id`),
  KEY `FKf628ap63yh4lifm2kfevnx24p` (`superior_id`),
  CONSTRAINT `FKb5wuk20apx80uc6v4vxme655` FOREIGN KEY (`dept_id`) REFERENCES `domain_unit` (`id`),
  CONSTRAINT `FKf628ap63yh4lifm2kfevnx24p` FOREIGN KEY (`superior_id`) REFERENCES `domain_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_user_role` */

CREATE TABLE `domain_user_role` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `role_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `user_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeleg05koj3m9fpgyiu84vc3ln` (`role_id`),
  KEY `FK22axxnp5vdplnwv8xv0tk7vv3` (`user_id`),
  CONSTRAINT `FK22axxnp5vdplnwv8xv0tk7vv3` FOREIGN KEY (`user_id`) REFERENCES `domain_user` (`id`),
  CONSTRAINT `FKeleg05koj3m9fpgyiu84vc3ln` FOREIGN KEY (`role_id`) REFERENCES `domain_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_visit` */

CREATE TABLE `domain_visit` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `bed` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `into_ward_date` datetime DEFAULT NULL,
  `leave_ward_date` datetime DEFAULT NULL,
  `name` varchar(16) COLLATE utf8_bin NOT NULL,
  `plan_leave_ward_date` datetime DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin NOT NULL,
  `state_desc` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `doctor_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `nurse_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK22asjr9hp86rwc8p6n3rycxa` (`dept_id`),
  KEY `FK6whuhse6l942hic6jucof5af1` (`doctor_id`),
  KEY `FKm6o0m67g084bfbeh4whx077a8` (`nurse_id`),
  CONSTRAINT `FK22asjr9hp86rwc8p6n3rycxa` FOREIGN KEY (`dept_id`) REFERENCES `domain_unit` (`id`),
  CONSTRAINT `FK6whuhse6l942hic6jucof5af1` FOREIGN KEY (`doctor_id`) REFERENCES `domain_user` (`id`),
  CONSTRAINT `FKm6o0m67g084bfbeh4whx077a8` FOREIGN KEY (`nurse_id`) REFERENCES `domain_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_visit_charge_item` */

CREATE TABLE `domain_visit_charge_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin NOT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo5dqs6cs9mlrvdd1mfvdssycw` (`charge_item_id`),
  KEY `FK9brijcn7m49h5v9168u00wu2o` (`visit_id`),
  CONSTRAINT `FK9brijcn7m49h5v9168u00wu2o` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKo5dqs6cs9mlrvdd1mfvdssycw` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_visit_log` */

CREATE TABLE `domain_visit_log` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `info` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `operator_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqlrg29eobxb0ydf6aofrkp2t3` (`operator_id`),
  KEY `FK7t1buefu3yv1jhkxchsiwpaum` (`visit_id`),
  CONSTRAINT `FK7t1buefu3yv1jhkxchsiwpaum` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKqlrg29eobxb0ydf6aofrkp2t3` FOREIGN KEY (`operator_id`) REFERENCES `domain_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
