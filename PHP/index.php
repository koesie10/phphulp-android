<?php
require_once 'bootstrap.php';

$query = $dbh->query('SELECT id,title,content,slug FROM articles');

$data = array();
while ($row = $query->fetch(PDO::FETCH_ASSOC)) {
	$data[] = $row;
}

echo json_encode($data);