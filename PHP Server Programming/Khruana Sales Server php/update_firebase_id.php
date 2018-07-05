<?php
 
/*
 * Following code will update all the users
 */
 $response = array();
$response1=array();
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$token = isset($_GET['token']) && $_GET['token'] != '' ? $_GET['token'] : "no";
$customer_id = isset($_GET['customer_id']) && $_GET['customer_id'] != '' ? $_GET['customer_id'] : "no";
$product = array(); 
$query = "update khurana_sales_access set firebase_id = '$token' where user_id = '$customer_id' ";
$result = mysqli_query($conn, $query) or die(mysqli_error($conn));
if ($result) {
        $product2["success"] = true;
        array_push($response1,$product2);
	echo json_encode($response1);
}
else {
    $response["success"] = 0;
    $response["message"] = "Could Not Update";
    echo json_encode($response);
}
?>



