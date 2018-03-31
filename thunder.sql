CREATE TABLE `counter` (
  `id` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `links` (
  `id` int(11) NOT NULL,
  `link` varchar(10000) NOT NULL,
  `countdown` int(11) NOT NULL DEFAULT '0',
  `lastchanged` int(11) NOT NULL DEFAULT '0',
  `changed` int(1) NOT NULL DEFAULT '0',
  `visited` int(5) NOT NULL DEFAULT '-1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




create table indexer(
	id int unsigned NOT NULL auto_increment, 
	stem varchar(50) not null , 
	link varchar(180) not null, 
	total int unsigned not null , 
	original varchar(50) not null , 
	tag varchar(20) not null , 
	position int unsigned not null , 
	primary key (id) 
);


ALTER TABLE `counter`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_id` (`id`);

ALTER TABLE `links`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `links`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1611;
COMMIT;

