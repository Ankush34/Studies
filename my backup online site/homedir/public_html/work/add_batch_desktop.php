<?php
$conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error());
$data_fetched = json_decode(file_get_contents('php://input'), true);
$array = $data_fetched["insert"];
foreach($array as $data)
{

$query  = "insert into batch_numbers ";
$product_id = $data["product_id"];
$pieces_avail = $data["pieces_available"];

$into_columns = "( product_id,";
foreach($data["columns"] as $key => $value)
{
  $into_columns = $into_columns."`".$value."`,";;
}
$into_columns = rtrim($into_columns,',');
$into_columns = $into_columns." )";

$into_values = "( '$product_id', ";
foreach($data["values"] as $key => $value)
{
  $into_values = $into_values."'".$value."', ";
}
$into_values = rtrim($into_values,', ');
$into_values = $into_values." )";

$query = $query."".$into_columns." values ".$into_values;
$result = mysqli_query($conn, $query) or die(mysqli_error($conn));
$inserted_id = mysqli_insert_id($conn);


$query_update = "update khurana_sales_stock set BatchId = '$inserted_id',Stock= '$pieces_avail' where product_id =  '$product_id'";
$result_update = mysqli_query($conn, $query_update);
if($result_update)
{
 $status["success"] = "true";
 $response = array();
 array_push($response, $status);
 echo json_encode($response);
}
else
{
 $status["success"] = "false";
 $response = array();
 array_push($response, $status);
 echo json_encode($response);
}
}
?>