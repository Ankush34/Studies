<?php
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$email = $_GET["email"];
$query = "select count(*) as total from khurana_sales_orders where sales_order_customer_email = '$email' and sales_order_status = 'VIEWED'";
$result = mysqli_query($conn,$query);
$row = mysqli_fetch_assoc($result);
$count = $row["total"];
$repsonse = array();
$response["total"] = $count;
echo json_encode([$response]);

?>