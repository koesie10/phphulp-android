<?php
header('Content-Type: application/json');
@ob_start();
set_exception_handler(function($exception) {
	showError($exception->getMessage());
	exit();
});

function showError($message) {
	@ob_end_clean();
	echo json_encode(array('error' => $message));
}

$dbh = new PDO('mysql:host=localhost;dbname=android_tut', 'android_tut', 'android_tut');
$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);