<?php
 
/*
 * Following code will list all the products
 */
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$product = array();
$query = "SELECT SUM( sales_order_quantity ) AS sales, sales_order_product_name
FROM  `khurana_sales_orders` 
GROUP BY sales_order_product_name
ORDER BY sales DESC 
LIMIT 10";

$sold_count = array();
$product_name = array();

$result = mysqli_query($conn, $query) or die(mysqli_error());
if (mysqli_num_rows($result)>0) {
$i = 0 ;
$data = array();
    while ($row = mysqli_fetch_array($result)) {
       	$product_name[$i] = $row["sales_order_product_name"];
       	$sold_count[$i] = $row["sales"];
          }

   for($k = 0 ; $k < sizeof($sold_count);$k++)
   {
   $product_info = array();
     $query_product = "select * from khurana_sales_stock where Name = '$product_name[$k]'";
     $product_result =mysqli_query($conn, $query_product);
     $product = mysqli_fetch_assoc($product_result);
       $product_info["product_id"] = $product["product_id"];
        $product_info["Name"] = $product["Name"];
        $product_info["stock"] = $product["Stock"];
        $product_info["mrp"] = $product["Price_MRP"];
        $product_info["mop"] = $product["Price_MOP"];
        $product_info["ksprice"] = $product["Price_KS"];
        $product_info["link"] = $product["links"];
        $product_info["tax"] = $product["product_tax_percent"];
        $product_info["sold_count"] = $sold_count[$k];
        array_push($data,$product_info);
   }
echo json_encode($data);
}
else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>
