<?php
 
$conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error());
$data = json_decode(file_get_contents('php://input'), true);
$array = $data["promoters"];
$data = array();
foreach($array as $data_parsed)
{
$email = $data_parsed["email"];
$from_date = $data_parsed["from"];
$to_date = $data_parsed["to"];
$time = strtotime($from_date);
$newfromdate = date('Y-m-d',$time);
$time = strtotime($to_date);
$newtodate = date('Y-m-d',$time);
$query = "select * from khurana_sales_orders where sales_order_customer_email='$email'";
$sql_promoter_info = "select * from khurana_sales_access where user_email = '$email' ";
$promoter_result = mysqli_query($conn, $sql_promoter_info) or die(mysqli_error($conn));
$promoter_info = mysqli_fetch_assoc($promoter_result);

$result = mysqli_query($conn, $query);
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
   $product["promoter_name"] = $promoter_info["user_name"];
   $product["date"] = $row["sales_order_date"];
   $product["count"] = $row["sales_order_quantity"];
   $product["price"] = $row["sales_order_price"];
   $product["order_status"] = $row["sales_order_status"];
   $product["invoice_status"] = $row["invoice_status"];
   $product["discount_offered"] = $row["sales_order_discount"];
   $product["customer_name"] = $row["sales_order_end_user_name"];
   array_push($data, $product); 
   
    }
    
  }
}
else
{
echo "some error ";
}
}
$response["response"] = $data;
$response["success"] = true;
echo json_encode($response);  
?>