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
$batches_selected = array();
$email = $_GET["email"];
$result = mysqli_query($conn,"SELECT * FROM khurana_sales_orders Where sales_order_customer_email = '$email' and sales_order_status='VIEWED';") or die(mysqli_error($conn));
if (mysqli_num_rows($result)>0) {
$i=0;
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        
        $product[$i]=$row["sales_order_product_name"];
         $product1[$i]=$row["sales_order_quantity"];
         $batches_selected[$i] = $row["batches_selected"];
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
        $product2["links"] = $row["links"];
        $total_price=$total_price+$product2["ksprice"];   
        $product2["tax"] = $row["product_tax_percent"];  
        $product2["product_hsn"] = $row["Product_HSN"];
        $product2["batch_selected"] = $batches_selected[$k];
        $batch_id = $row["BatchId"];
        $batch = array();
        if($batch_id > 0)
        {
        $query_batch  = "SELECT * FROM `batch_numbers` WHERE id = $batch_id";
        $result_batch  = mysqli_query($conn,$query_batch)or die(mysqli_error($conn));
        if(mysqli_num_rows($result_batch) > 0)
        {
        $row_batch = mysqli_fetch_array($result_batch);
        $batch_detail["batch_id"] = $row_batch["id"];
        $batch_detail["product_id"] = $row_batch["product_id"];
        $batch_detail["3 Main Location"] = $row_batch["3 Main Location"];
        $batch_detail["Jm Road"] = $row_batch["Jm Road"];
        $batch_detail["Vat Stock"] = $row_batch["Vat Stok"];
        array_push($batch,$batch_detail);
        }
        }
        $product2["batch_details"]  = $batch;
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


