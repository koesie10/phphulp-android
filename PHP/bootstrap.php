<?php
/*
 * Copyright (C) 2013 Koen Vlaswinkel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
header('Content-Type: application/json');
@ob_start();
set_exception_handler(function($exception) {
	showError($exception->getMessage());
	exit();
});

function showError($message) {
	@ob_end_clean();
	header($_SERVER['SERVER_PROTOCOL'] . ' 500 Internal Server Error', true, 500);
	echo json_encode(array('error' => $message));
}

$dbh = new PDO('mysql:host=localhost;dbname=android_tut', 'android_tut', 'android_tut');
$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);