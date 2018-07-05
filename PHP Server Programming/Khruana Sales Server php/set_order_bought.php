<?php
require_once __DIR__ . '/firebase_firebase.php';
require_once __DIR__ . '/firebase_push.php';
require_once __DIR__ . '/send_mail.php';

$conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error());
$email = $_GET["email"];
$date_of_order = $_GET["date"];
$sql_order_number = "SELECT * FROM khurana_sales_orders  where sales_order_status = 'ORDERED' ORDER BY sales_order_number DESC LIMIT 1";
$result_sales_order_number = mysqli_query($conn,$sql_order_number);
$row_sales_order_number = mysqli_fetch_assoc($result_sales_order_number);
$invoice_number = $row_sales_order_number["sales_order_invoice_number"] + 1;
$sql1 = "select * from khurana_sales_orders where sales_order_customer_email = '$email' and sales_order_status = 'VIEWED'";
$result = mysqli_query($conn,$sql1) or die(mysqli_error($conn));
if(mysqli_num_rows($result)>0){
	$response = array();
	$response["status"] = "success";
        echo json_encode([$response]);
	while ($row = mysqli_fetch_array($result))
	{
		$product_name = $row["sales_order_product_name"];
		$sql2 = "update khurana_sales_orders set sales_order_invoice_number = $invoice_number , sales_order_status = 'ORDERED',sales_order_date = '$date_of_order' where sales_order_customer_email = '$email' and sales_order_product_name = '$product_name' and  sales_order_status = 'VIEWED' ";
		mysqli_query($conn,$sql2);
		//$sql3 = "update khurana_sales_stock set Stock = Stock + $row[sales_order_quantity] where Name = '$product_name'";
	        //mysqli_query($conn,$sql3);
	}
	$sql1 = "select * from khurana_sales_orders where sales_order_customer_email = '$email' and sales_order_status = 'ORDERED'";
        $result = mysqli_query($conn,$sql1) or die(mysqli_error($conn));
        if(mysqli_num_rows($result) > 0)
        {			
	while($row = mysqli_fetch_array($result))
	{
	  $batch = $row["batches_selected"];
	  $name = $row["sales_order_product_name"];
	  $query = "select * from khurana_sales_orders where sales_order_product_name = '$name' AND sales_order_status = 'VIEWED'";
	  $same_product_customers = mysqli_query($conn,$query) or die(mysqli_error($conn));
	  $product_query = "select * from khurana_sales_stock where Name = '$name'";
	   $result_product = mysqli_query($conn,$product_query);
	             $payload = array();
	         $data = array();
	             $row_product = mysqli_fetch_assoc($result_product);
	     
	         $data["mobile_name"] = $row_product["Name"];
	         $data["mobile_brand"] = $row_product["Brand"];
	         $data["mobile_stock"] = $row_product["Stock"];
	         $data["mobile_mop_price"] = $row_product["Price_MOP"];
	         $data["mobile_ks_price"] = $row_product["Price_KS"];
	         $data["mobile_image_links"] = $row_product["links"];
	         $data["notification_type"] = "Out Of Stock";
	         $data["notification_product_id"] = $row_product["product_id"];
    		 $payload["mobile_info"] = $data;
                 $firebase = new Firebase();
                 $push = new Push();
                 $push_type = "indivisual";
       		 $title ="Products in cart out of stock";
        	 $message = "Please change items in your cart";
       		 $include_image="FALSE";
       		  $push->setTitle($title);
                  $push->setMessage($message);
       		  $push->setIsBackground(FALSE);
                  $push->setPayload($payload);
          
	  if(mysqli_num_rows($same_product_customers) > 0)
	  {
	    while($row_other  = mysqli_fetch_array($same_product_customers))
	    {
	    $customer_email = $row_other["sales_order_customer_email"];
	      $batches_selected_by_other_customer = $row_other["batches_selected"];
	      if(strpos($batch, $batches_selected_by_other_customer) !== false)
	      {
	         $customer_query = "select firebase_id from khurana_sales_access where user_email = '$customer_email' ";
	         $result_customer = mysqli_query($conn,$customer_query);
	         $row_customer = mysqli_fetch_assoc($result_customer);
	     	 $regId = $row_customer["firebase_id"];
       	         $json = '';
                 $json = $push->getPush();
                 $response_firebase['in indivisual'] = "true";
                 $response_firebase = $firebase->send($regId, $json);
                 if ($response_firebase != '') {
	           $success = "true";
	           $response_firebase["success"] = $success;
	          }
	   

	      }
	    }
	  }
	  
	}
      }
      
      $email_sending = new send_mail("khuranasales2015@gmail.com",'$email',"Invoice Has Been Generated");
      $email_sending_owner = new send_mail("ankushkhurana34@gmail.com",'khuranasales2015@gmail.com',"Invoice Has Been Generated");
      $promoter = $email;
      $email_sending->send_mail_start("ankush@amuratech.com",'khuranasales2015@gmail.com',"Invoice Generated,  An Order Took Place",$invoice_number,$promoter);
$email_sending_owner->send_mail_start("khuranasales2015@gmail.com",'ankush@amuratech.com',"Invoice Generated,  An Order Took Place",$invoice_number,$promoter);     
}
?>
