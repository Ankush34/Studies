<?php
$response1 = array();
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$promoters = array();
 $result = array();
 $content = trim(file_get_contents("php://input"));
 $decoded = json_decode($content, true);
 $date = $decoded["date"][0];
 $promoters = $decoded["promoters"];
 $newarray = implode(", ",$promoters);
 foreach ($promoters as $promoter)
 {
  $query2 = "select * from khurana_sales_orders where sales_order_customer_email = '$promoter' AND sales_order_date = '$date'";
  $result = mysqli_query($conn,$query2);
  if(mysqli_num_rows($result) > 0)
   {
   $query_user = "select user_name , user_id, user_phone from khurana_sales_access where user_email = '$promoter'";
   $user = mysqli_query($conn,$query_user) or die(mysqli_error());
  	$user_details = array();
   if(mysqli_num_rows($user) > 0)
   {
     $row = mysqli_fetch_array($user);
   	$user_details["name"] = $row["user_name"];
   	$user_details["user_id"] = $row["user_id"];
   	$user_details["user_phone"] = $row["user_phone"];
   }
   $response1[$promoter] = array();
   while($row = mysqli_fetch_array($result))
   {
   $products_per_promoter  = array();
   		$products_per_promoter["promoter_name"] = $user_details["name"];
   		$products_per_promoter["promoter_id"] = $user_details["user_id"];
   		$products_per_promoter["promoter_contact"] = $user_details["user_phone"];
       $products_per_promoter["name"] = $row["sales_order_product_name"];
       $products_per_promoter["price"] = $row["sales_order_price"];
       $products_per_promoter["order_type"] = $row["sales_order_type"];
       $products_per_promoter["customer_name"] = $row["sales_order_end_user_name"];
       $products_per_promoter["customer_contact"] = $row["sales_order_end_user_phone"];
       $products_per_promoter["order_status"] = $row["sales_order_status"];
       $products_per_promoter["quantity"] = $row["sales_order_quantity"];
       $products_per_promoter["invoice_status"] = $row["invoice_status"];
    array_push($response1[$promoter],$products_per_promoter);
    }
  }
   }
   echo json_encode($response1) ;
 
?>