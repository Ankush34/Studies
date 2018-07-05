<?php  
$con = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($con,"KhuranaSalesRastapeth") or die(mysqli_error());
$email = $_GET["email"];
$get_query = "select * from khurana_sales_offers";
$result = mysqli_query($con, $get_query);
if(mysqli_num_rows($result) > 0)
{
$products = array();
$response = array();
while($row = mysqli_fetch_array($result))
{
  $product_info  = array();
  $product_id = $row["product_id"];
  $offer_title = $row["offer_title"];
  $offer_description = $row["offer_description"];
  $product_query = "select * from khurana_sales_stock where product_id = $product_id";
  $product_result  = mysqli_query($con,$product_query);
  $product = mysqli_fetch_assoc($product_result);
    	$product_info["product_id"] = $product["product_id"];
        $product_info["Name"] = $product["Name"];
        $product_info["stock"] = $product["Stock"];
        $product_info["mrp"] = $product["Price_MRP"];
        $product_info["mop"] = $product["Price_MOP"];
        $product_info["ksprice"] = $product["Price_KS"];
        $product_info["link"] = $product["links"];
        $product_info["tax"] = $product["product_tax_percent"];
        $product_info["offer_title"] = $offer_title;
        $product_info["offer_description"] = $offer_description;
        
        array_push($products,$product_info);
}
$response["status"] = "Success";
$response["product_info"] = $products;

echo json_encode([$response]);
}
else
{
$response = array();
$response["status"] = "Empty";
echo json_encode([$response]);
}

?>



