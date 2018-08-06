<?php
 
/*
 * Following code will list all the products
 */
 $response = array();
$response1=array();
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$order_date = $_GET["sales_date"];
$promoters = array();
$query = "SELECT DISTINCT sales_order_customer_email FROM khurana_sales_orders ";
$result = mysqli_query($conn, $query) or die(mysqli_error());
if (mysqli_num_rows($result)>0) {
    while ($row = mysqli_fetch_array($result)) {
        array_push($promoters,$row["sales_order_customer_email"]);
          }
 array_push($response,$promoters);
}
else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No promoters found";
 
    // echo no users JSON
    echo json_encode($response);
}
 
 if(count($promoters) > 0)
 {
 foreach ($promoters as $promoter)
 {
  $query2 = "select * from khurana_sales_orders where sales_order_customer_email = '$promoter' AND sales_order_date = '$order_date'";
  
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
   while($row = mysqli_fetch_array($result))
   {
   $products_per_promoter  = array();
   		$products_per_promoter[$promoter]["promoter_name"] = $user_details["name"];
   		$products_per_promoter[$promoter]["promoter_id"] = $user_details["user_id"];
   		$products_per_promoter[$promoter]["promoter_contact"] = $user_details["user_phone"];
       $products_per_promoter[$promoter]["name"] = $row["sales_order_product_name"];
       $products_per_promoter[$promoter]["price"] = $row["sales_order_price"];
       $products_per_promoter[$promoter]["order_type"] = $row["sales_order_type"];
       $products_per_promoter[$promoter]["customer_name"] = $row["sales_order_end_user_name"];
       $products_per_promoter[$promoter]["customer_contact"] = $row["sales_order_end_user_phone"];
       $products_per_promoter[$promoter]["order_status"] = $row["sales_order_status"];
       $products_per_promoter[$promoter]["quantity"] = $row["sales_order_quantity"];
       $products_per_promoter[$promoter]["invoice_status"] = $row["invoice_status"];
    array_push($response1,$products_per_promoter);
    }
  }
   }
   echo json_encode($response1) ;
   
 }
?>



