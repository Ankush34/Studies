<?php
$conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error());
$data = json_decode(file_get_contents('php://input'), true);
$array  = $data["insert"];
foreach($array as $data)
{
	$product_name = $data["product_name"];
	$product_stock = $data["product_stock"];
	$brand = $data["product_brand"];
	$sql_insert = "insert into khurana_sales_stock (Stock, Brand, Name) values ('$product_stock', '$brand', '$product_name')";
	$insert_result = mysqli_query($conn, $sql_insert);
	if($update_result)
	{
	  $response["status"] = "success";
	$data = array();
	array_push($data, $response);
	echo json_encode($data);
	}
	else
	{
	$response["status"] = "success";
	$data = array();
	array_push($data, $response);
	echo json_encode($data);
	}
}
?>