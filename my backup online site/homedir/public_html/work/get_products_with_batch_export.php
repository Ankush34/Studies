<?php

$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$data = json_decode(file_get_contents('php://input'), true);
$array = $data["brands"];
$data = array();
foreach($array as $data_parsed)
{
$brand = $data_parsed["brand"];
$type =  $data_parsed["type"];
if($type == "Mobile")
{
$query = "SELECT * FROM khurana_sales_stock where Brand = '".$brand."' AND Name LIKE '%Mobile%'";
}
else
{
$query = "SELECT * FROM khurana_sales_stock where Brand = '".$brand."' AND Name NOT LIKE '%Mobile%' AND Name NOT LIKE '%Tab%'";
}
$result = mysqli_query($conn, $query);
while($row_product_details = mysqli_fetch_array($result))
{
$batch_id = $row_product_details["BatchId"];
  $query_batch = "select * from batch_numbers where id= $batch_id";
  $result_batch = mysqli_query($conn, $query_batch);
  $row_batch_info = mysqli_fetch_assoc($result_batch);
 $product = array();
    $product_id = $row_product_details["product_id"];
   $product["Name"] = $row_product_details["Name"];
   $product["stock"] = $row_product_details["Stock"];
   $product["mrp"] = $row_product_details["Price_MRP"];
   $product["mop"] = $row_product_details["Price_MOP"];
   $product["hsn"] = $row_product_details["Product_HSN"];
   $product["ksprice"] = $row_product_details["Price_KS"];
   $product["product_id"] = $row_product_details["product_id"];
   $product["tax"] = $row_product_details["product_tax_percent"];
   foreach ($row_batch_info as $key => $value) {
   if($value == null && $key != "id" && $key != "product_id")
   {
   $batch[$key] = "";
   }
   else if($value != null && $key != "id" && $key != "product_id")
   {
    $batch[$key] = $value;
   }
  }
  $product["batch"] = $batch;
  array_push($data, $product);
}

 }
 $response["response"] = $data;
$response["success"] = "true";
echo json_encode($response);
?>