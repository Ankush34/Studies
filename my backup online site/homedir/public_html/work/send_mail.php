<?php
$conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error());

class send_mail{
	var $from;
	var $to;
	var $message;
	
 function _construct($from, $to, $message)
 {
 	$this->from = $from;
 	$this->to = $to;
 	$this->message = $message;
 	
 }
 
 function send_mail_start($from_email, $to_email, $subject_email, $invoice_number, $promoter)
 {
 
	$to = $to_email ;
	$subject = $subject_email;
	$from = $from_email;
	 
	 $products = $products_email;
	// To send HTML mail, the Content-type header must be set
	$headers  = 'MIME-Version: 1.0' . "\r\n";
	$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";
	 
	// Create email headers
	$headers .= 'From: '.$from."\r\n".
	    'Reply-To: '.$from."\r\n" .
	    'X-Mailer: PHP/' . phpversion();
	 
	// Compose a simple HTML email message
	$message = '<html><body>';
	$message .= '<h1>Hi Sir , Mukesh Khurana</h1>';
	$message .= '<p style="font-size:18px;">A New Invoice Has Been Generated</p>';
	$message .= '<table cellspacing="20", style="border-collapse: separate; border: 1px solid black; border-spacing: 15px 20px"><th>Product Name</th><th>Total</th><th>Selling Price</th>';
	  
	  
	 $conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
         $db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error());
$result = mysqli_query($conn,"select * from khurana_sales_orders where sales_order_customer_email = '$promoter' AND sales_order_invoice_number = $invoice_number ");       
           $viewed = false;
	  while($row = mysqli_fetch_array($result))
	  {
	  if($viewed == false)
	  {
	  if($row['finance_payment_id'] != -1)
	  {
	  $viewed = true;
	  $message .= '<th>Customer Name</th><th>Customer phone</th><th>Customer GST</th><th>Customer Address</th><th>Customer Email</th><th>Discount Amount</th><th>Cash Payment</th><th>Processing Fees</th><th>Paytm Payment</th><th>Cheque Payment</th><th>Card Payment</th><th>Finance Payment</th><th>Batches Selected</th><th>Promoter Name</th>';
	  }
	  else
	  {
	  $viewed = true;
	  $message .= '<th>Customer Name</th><th>Customer phone</th><th>Customer GST</th><th>Customer Address</th><th>Customer Email</th><th>Discount Amount</th><th>Cash Payment</th><th>Paytm Payment</th><th>Cheque Payment</th><th>Card Payment</th><th>Finance Payment</th><th>Batches Selected</th><th>Promoter Name</th>';
	  }
	  }
	   $message .= '<tr>';
	  $message .= '<td>'.$row["sales_order_product_name"].'</td>';
	  $message .= '<td>'.$row['sales_order_quantity'].'</td>';
	  $message .= '<td>'.$row['sales_order_price'].'</td>';
	  $message .= '<td>'.$row['sales_order_end_user_name'].'</td>';
	  $message .= '<td>'.$row['sales_order_end_user_phone'].'</td>';
	  $message .= '<td>'.$row['sales_order_end_user_gst_no'].'</td>';
	  $message .= '<td>'.$row['sales_order_address'].'</td>';
	  $message .= '<td>'.$row['sales_order_end_user_email'].'</td>';
	  $message .= '<td>'.$row['sales_order_discount'].'</td>';
	  
	  $processing_fees_taken = 0;
	  if($row['finance_payment_id'] != -1)
	  {
	   $finance_payment_id = $row["finance_payment_id"];
	   $query_finance = "select * from khurana_sales_finance_payment where id = $finance_payment_id";
	   $result = mysqli_query($conn, $query_finance);
	   $row_finance_payment = mysqli_fetch_assoc($result);
	   $processing_fees_taken = $row_finance_payment["finance_down_payment"];
	  }
	  
	  if($processing_fees_taken  != 0)
	  {
	  $message .= '<td>'.$row['cash_payment_amount'].'</td>';
	  $message .= '<td>'.$processing_fees_taken.'</td>';
	  }
	  else
	  {
	  $message .= '<td>'.$row['cash_payment_amount'].'</td>';
	  }
	  
	  
	  if($row['paytm_payment_id'] == -1)
	  {
	  $message .= '<td>'."No".'</td>';
	  }
	  else
	  {
	   $paytm_payment_id = $row["paytm_payment_id"];
	   $query_paytm = "select * from khurana_sales_paytm_payments where id = $paytm_payment_id";
	   $result = mysqli_query($conn, $query_paytm);
	   $row_paytm_payment = mysqli_fetch_assoc($result);
	   $paytm_payment_amount = $row_paytm_payment["paytm_payment_amount"];
	   $message .= '<td>'.$paytm_payment_amount.'</td>';
	  }
	  
	  if($row['cheque_payment_id'] == -1)
	  {
	  $message .= '<td>'."No".'</td>';
	  }
	  else
	  {
	   $cheque_payment_id = $row["cheque_payment_id"];
	   $query_cheque = "select * from khurana_sales_cheque_payments where id = $cheque_payment_id";
	   $result = mysqli_query($conn, $query_cheque);
	   $row_cheque_payment = mysqli_fetch_assoc($result);
	   $cheque_payment_amount = $row_cheque_payment["cheque_amount"];
	   $cheque_bank = $row_cheque_payment["cheque_bank_name"];
	   $message .= '<td>'.$cheque_payment_amount." (".$cheque_bank.") ".'</td>';
	  }
	  
	  if($row['card_payment_id'] == -1)
	  {
	  $message .= '<td>'."No".'</td>';
	  }
	  else
	  {
	   $card_payment_id = $row["card_payment_id"];
	   $query_card = "select * from khurana_sales_card_payment where id = $card_payment_id";
	   $result = mysqli_query($conn, $query_card);
	   $row_card_payment = mysqli_fetch_assoc($result);
	   $card_payment_amount = $row_card_payment["card_payment_amount"];
	   $card_bank = $row_card_payment["card_bank_name"];
	   $message .= '<td>'.$card_payment_amount." (".$card_bank.") ".'</td>';
	  }
	  
	  if($row['finance_payment_id'] == -1)
	  {
	  $message .= '<td>'."No".'</td>';
	  }
	  else
	  {
	   $finance_payment_id = $row["finance_payment_id"];
	   $query_finance = "select * from khurana_sales_finance_payment where id = $finance_payment_id";
	   $result = mysqli_query($conn, $query_finance);
	   $row_finance_payment = mysqli_fetch_assoc($result);
	   $finance_payment_amount = $row_finance_payment["finance_amount"];
	   $financer = $row_finance_payment["financer"];
	   $message .= '<td>'.$finance_payment_amount." (".$financer.") ".'</td>';
	   
	  }
	   $message .= '<td>'.$row['batches_selected'].'</td>';
	  $user_email_from_result = $row['sales_order_customer_email'];
	  $user = mysqli_query($conn,"select * from khurana_sales_access where user_email ='$user_email_from_result' ");
	  $user_row = mysqli_fetch_assoc($user);
	   $message .= '<td>'.$user_row["user_name"].'</td>';
	  
	  $message .= '</tr>';
	  }
	  
	
	$message .= '</table>';
	$message .= '</body></html>';
	 
	// Sending email
	if(mail($to, $subject, $message, $headers)){
	    echo 'Your mail has been sent successfully.';
	} else{
	    echo 'Unable to send email. Please try again.';
	}
 }
 
}
?>