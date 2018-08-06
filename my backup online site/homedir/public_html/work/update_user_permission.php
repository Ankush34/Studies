<?php
 
/*
 * Following code will update all the users
 */
 $response = array();
$response1=array();
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$permission_type = isset($_GET['permission_type']) && $_GET['permission_type'] != '' ? $_GET['permission_type'] : "no";
$contact_number = isset($_GET['contact_number']) && $_GET['contact_number'] != '' ? $_GET['contact_number'] : "no";
$product = array(); 
$query = "update khurana_sales_access set user_type = '$permission_type' , permission = 'Yes' where user_phone = '$contact_number' ";
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



