<?php  
$con = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$response = array();
$response1=array();
$product = array();
$db = mysqli_select_db($con,"KhuranaSalesRastapeth") or die(mysqli_error());
if($_SERVER['REQUEST_METHOD']=='GET'){
$customer_email = $_GET['customer_email'];
$product_id = $_GET["id"];
$product_ordered_quantity = $_GET["count"];
$date_of_product = $_GET["date"];

$product_info_query = "select * from khurana_sales_stock where product_id =".$product_id." ";
$product_info = mysqli_query($con,$product_info_query);
$info_row = mysqli_fetch_assoc($product_info);
$name = $info_row["Name"];

$query2="select sales_order_quantity as total from khurana_sales_orders where sales_order_product_name = '$name' And sales_order_customer_email ='$customer_email' AND sales_order_status ='VIEWED' " ;
$result3=mysqli_query($con,$query2)or die(mysql_error());
$data=mysqli_fetch_assoc($result3);
$count=$data['total'];
if($count<1)
{
$product_info_query = "select * from khurana_sales_stock where product_id =".$product_id." ";
$product_info = mysqli_query($con,$product_info_query);
$info_row = mysqli_fetch_assoc($product_info);
$name = $info_row["Name"];
$price_mop = $info_row["Price_MOP"];
$price_mrp = $info_row["Price_MRP"];
$price_ks = $info_row["Price_KS"]; 
$sql = "INSERT INTO khurana_sales_orders (sales_order_date,sales_order_customer_email,sales_order_product_name,sales_order_price,sales_order_quantity,sales_order_status) VALUES ('$date_of_product','$customer_email','$name',$price_mop,$product_ordered_quantity,'VIEWED')";
mysqli_query($con,$sql)or die(mysqli_error($con));
$product2["success"] ="true";
array_push($response1,$product2);
echo json_encode($response1);

}
else
{
$product_info_query = "select * from khurana_sales_stock where product_id =".$product_id." ";
$product_info = mysqli_query($con,$product_info_query);
$info_row = mysqli_fetch_assoc($product_info);
$product_name = $info_row["Name"];
$total=$count + $product_ordered_quantity;
$sql2="update khurana_sales_orders set sales_order_quantity = '$total' where sales_order_customer_email='$customer_email' and sales_order_product_name = '$product_name' and sales_order_status='VIEWED' ";
$success = mysqli_query($con,$sql2)or die(mysqli_error($con));
$product2["success"] ="true";
        array_push($response1,$product2);
       echo json_encode($response1);
 
}
 }
else{
$product2["success"] ="false";
        array_push($response1,$product2);
       echo json_encode($response1);

}

?>



