<?php
$con = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($con,"KhuranaSalesRastapeth") or die(mysqli_error());
$email = $_GET["email"];
$query  = "select * from `khurana_sales_orders` where sales_order_customer_email = '$email' AND sales_order_status = 'VIEWED' ";
$result = mysqli_query($con,$query);
$availability = "true";
if(mysqli_num_rows($result) > 0)
{
  while($row = mysqli_fetch_array($result))
  {
    $batch_selected = $row["batches_selected"];
    $name = $row["sales_order_product_name"];
    $query2 = "select * from khurana_sales_stock where name = '$name'";
    $product_details = mysqli_query($con,$query2);
    if(mysqli_num_rows($product_details)> 0 )
    {
      while($row_new = mysqli_fetch_array($product_details))
      {
        $batch = $row_new["BatchId"];
       $query_batch = "select * from batch_numbers where id = $batch";
       $found = false;
       $batch_detail = mysqli_query($con,$query_batch);
       if(mysqli_num_rows($batch_detail) > 0)
       {
         while($row_batch = mysqli_fetch_array($batch_detail))
         {
           $batch1 = $row_batch["3 Main Location"];
           if (strpos($batch1, $batch_selected) !== false) {
   	             $found = true;
   	             break;
   		}
            $batch1 = $row_batch["Jm Road"];
           if (strpos($batch1, $batch_selected) !== false) {
   		 $found = true;
   		 break;
		}
            $batch1 = $row_batch["Vat Stok"];
           if (strpos($batch1, $batch_selected) !== false) {
   		 $found = true;
   		break;
		}
           
         }
         
       }
       
      }
      if($found == false)
      {
      $availability = "false";
      break;
      }
    }
  }
}
$result = array();
$result["availability"] = $availability;
$result_new = array();
array_push($result_new,$result);
echo json_encode($result_new)

?>