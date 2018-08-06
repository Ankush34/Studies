<?php  
$con = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($con,"KhuranaSalesRastapeth") or die(mysqli_error());
$content = trim(file_get_contents("php://input"));
 $decoded = json_decode($content, true);
 
$product_id = $decoded["product_id"];
$title = $decoded["title"];
$description = $decoded["description"];
$offer_type = $decoded["offer_type"];

$query = "";
if($offer_type == "Super Value Offer")
{
$item_count = $decoded["minimum_item_count"];
$combined_price = $decoded["discounted_amount"];
$query = "insert into khurana_sales_offers(product_id,offer_title,offer_description,min_item_count, discounted_combined_price, offer_type)values ($product_id,'$title','$description',$item_count, $combined_price, '$offer_type')";


}	

if($offer_type == "Combo Offer")
{
$product_ids_discounted = $decoded["discount_product_ids"];
$product_discounted_prices = $decoded["discounted_product_discounts"];
$products_ids_free = $decoded["free_product_ids"];
$query = "insert into khurana_sales_offers(product_id,offer_title,offer_description, discounted_products_ids, discounted_products_prices, free_products_ids, offer_type) values ($product_id,'$title','$description', '$product_ids_discounted', '$product_discounted_prices', '$products_ids_free', '$offer_type')";
 
}

if($offer_type == "Discount Offer")
{
  $discounted_amount = $decoded["total_discount_offered"];
$query = "insert into khurana_sales_offers(product_id,offer_title,offer_description,discount_amount_single_product, offer_type)values ($product_id,'$title','$description',$discounted_amount,'$offer_type')";

}

$query_check = "select * from khurana_sales_offers where product_id = $product_id ";
$query_check_result = mysqli_query($con, $query_check);
if(mysqli_num_rows($query_check_result) > 0)
{
$result = array();
$result["status"] = "success";
echo json_encode($result);
}
else
{

$query_insert = "";
if($query != "")
{
$query_insert = $query;
}
else
{
$query_insert = "insert into khurana_sales_offers(product_id,offer_title,offer_description) values ($product_id,'$title','$description')";
}
$result = mysqli_query($con,$query_insert);
if($result > 0)
{
$result = array();
$result["status"] = "success";
echo json_encode($result);
}
else
{
$result = array();
$result["status"] = "failure";
$result["query"] = $query_insert;
echo json_encode($result);

}

}

?>

