<?php

$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");

$query = "select * from batch_numbers where product_id > 4055 ";
$result = mysqli_query($conn, $query);
$data = array();
if(mysqli_num_rows($result) > 0)
{
 while($row = mysqli_fetch_array($result))
 {
    $product = array();
    $product_id = $row["product_id"];
   $query_product = "select Name from khurana_sales_stock where product_id = $product_id";
   $product_result = mysqli_query($conn, $query_product);
   $row_product = mysqli_fetch_assoc($product_result);
   $product["name"] = $row_product["Name"];
  
   foreach ($row as $key => $value) {
   if($value == null)
   {
   $product[$key] = "";
   }
   else
   {
    $product[$key] = $value;
   
   }
    }
    array_push($data, $product);
    
}
echo json_encode($data);
 }
?>