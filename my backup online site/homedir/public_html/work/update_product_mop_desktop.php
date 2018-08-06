<?php
$conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error());
$data = json_decode(file_get_contents('php://input'), true);
$array = $data["update"];
foreach($array as $data)
{
 $product_name = $data["name"];
 $product_price = $data["mop"];
 $sql_update = "update khurana_sales_stock set Price_MOP = $product_price where Name = '$product_name'";
 $update_result = mysqli_query($conn, $sql_update);
 if($update_result)
 {
   $response["status"] = "success";
   $data = array();
   array_push($data, $response);
   echo json_encode($data);
  }
 else
 {
  $response["status"] = "failure"; 
  $data = array();
  array_push($data, $response);
  echo json_encode($data);
  }
}
?>