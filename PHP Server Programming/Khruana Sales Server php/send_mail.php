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
	  $message .= '<th>Customer Name</th><th>Customer phone</th><th>Cash Payment</th><th>Card Payment</th><th>Finance Payment</th><th>Cheque Payment</th><th>Paytm Payment</th><th>Batches Selected</th><th>Promoter Name</th>';
	 $conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
         $db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error());
$result = mysqli_query($conn,"select * from khurana_sales_orders where sales_order_customer_email = '$promoter' AND sales_order_invoice_number = $invoice_number ");
	  while($row = mysqli_fetch_array($result))
	  {
	   $message .= '<tr>';
	  $message .= '<td>'.$row["sales_order_product_name"].'</td>';
	  $message .= '<td>'.$row['sales_order_quantity'].'</td>';
	  $message .= '<td>'.$row['sales_order_price'].'</td>';
	  $message .= '<td>'.$row['sales_order_end_user_name'].'</td>';
	  $message .= '<td>'.$row['sales_order_end_user_phone'].'</td>';
	  $message .= '<td>'.$row['cash_payment_amount'].'</td>';
	  if($row['paytm_payment_id'] == -1)
	  {
	  $message .= '<td>'."No".'</td>';
	  }
	  else
	  {
	   $message .= '<td>'."Yes".'</td>';
	  }
	  
	  if($row['cheque_payment_id'] == -1)
	  {
	  $message .= '<td>'."No".'</td>';
	  }
	  else
	  {
	   $message .= '<td>'."Yes".'</td>';
	  }
	  
	  if($row['card_payment_id'] == -1)
	  {
	  $message .= '<td>'."No".'</td>';
	  }
	  else
	  {
	   $message .= '<td>'."Yes".'</td>';
	  }
	  
	  if($row['finance_payment_id'] == -1)
	  {
	  $message .= '<td>'."No".'</td>';
	  }
	  else
	  {
	   $message .= '<td>'."Yes".'</td>';
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