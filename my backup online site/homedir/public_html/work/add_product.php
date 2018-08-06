<?php  
$con = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$response = array();
$response1=array();
$product = array();
$db = mysqli_select_db($con,"KhuranaSalesRastapeth") or die(mysqli_error());
if($_SERVER['REQUEST_METHOD']=='GET'){
$customer_email = $_GET['customer_email'];
$product_id = $_GET["id"];
$product_ordered_quantity = $_GET["count"];
$date_of_product = $_GET["date"];

$product_info_query = "select * from khurana_sales_stock where product_id =".$product_id." ";
$product_info = mysqli_query($con,$product_info_query);
$info_row = mysqli_fetch_assoc($product_info);
$name = $info_row["Name"];



$sql_check_count_rows = "select * from khurana_sales_orders where sales_order_product_name = '$name' And sales_order_customer_email ='$customer_email' AND sales_order_status ='VIEWED' ";
$sql_result_check_rows = mysqli_query($con, $sql_check_count_rows);
$count_result_check_rows = mysqli_num_rows($sql_result_check_rows);
if($count_result_check_rows  == 2)
{
 $row_second  = "";
 $offer_description = "";
 $row_first = "";
while($row = mysqli_fetch_array($sql_result_check_rows))
{
   if($row["offer_description_id"] == -1)
  {
    $row_second = $row;
  }
  else
  {
   $offer_description_id = $row["offer_description_id"];
   $sql = "select * from offer_desc_cart_product where offer_id = $offer_description_id";
   $result_offer = mysqli_query($con, $sql);
   $offer_description = mysqli_fetch_assoc($result_offer);
   $row_first =$row;
  }
}

 $item_count_required_for_offer = $offer_description["item_count"];
 $item_currently_of_product = $row_second["sales_order_quantity"];
 if((($product_ordered_quantity + $item_currently_of_product) % $item_count_required_for_offer) == 0)
 {
   $id_to_delete = $row_second["sales_order_number"];
   $sql_delete = "delete from khurana_sales_orders where sales_order_number = $id_to_delete";
   $result_delete = mysqli_query($con, $sql_delete);
   
   $id_to_update = $row_first["sales_order_number"];
   $count_to_update = $row_first["sales_order_quantity"] + $product_ordered_quantity + $row_second["sales_order_quantity"] ; 
   $sql_update = "update khurana_sales_orders set sales_order_quantity = $count_to_update where sales_order_number = $id_to_update";
   $result_update = mysqli_query($con, $sql_update);
   if($result_update)
   {
   $product2["success"] ="true";
   array_push($response1,$product2);
   echo json_encode($response1);
   }
   else
   {
   $product2["success"] ="false";
   array_push($response1,$product2);
    echo json_encode($response1);
   }
 }
 else
 {
  $count_to_update = $row_second["sales_order_quantity"] + $product_ordered_quantity ; 
  $id_to_update = $row_second["sales_order_number"];
   $sql_update = "update khurana_sales_orders set sales_order_quantity = $count_to_update where sales_order_number = $id_to_update";
   $result_update = mysqli_query($con, $sql_update);
   if($result_update)
   {
   $product2["success"] ="true";
   array_push($response1,$product2);
   echo json_encode($response1);
   }
   else
   {
   $product2["success"] ="false";
   array_push($response1,$product2);
   echo json_encode($response1);
   }
 }
 exit(0);
}



$query2="select sales_order_quantity as total from khurana_sales_orders where sales_order_product_name = '$name' And sales_order_customer_email ='$customer_email' AND sales_order_status ='VIEWED' " ;
$result3=mysqli_query($con,$query2)or die(mysql_error());
$data=mysqli_fetch_assoc($result3);
$count=$data['total'];
if($count<1)
{
$product_info_query = "select * from khurana_sales_stock where product_id =".$product_id." ";
$product_info = mysqli_query($con,$product_info_query);
$info_row = mysqli_fetch_assoc($product_info);
$name = $info_row["Name"];
$price_mop = $info_row["Price_MOP"];
$price_mrp = $info_row["Price_MRP"];
$price_ks = $info_row["Price_KS"]; 
$sql = "INSERT INTO khurana_sales_orders (sales_order_date,sales_order_customer_email,sales_order_product_name,sales_order_price,sales_order_quantity,sales_order_status) VALUES ('$date_of_product','$customer_email','$name',$price_mop,$product_ordered_quantity,'VIEWED')";
mysqli_query($con,$sql)or die(mysqli_error($con));
$product2["success"] ="true";
array_push($response1,$product2);
echo json_encode($response1);

}
else
{
$product_info_query = "select * from khurana_sales_stock where product_id =".$product_id." ";
$product_info = mysqli_query($con,$product_info_query);
$info_row = mysqli_fetch_assoc($product_info);
$name_for_product_info = $info_row["Name"];

$sql = "select * from khurana_sales_orders where sales_order_product_name = '$name_for_product_info' AND sales_order_customer_email = '$customer_email' AND sales_order_status = 'VIEWED' ";
$product_info_for_offer_description_result = mysqli_query($con, $sql);
$row_product_info_for_offer_description = mysqli_fetch_assoc($product_info_for_offer_description_result);
$offer_description_id = $row_product_info_for_offer_description["offer_description_id"];

if($offer_description_id != -1)
{
  $sql_get_offer_info  = "select * from offer_desc_cart_product where offer_id = $offer_description_id";
  $result_offer = mysqli_query($con, $sql_get_offer_info);
  $row_offer = mysqli_fetch_assoc($result_offer);
  $type_offer = $row_offer["offer_type"];
  if($type_offer == "Super Value Offer")
  {
    $item_count_for_offer = $row_offer["item_count"];
    
    if( ( ($count + $product_ordered_quantity) % $item_count_for_offer ) == 0)
    {
     $product_name = $info_row["Name"];
    $total = $count + $product_ordered_quantity;
     $sql_update="update khurana_sales_orders set sales_order_quantity = '$total' where sales_order_customer_email='$customer_email' and sales_order_product_name = '$product_name' and sales_order_status='VIEWED' ";
     $result_update = mysqli_query($con, $sql_update);
     if($result_update)
     {
        $product2["success"] ="true";
        array_push($response1,$product2);
        echo json_encode($response1);
     }
    }
    else 
    {
    $name = $info_row["Name"];
    $price_mop = $info_row["Price_MOP"];
    $price_mrp = $info_row["Price_MRP"];
    $price_ks = $info_row["Price_KS"]; 
      $sql_insert = "INSERT INTO khurana_sales_orders (sales_order_date,sales_order_customer_email,sales_order_product_name,sales_order_price,sales_order_quantity,sales_order_status) VALUES ('$date_of_product','$customer_email','$name',$price_mop,$product_ordered_quantity,'VIEWED')";
      $result_sql_insert = mysqli_query($con, $sql_insert);
      if($result_sql_insert)
      {
        $product2["success"] ="true";
        array_push($response1,$product2);
        echo json_encode($response1);
      }
    }
  }
  else
  {
  $product_name = $info_row["Name"];
  $total=$count + $product_ordered_quantity;
  $sql2="update khurana_sales_orders set sales_order_quantity = '$total' where sales_order_customer_email='$customer_email' and sales_order_product_name = '$product_name' and 
  sales_order_status='VIEWED' ";
  $success = mysqli_query($con,$sql2)or die(mysqli_error($con));
  $product2["success"] ="true";
       array_push($response1,$product2);
       echo json_encode($response1);
  }
}
else
{
$product_name = $info_row["Name"];
$total=$count + $product_ordered_quantity;
$sql2="update khurana_sales_orders set sales_order_quantity = '$total' where sales_order_customer_email='$customer_email' and sales_order_product_name = '$product_name' and sales_order_status='VIEWED' ";
$success = mysqli_query($con,$sql2)or die(mysqli_error($con));
$product2["success"] ="true";
        array_push($response1,$product2);
       echo json_encode($response1);

}
}
 }
else{
$product2["success"] ="false";
        array_push($response1,$product2);
       echo json_encode($response1);
}

?>



