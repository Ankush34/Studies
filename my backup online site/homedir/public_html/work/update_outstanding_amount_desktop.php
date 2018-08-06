<?php
$conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error());

$email = $_GET["email"];
$amount = $_GET["amount_to_write"];

$sql = "update khurana_sales_access set outstanding_amount = $amount where user_email = '$email'";
$result = mysqli_query($conn, $sql) or die(mysqli_error($conn));
if($result)
{
echo "promoter has been updated";
}
else
{
echo "could not update promoter";
}
?>