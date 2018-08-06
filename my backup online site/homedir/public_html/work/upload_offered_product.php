<?php
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");

$inserted_id = 0;
$offer_details_uploaded = false;
$product_id = $_GET["product_id"];
$offer_type = $_GET["offer_type"];
$customer_email = $_GET['customer_email'];
$product_ordered_quantity = $_GET["count"];
$date_of_product = $_GET["date"];
$item_count = 0;
$product_info_query = "select * from khurana_sales_stock where product_id =".$product_id." ";
$product_info = mysqli_query($conn,$product_info_query);
$info_row = mysqli_fetch_assoc($product_info);
$name = $info_row["Name"];
$price_mop = $info_row["Price_MOP"];
$price_mrp = $info_row["Price_MRP"];
$price_ks = $info_row["Price_KS"]; 

if($offer_type == "Combo")
{
  $discount_offered = $_GET["discount_offered"];
  $sql_insert_offers = "insert into offer_desc_cart_product(offer_type, discount_amount) values('Combo Offer', '$discount_offered')";
  $result = mysqli_query($conn, $sql_insert_offers);
  $inserted_id = mysqli_insert_id($conn);
  $offer_details_uploaded = true; 
}

if($offer_type == "Super")
{
  $discount_offered = $_GET["discount_offered"];
  $item_count = $_GET["item_count"];
  $sql_insert_offers = "insert into offer_desc_cart_product(offer_type, discount_amount, item_count) values('Super Value Offer', '$discount_offered', '$item_count')";
  $result = mysqli_query($conn, $sql_insert_offers);
  $inserted_id = mysqli_insert_id($conn);
    $offer_details_uploaded = true;
    

}

if($offer_type == "Discount")
{

  $discount_offered = $_GET["discount_offered"];
  $sql_insert_offers = "insert into offer_desc_cart_product(offer_type, discount_amount) values('Dicount Offer', '$discount_offered')";
  $result = mysqli_query($conn, $sql_insert_offers);
  $inserted_id = mysqli_insert_id($conn);
    $offer_details_uploaded = true;

  
}
if($offer_details_uploaded == true)
{
$we_need_to_update = false;

if($offer_type == "Discount")
{
  $sql_check_in_cart = "select * from khurana_sales_orders where sales_order_product_name = '$name' AND sales_order_customer_email = '$customer_email' ";
  $result = mysqli_query($conn, $sql_check_in_cart);
  if(mysqli_num_rows($result)>0)
  {
    $we_need_to_update = true;
  }
}
if($offer_type == "Super")
{
  $sql_check_in_cart = "select * from khurana_sales_orders where sales_order_product_name = '$name' AND sales_order_customer_email = '$customer_email' ";
  $result_check_in_cart = mysqli_query($conn, $sql_check_in_cart);
  $count_rows = mysqli_num_rows($result_check_in_cart);
  if($count_rows == 0)
  {
    $insert_query = "INSERT INTO khurana_sales_orders (sales_order_date,sales_order_customer_email,sales_order_product_name,sales_order_price,sales_order_quantity,sales_order_status, 
   offer_description_id) VALUES ('$date_of_product','$customer_email','$name',$price_mop,$item_count,'VIEWED',$inserted_id)";
   $result_insert  = mysqli_query($conn, $insert_query);
   if($result_insert)
   {
        $result =array();
	$result["success"] = true;
	$data = array();
	array_push($data, $result);
	echo json_encode($data);
   }
   else
   {
        $result =array();
	$result["success"] = false;
	$data = array();
	array_push($data, $result);
	echo json_encode($data);
   }
  }
  else if($count_rows == 1)
  {
     $row_in_cart  = mysqli_fetch_assoc($result_check_in_cart);
     $count_of_product_in_cart = $row_in_cart["sales_order_quantity"];
     if($count_of_product_in_cart < $item_count )
     {
       $sql_update_to_offers = "update khurana_sales_orders set sales_order_quantity = $item_count, offer_description_id = '$inserted_id' Where sales_order_product_name = '$name' AND sales_order_customer_email = '$customer_email'";
       $result_update_to_offers = mysqli_query($conn, $sql_update_to_offers);
       if($result_update_to_offers)
       {
        $result =array();
	$result["success"] = true;
	$data = array();
	array_push($data, $result);
	echo json_encode($data);
       }
     }
     else if(($count_of_product_in_cart % $item_count) == 0 )
     {
        $sql_to_update = "update khurana_sales_orders set offer_description_id = '$inserted_id' where sales_order_product_name = '$name' AND sales_order_customer_email = '$customer_email'";
        $result_update = mysqli_query($conn, $sql_to_update);
        if($result_update)
        {
         $result =array(); 
	 $result["success"] = true;
	 $data = array();
	 array_push($data, $result);
	 echo json_encode($data);
        }
     }
     else
     {
       $count_extra =  $count_of_product_in_cart - $item_count;
       $update_query = "update khurana_sales_orders set sales_order_quantity = $item_count, offer_description_id = '$inserted_id' where sales_order_product_name = '$name' AND sales_order_customer_email = '$customer_email'";
       $result_of_update = mysqli_query($conn, $update_query);
       $insert_query = "INSERT INTO khurana_sales_orders (sales_order_date,sales_order_customer_email,sales_order_product_name,sales_order_price,sales_order_quantity,sales_order_status) VALUES ('$date_of_product','$customer_email','$name',$price_mop,$count_extra,'VIEWED')";
       $result_insert_query = mysqli_query($conn, $insert_query);   
         if($result_insert_query)
         {    
		 $result = array();
		 $result["success"] = true;
		 $data = array();
		 array_push($data, $result);
		 echo json_encode($data);
	 }
     }
  }
  else if($count_rows == 2)
  {
    $ids = array();
    $i = 0;
    while($row = mysqli_fetch_array($result_check_in_cart))
    {
     if($row["offer_description_id"] == -1)
     {
       $sales_order_number = $row["sales_order_number"];
       $sql_delete = "delete from khurana_sales_orders where sales_order_number = $sales_order_number";
       $result_delete_query = mysqli_query($conn, $sql_delete);
     }
     else
     {
       $current_item_count = $row["sales_order_quantity"];
       $total_count = $current_item_count + $item_count;
       $update_query = "update khurana_sales_orders set sales_order_quantity = $total_count where sales_order_product_name = '$name' AND sales_order_customer_email = '$customer_email'";
       $update_query_result = mysqli_query($conn, $update_query);
       
     }
    }
         $result = array(); 
	 $result["success"] = false;
	 $data = array();
	 array_push($data, $result);
	 echo json_encode($data);
  }
  

}
$sql = "";
if($offer_type != "Super")
{
if($we_need_to_update == true)
{
 $sql = "update khurana_sales_orders set offer_description_id = $inserted_id where sales_order_product_name = '$name' AND sales_order_customer_email = '$customer_email'";
}
else
{
   $sql = "INSERT INTO khurana_sales_orders (sales_order_date,sales_order_customer_email,sales_order_product_name,sales_order_price,sales_order_quantity,sales_order_status, 
   offer_description_id) VALUES ('$date_of_product','$customer_email','$name',$price_mop,$product_ordered_quantity,'VIEWED',$inserted_id)";
}
$result = mysqli_query($conn,$sql)or die(mysqli_error($conn));
if($result)
{
$result= array();
$result["success"] = true;
$data = array();
array_push($data, $result);
echo json_encode($data);
}
else
{
$result =array();
$result["success"] = false;
$data = array();
array_push($data, $result);
echo json_encode($data);
}
}
}
else
{
$result = array();
$result["success"] = false;
$data = array();
array_push($data, $result);
echo json_encode($data);
}

?>