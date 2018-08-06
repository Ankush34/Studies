<?php
   require_once __DIR__ . '/firebase_firebase.php';
        require_once __DIR__ . '/firebase_push.php';

$response = array();
 
    $mrp = $_POST['mrp'];
    $mop = $_POST['mop'];    
    $ksprice = $_POST['ksprice'];
    $name = $_POST['name'];
    
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$query = "update khurana_sales_stock set Price_MRP= $mrp,Price_MOP= $mop,Price_KS= $ksprice where Name='$name';";
$q = mysqli_query($conn, $query);
if($q)
{
echo "successfully updated";
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
	         $data["notification_type"] = "Price Change";
	         $data["notification_product_id"] = $row_product["product_id"];
    		 $payload["mobile_info"] = $data;
                 $firebase = new Firebase();
                 $push = new Push();
                 $push_type = "indivisual";
       		 $title ="Price of few products in your cart are changed";
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
else
{
echo "sorry";
}
?>

