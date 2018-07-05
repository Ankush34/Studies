<?php
 
/*
 * Following code will update all the users
 */
 $response = array();
$response1=array();
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$order_number = isset($_GET['order_number']) && $_GET['order_number'] != -1 ? $_GET['order_number'] : -1;
$order_status = isset($_GET['order_status']) && $_GET['order_status'] != '' ? $_GET['order_status'] : "ORDERED";
$product = array(); 
$query = "update khurana_sales_orders set sales_order_status = '$order_status' where sales_order_number = '$order_number' ";
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



