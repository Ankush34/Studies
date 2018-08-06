<?php


 $response = array();
$product_id = 0;
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
if(isset($_GET['product_id']))
{ 
$product_id = $_GET['product_id'];
}
$count = 0;
$result1=mysqli_query($conn,"select stock as total from khurana_sales_stock where product_id = {$product_id} ;") or  die(mysqli_error());
$data=mysqli_fetch_assoc($result1);
$count=$data['total'];
if($count == null)
{
$count = 0;
}
 $product = array();
        $product["count"] = $count;
        array_push($response,$product);
echo json_encode($response);

?>

