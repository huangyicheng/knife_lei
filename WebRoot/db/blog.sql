
--
-- power by knife using jfinal for java  knife
--
CREATE DATABASE 'knife' DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kf_user`;

/*用户表*/
CREATE TABLE `kf_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL,
  `password` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
insert into kf_user values('','knife','319504');
/*标签表*/
DROP TABLE IF EXISTS `kf_tags`;

/*文章表*/
DROP TABLE IF EXISTS `kf_article`;
CREATE TABLE 'kf_article'(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `createDateTime` datetime NOT NULL,
  `tags` varchar(300) NOT NULL,
)ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;