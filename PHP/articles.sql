CREATE TABLE IF NOT EXISTS `articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 NOT NULL,
  `content` text CHARACTER SET utf8 NOT NULL,
  `slug` varchar(20) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `articles` (`id`, `title`, `content`, `slug`) VALUES
(1, 'Lorem ipsum', 'Lorem ipsum dolor sir amet ', 'lorem-ipsum'),
(2, 'Welkom op mijn blog!', 'Beetje laat, eigenlijk was het vorige artikel te vroeg...', 'welkom');