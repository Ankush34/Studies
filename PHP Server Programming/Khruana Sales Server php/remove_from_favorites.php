<?php  
$con = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($con,"KhuranaSalesRastapeth") or die(mysqli_error());
$product_id = $_GET["product_id"];
$email = $_GET["email"];
$query_delete = "delete from khurana_sales_favorites_products where product_id = $product_id AND user_email = '$email' ";
$result = mysqli_query($con,$query_delete);
if($result > 0)
{
$result = array();
$result["status"] = "success";
echo json_encode([$result]);
}
else
{
$result = array();
$result["status"] = "failure";
echo json_encode([$result]);
}

?>



