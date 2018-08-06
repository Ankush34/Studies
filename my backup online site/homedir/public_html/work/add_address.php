<?php
 
/*
 * Following code will list all the products
 */
$con = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($con,"KhuranaSalesRastapeth") or die(mysqli_error());

$response = array();
$response1=array();
$product = array();
$address = $_GET['address'];
$customer_id = $_GET["customer_id"];
$query = "insert into khurana_sales_delivery_addresses(khurana_sales_customer_id, khurana_sales_customer_address) values($customer_id , '$address' )";
$result = mysqli_query($con,$query) or die(mysqli_error($con));
if ($result){
        $product2["success"] ="true";
        array_push($response1,$product2);
       echo json_encode($response1);
}
else {
    $response["success"] = false;
    $response["message"] = "No products found";
    echo json_encode($response);
}
?>




