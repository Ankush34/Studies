<?php
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
 $content = trim(file_get_contents("php://input"));
 $decoded = json_decode($content, true);
 $selected_batch = $decoded["selected_batch"];
 $product_name = $decoded["product_name"];
 $email = $decoded["email"];

 $query =  "update khurana_sales_orders set batches_selected = '$selected_batch' where sales_order_product_name = '$product_name' AND sales_order_status = 'VIEWED' AND sales_order_customer_email = '$email' ";
 $result = mysqli_query($conn,$query);
 if($result)
 {
	$data["success"] = true;
 }
 else
 {
 $data["success"] = false;
 }
 echo json_encode($data);
?>