<?php  
$con = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($con,"KhuranaSalesRastapeth") or die(mysqli_error());
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
  $offer_type = $row["offer_type"];
  if($offer_type == "Super Value Offer")
  {
  $item_count = $row["min_item_count"];
  $discounted_price = $row["discounted_combined_price"];
  }
  
  if($offer_type == "Combo Offer")
  {
  $discounted_products_ids = $row["discounted_products_ids"];
  $free_products_ids = $row["free_products_ids"];
  $discounted_products_prices = $row["discounted_products_prices"];
  }
  
  if($offer_type == "Discount Offer")
  {
    $discount_offered = $row["discount_amount_single_product"];
    
  }
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
        $product_info["offer_type"] = $offer_type;
        if($offer_type == "Super Value Offer")
        {
        $product_info["item_count"] = $item_count;
        $product_info["total_item_price"] = $discounted_price;
        
        }
        if($offer_type == "Discount Offer")
        {
          $product_info["discount_offered"] = $discount_offered; 
        }
        if($offer_type == "Combo Offer")
        {
          $product_info["discounted_products_ids"] = $discounted_products_ids;
          $product_info["free_products_ids"] = $free_products_ids;
          $product_info["discounted_product_prices"] = $discounted_products_prices;
         }
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



