<?php
 
/*
 * Following code will list all the products
 */
 $response = array();
$response1=array();
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$order_date = $_GET["sales_date"];
$promoters = array();
$query = "SELECT DISTINCT user_name,user_email,user_id,outstanding_amount FROM khurana_sales_access";
$result = mysqli_query($conn, $query) or die(mysqli_error());
if(mysqli_num_rows($result) > 0 )
{
	while($row = mysqli_fetch_array($result))
	{
		$product = array();
		$product["promoter_name"] = $row["user_name"];
		$product["promoter_email"] = $row["user_email"];
		$product["promoter_id"] = $row["user_id"];
		$product["outstanding_amount"] = $row["outstanding_amount"];
		array_push($response,$product);
	}
	echo json_encode($response);
	
}
else
{
echo "no sellers found";
}

?>



