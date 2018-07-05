<?php
 
/*
 * Following code will list all the products
 */
 $response = array();
$response1=array();
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$product = array();
$product1 = array();
$product3 = array();
$date_order = array();
$email = $_GET["email"];
$date_from_web = $_GET["order_date"];
$query_fetch = "SELECT * FROM khurana_sales_orders Where sales_order_date = '$date_from_web' AND sales_order_customer_email = '$email' AND sales_order_status='ORDERED' OR sales_order_status = 'CANCELLED'";
$address = array();
$sales_order_status = array();
$sales = array();

$result = mysqli_query($conn,$query_fetch) or die(mysqli_error($conn));
if (mysqli_num_rows($result)>0) {
$i=0;
    while ($row = mysqli_fetch_array($result)) {
        // temp user array      
        $product[$i]=$row["sales_order_product_name"];
         $product1[$i]=$row["sales_order_quantity"];
         $address[$i] = $row["sales_order_address"];
         $product3[$i]=$row["sales_order_number"]; 
         $date_order[$i] = $row["sales_order_date"];
         $sales_order_status[$i] = $row['sales_order_status'];
         $sales[$i] = $row["sales_order_invoice_number"];
         
        // push single product into final response array
$i++;            
          }
          
}
$total_price=0;
$product2=array();
if(sizeof($product)>0)
{
for($k=0;$k <sizeof($product); $k++)
{
$result2 = mysqli_query($conn,"SELECT * from khurana_sales_stock where Name = '$product[$k]' ")or die(mysqli_error($conn));
if (mysqli_num_rows($result2)>0) {
    while ($row = mysqli_fetch_array($result2)) {
        // temp user array
        $product2["product_id"] = $row["product_id"];
        $product2["name"]=$row["Name"];
        $product2["stock"] = $product1[$k];
        $product2["MRP"] = $row["Price_MRP"];
        $product2["MOP"] = $row["Price_MOP"];
        $product2["ksprice"]=$row["Price_KS"];
        $product2["links"]=$row["links"];
        $product2["address"] = $address[$k];
        $product2["sales_order_number"] = $product3[$k];
        $product2["sales_order_date"] = $date_order[$k];
        $product2["tax"] = $row["product_tax_percent"];
        $product2["sales_order_status"] = $sales_order_status[$k];
        $product2["sales_order_invoice_number"] = $sales[$k];
        $total_price=$total_price+$product2["ksprice"];       
        // push single product into final response array
        array_push($response1,$product2);
    }
   }
}echo json_encode($response1);
}
else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>