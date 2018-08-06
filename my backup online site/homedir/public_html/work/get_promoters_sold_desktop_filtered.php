<?php
 
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");

$email = $_GET["email"];
$from_date = $_GET["from"];
$to_date = $_GET["to"];
$time = strtotime($from_date);

$newfromdate = date('Y-m-d',$time);
$time = strtotime($to_date);
$newtodate = date('Y-m-d',$time);
$query = "select * from khurana_sales_orders where sales_order_customer_email='$email'";
$result = mysqli_query($conn, $query);
$data = array();
if(mysqli_num_rows($result)>0)
{
  while($row = mysqli_fetch_array($result))
  {
    $time = strtotime($row["sales_order_date"]);
    $newcomparingdate =  date('Y-m-d',$time);
    if($newcomparingdate >= $newfromdate  && $newcomparingdate <= $newtodate  )
    {
     $product =  array();
   $product["name"] = $row["sales_order_product_name"];
   $product["email"] = $row["sales_order_customer_email"];
   $product["date"] = $row["sales_order_date"];
   $product["count"] = $row["sales_order_quantity"];
   $product["price"] = $row["sales_order_price"];
   $product["order_status"] = $row["sales_order_status"];
   $product["invoice_status"] = $row["invoice_status"];
   array_push($data, $product); 
   
    }
    
  }
  
  echo json_encode($data);  
}
else
{
echo "some error ";
}
?>