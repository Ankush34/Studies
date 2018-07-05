<?php
$customer_email = $_GET["customer_email"];
$product_id = $_GET["product_id"];
$conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die(mysqli_error($conn));
$db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error("unable to select database"));
$query = "select * from khurana_sales_stock where product_id = $product_id";;
$result = mysqli_query($conn,$query) or die(mysqli_error($conn));
if(mysqli_num_rows($result)>0)
{
 $row =  mysqli_fetch_assoc($result);
 $name = $row["Name"];
 $query2 = "select * from khurana_sales_orders where sales_order_customer_email = '$customer_email' and sales_order_product_name = '$name' and sales_order_status = 'VIEWED'";
$result = mysqli_query($conn,$query2) or die(mysqli_error($conn));
if(mysqli_num_rows($result)>0)
{
$row = mysqli_fetch_assoc($result);
$ordered_count = $row["sales_order_quantity"];
$updatable_count = $ordered_count - 1;
$query3 = "update khurana_sales_orders set sales_order_quantity = $updatable_count where sales_order_customer_email = '$customer_email' and sales_order_product_name = '$name' and sales_order_status = 'VIEWED' ";
$result = mysqli_query($conn,$query3) or die(mysqli_error($conn));
if($result){
   $response = array();
   $response["response"] = "success";
   echo json_encode([$response]);
    }
else{
     $response = array();
   $response["response"] = "failure";
   echo json_encode([$response]);
  }
  
}
}
?>