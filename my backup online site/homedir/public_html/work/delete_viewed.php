<?php
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$customer_email = $_GET["customer_email"];
$product_id =  $_GET["product_id"];
$query1 = "select * from khurana_sales_stock where product_id = $product_id";
$result = mysqli_query($conn,$query1) or die(mysqli_error($conn));
$row =  mysqli_fetch_assoc($result);
$name = $row["Name"];
$query2 = "delete from khurana_sales_orders where sales_order_customer_email = '$customer_email' and sales_order_product_name = '$name' and sales_order_status = 'VIEWED' ";
$result = mysqli_query($conn,$query2);
if($result)
{
$reponse = array();
$response["response"] = "success";
echo json_encode([$response]);
}
else
{
$reponse = array();
$response["response"] = "failure";
echo json_encode([$response]);
}
?>
 