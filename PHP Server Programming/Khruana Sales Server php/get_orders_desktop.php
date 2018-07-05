 <?php
 
 $conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
 $db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error());

 $orders_query = "select DISTINCT sales_order_invoice_number from khurana_sales_orders";
 $result_orders = mysqli_query($conn, $orders_query);
 $order_numbers = array();
 
 if(mysqli_num_rows($result_orders) > 0)
 {
 $i = 0; 
   while($row = mysqli_fetch_array($result_orders))
   {
     $order_numbers[$i] = $row['sales_order_invoice_number'];
     $i = $i+1;
   }
 }
 
 $data = array();
 for($k = 0 ; $k < sizeof($order_numbers); $k++)
 {
       $invoice_number = $order_numbers[$k];
    $result = mysqli_query($conn,"select * from khurana_sales_orders where sales_order_invoice_number = $invoice_number ");
    while($row = mysqli_fetch_array($result))
    {
     $product =array();
     $product["sales_order_product_name"] = $row["sales_order_product_name"];
     $product["sales_order_product_count"] = $row["sales_order_quantity"];
     $product["sales_order_price"] = $row["sales_order_price"];
     $product["sales_order_number"] = $invoice_number;
     $product["sales_order_end_user_name"] = $row["sales_order_end_user_name"];
     $product["sales_order_end_user_phone"] = $row["sales_order_end_user_phone"];
     $product["cash_payment_amount"] = $row["cash_payment_amount"];
      if($row['paytm_payment_id'] == -1)
	  {
	  $product["paytm_payment"] = "NO";
	  }
	  else
	  {
	  $product["paytm_payment"] = "YES";
	  }
	  
	   if($row['cheque_payment_id'] == -1)
	  {
	    $product["cheque_payment"] = "NO";
	  }
	  else
	  {
	    $product["cheque_payment"] = "YES";
	  }
	  
	  
	   if($row['card_payment_id'] == -1)
	  {
	  $product["card_payment"] = "NO";
	  }
	  else
	  {
	  $product["card_payment"] = "YES";
	  }
	  
	  
	   if($row['finance_payment_id'] == -1)
	  {
	  $product["finance_payment"] = "NO";
	  }
	  else
	  {
	  $product["finance_payment"] = "YES";
	 }  
	 
	 if($row["batches_selected"] != null)
	 {
	  $product["batches_selected"] = $row["batches_selected"];
	 }
	 else
	 {
	   $product["batches_selected"] = "";
	 }
	
	 $product["promoter_email"]=  $row["sales_order_customer_email"];
	 $user_email = $row["sales_order_customer_email"];
	 
	   $user = mysqli_query($conn,"select * from khurana_sales_access where user_email ='$user_email' ");
	   $user_row = mysqli_fetch_assoc($user);
	  $product["user_name"] = $user_row["user_name"];
	array_push($data,$product);
    }
 }	
 echo json_encode($data) ; 	 	  
 ?>