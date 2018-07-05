<?php
$response = array();
$response1=array();
$product2 = array();
$email = $_GET["email"];
$query = "select * from khurana_sales_orders where sales_order_customer_email = '$email' and sales_order_status = 'ORDERED' ";
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$result = mysqli_query($conn,$query);
if (mysqli_num_rows($result)>0) {
    while ($row = mysqli_fetch_array($result)) {
        $product2["order_date"] = $row["sales_order_date"];
        $product2["product_name"] = $row["sales_order_product_name"];
        $product2["order_quantity"] = $row["sales_order_quantity"];
        $product2["order_address"] = $row["sales_order_address"];
        $name = $row["sales_order_product_name"];
  	$query1 = "select * from khurana_sales_stock where Name = '$name'";
  	$result2 = mysqli_query($conn,$query1);
  	$row1 = mysqli_fetch_assoc($result2);
 	$product2["order_per_price_mop"] = $row1["Price_MOP"];
  	$product2["order_per_price_mrp"] = $row1["Price_MRP"];
  	$product2["order_per_price_ks"] = $row1["Price_KS"];
        array_push($response1,$product2);
          }

echo json_encode($response1);


}
else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
    // echo no users JSON
    echo json_encode($response);
}

?>
