<?php  
$conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error($conn));
$customer_email = "";
$customer_name = "";
$customer_phone = "";
$customer_address = "";
$customer_gst = "";
$promoter_email = "";

if($_SERVER['REQUEST_METHOD'] == 'GET')
{
$customer_name = $_GET["customer_name"];
$customer_phone = $_GET["customer_phone"];
$customer_email = $_GET["customer_email"];
$customer_address = $_GET["customer_address"];
$customer_gst = $_GET["customer_gst"];
$promoter_email = $_GET["promoter_email"];
}
if($_SERVER['REQUEST_METHOD'] == 'POST')
{
$customer_name = $_POST["customer_name"];
$customer_phone = $_POST["customer_phone"];
$customer_email = $_POST["customer_email"];
$customer_address = $_POST["customer_address"];
$customer_gst = $_POST["customer_gst"];
$promoter_email = $_POST["promoter_email"];
}
$sql = "update khurana_sales_orders set sales_order_end_user_name = '$customer_name',sales_order_end_user_phone = $customer_phone, sales_order_end_user_email = '$customer_email', sales_order_address = '$customer_address', sales_order_end_user_gst_no = '$customer_gst' where sales_order_customer_email = '$promoter_email' AND sales_order_status = 'VIEWED'";
$result = mysqli_query($conn,$sql) or die(mysqli_error($conn));
if($result)
{
	echo "successfull";
}
else
{
	echo "failure";
}
$sql_check_ledger = "select * from khurana_sales_ledgers where Email = '$customer_email'";
$result_check_ledger = mysqli_query($conn, $sql_check_ledger) or die(mysqli_error($conn));
if(mysqli_num_rows($result_check_ledger) > 0)
{
 $sql_update_ledger = "update khurana_sales_ledgers set Name = '$customer_name' , GSTIN = '$customer_gst', Mobile = $customer_phone, Address = '$customer_address' where Email = '$customer_email'";
 $result_update_ledger = mysqli_query($conn, $sql_update_ledger);
 
}
else
{
  $sql_insert = "insert into khurana_sales_ledgers(Name, Email, Mobile, GSTIN, Address, Type) values ('$customer_name', '$customer_email', '$customer_phone', '$customer_gst', '$customer_address', 'LEDGER')";
  $result_insert = mysqli_query($conn, $sql_insert) or die(mysqli_error($conn));
}
