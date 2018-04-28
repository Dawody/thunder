DROP DATABASE IF EXISTS thunder;

CREATE DATABASE thunder;

use thunder;

CREATE TABLE `counter` (
  `id` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `links` (
  `id` int(11) NOT NULL,
  `link` varchar(255) NOT NULL,
  `countdown` int(11) NOT NULL DEFAULT '0',
  `lastchanged` int(11) NOT NULL DEFAULT '0',
  `changed` int(1) NOT NULL DEFAULT '0',
  `visited` int(5) NOT NULL DEFAULT '-1',
  `out_links` int(11) NOT NULL DEFAULT '0',
  `score` DOUBLE NOT NULL DEFAULT '0.0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `queries` (
  `query` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table indexer(
	id int unsigned NOT NULL auto_increment, 
	stem varchar(1000) not null ,
	link varchar(255) not null,
	total int unsigned not null , 
	original varchar(50) not null , 
	tag varchar(20) not null , 
	position int unsigned not null , 
	primary key (id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



ALTER TABLE `counter`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_id` (`id`);

ALTER TABLE `links`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `links`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
COMMIT;
ALTER TABLE `links` ADD UNIQUE(`link`);

ALTER TABLE `queries`
  ADD UNIQUE KEY (`query`);

CREATE TABLE `in_out` ( 
`link1` varchar(255) NOT NULL , 
`link2` varchar(255) NOT NULL ,
 PRIMARY KEY (`link1`, `link2`) ,
 FOREIGN KEY (`link1`) REFERENCES links(`link`) ON DELETE CASCADE ON UPDATE CASCADE,
 FOREIGN KEY (`link2`) REFERENCES links(`link`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `in_out` ADD INDEX(`link1`);
ALTER TABLE `in_out` ADD INDEX(`link2`);


INSERT INTO `counter` (`id`) VALUES ('0');
INSERT INTO `links` (`id`, `link`, `countdown`, `lastchanged`, `changed`, `visited`) VALUES (NULL, 'https://www.geeksforgeeks.org/', '0', '0', '0', '-1');


