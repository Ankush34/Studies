<?php
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$data = json_decode(file_get_contents('php://input'), true);
$names = $data["parent"];
$final_response = array();
foreach($names as $name)
{
$query  = "select * from khurana_sales_stock where Name = '$name'";
$result = mysqli_query($conn, $query);
$response1 = array();

if(mysqli_num_rows($result)>0)
{
$product2 = array();
  $row = mysqli_fetch_assoc($result);
        $product2["product_id"] = $row["product_id"];
        $product2["Name"] = $row["Name"];
        $product2["stock"] = $row["Stock"];
        $product2["mrp"] = $row["Price_MRP"];
        $product2["mop"] = $row["Price_MOP"];
        $product2["ksprice"] = $row["Price_KS"];
        $product2["link"] = $row["links"];
        $product2["batch_id"] = $row["BatchId"];
        $product2["tax"] = $row["product_tax_percent"];
        $product2["found"] = "true";
        
        array_push($final_response,$product2);
}
else
{
 $product["found"] = "false";
 array_push($final_response, $product);
}
}
echo json_encode($final_response);
?>