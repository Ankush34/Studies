<?php
 

$con = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($con,"KhuranaSalesRastapeth") or die(mysqli_error());
$customer_id = $_GET["customer_id"];
$result = mysqli_query($con,"select * from khurana_sales_delivery_addresses where khurana_sales_customer_id = $customer_id ") or die(mysqli_error($con));
if (mysqli_num_rows($result)>0) {
    $response1=array();
    $product = array();
    while ($row = mysqli_fetch_array($result)) {
        $product2["address"] = $row["address"];
        array_push($response1,$product2);
          }
echo json_encode($response1);
}
else {
    $response = array();
    $response["success"] = 0;
    $response["message"] = "No products found";
    echo json_encode($response);
}
?>



