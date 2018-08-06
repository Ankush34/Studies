        <?php
        
         $conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
  	 $db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
    
        $result = array();
        $content = trim(file_get_contents("php://input"));
	$decoded = json_decode($content, true);
        require_once __DIR__ . '/firebase_firebase.php';
        require_once __DIR__ . '/firebase_push.php';

        $firebase = new Firebase();
        $push = new Push();

        // optional payload
        $payload = array();
        $payload['team'] = 'India';
        $payload['score'] = '5.6';
        
        $push_type = "";
        $title ="";
        $message = "";
        $include_image="";
        $regId = "";
if($_SERVER['REQUEST_METHOD'] == "POST")
 {
       $title = $decoded["title"];
        $message = $decoded["message"];
        $push_type = $decoded["push_type"];
        $include_image = $decoded["include_image"];
        $mobile_name = $decoded["product_name"];
        $notification_type = $decoded["notification_type"];
        $regId = $decoded["regId"];

}
if($_SERVER['REQUEST_METHOD'] == 'GET')
{
 $title = isset($_GET['title']) ? $_GET['title'] : '';
        $message = isset($_GET['message']) ? $_GET['message'] : '';
	$push_type = isset($_GET['push_type']) ? $_GET['push_type'] : '';
        $include_image = isset($_GET['include_image']) ? TRUE : FALSE;
	$regId = $_GET['regId'];
	$mobile_name = $_GET["product_name"];
        $notification_type = $_GET["notification_type"];
	
}
       $data = array();
       $query = "Select * from khurana_sales_stock where Name = '".$mobile_name."'";
       $result_product = mysqli_query($conn, $query);
       if(mysqli_num_rows($result_product) > 0 )
       {
       while ($row = mysqli_fetch_array($result_product)) {
         $data["mobile_name"] = $row["Name"];
       $data["mobile_brand"] = $row["Brand"];
       $data["mobile_stock"] = $row["Stock"];
       $data["mobile_mop_price"] = $row["Price_MOP"];
       $data["mobile_ks_price"] = $row["Price_KS"];
       $data["mobile_image_links"] = $row["links"];
       $data["notification_type"] = $notification_type;
       $data["notification_product_id"] = $row["product_id"];
          }
      
       }

       $payload["mobile_info"] = $data;

        $push->setTitle($title);
        $push->setMessage($message);
        if ($include_image == "TRUE") {
            $push->setImage('https://i.pinimg.com/originals/e1/5c/1d/e15c1dabbe7afc78d16ca740688a24d4.jpg');
        } else {
            $push->setImage('');
        }
        $push->setIsBackground(FALSE);
        $push->setPayload($payload);


        $json = '';
        $response = '';

        if ($push_type == 'topic') {
            $json = $push->getPush();
            $response = $firebase->sendToTopic('global', $json);
        } else if ($push_type == 'individual') {
            $json = $push->getPush();
            $result['in indivisual'] = "true";
            $response = $firebase->send($regId, $json);
        }
        
         if ($response != '') {
           $success = "true";
           $result["success"] = $success;
           echo json_encode($result);
           }
           else
           {
           $success = "false";
           $result["success"] = $success;
           echo json_encode($result2);
          } 
        ?>