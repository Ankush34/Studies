<?php
$response = array();
$response1=array();
$product2 = array();
$email = $_GET["email"];
$query = "select * from khurana_sales_access where user_email = '$email'";
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$result = mysqli_query($conn,$query);
if (mysqli_num_rows($result)>0) {
    $row = mysqli_fetch_assoc($result);
    $response["user_id"] = $row["user_id"];
    $response["user_name"] = $row["user_name"];
    $response["user_shop_name"] = $row["user_shop_name"];
    $response["user_shop_address"] = $row["user_shop_address"];
    $response["user_gst"] = $row["user_gst"];
    $response["user_phone"] = $row["user_phone"];
    array_push($response1,$response);
    echo json_encode($response1);
  }else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
    // echo no users JSON
    echo json_encode($response);}

?>