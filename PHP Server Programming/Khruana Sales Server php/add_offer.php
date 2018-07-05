<?php  
$con = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($con,"KhuranaSalesRastapeth") or die(mysqli_error());
$content = trim(file_get_contents("php://input"));
 $decoded = json_decode($content, true);
 
$product_id = $decoded["product_id"];
$title = $decoded["title"];
$description = $decoded["description"];

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
$query_insert = "insert into khurana_sales_offers(product_id,offer_title,offer_description) values ($product_id,'$title','$description')";
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
echo json_encode($result);

}

}

?>

