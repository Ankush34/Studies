
<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
    $email = $_GET['email'];
    $password = $_GET['password'];

    // connecting to db
    $conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
    $db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
    
$query = "select * from khurana_sales_access where user_email='".$email."' and user_password='".$password."' and permission='Yes'";
$result = mysqli_query($conn,$query) or die(mysqli_error($conn));;
if(mysqli_num_rows($result)>0)
{
$row = mysqli_fetch_assoc($result);
$response = array();
$response1 = array();
$response["success"]="true";
array_push($response1,$response);
$customer_info =  array();
$customer_info["customer_id"] = $row["user_id"];
$customer_info['phone_number'] = $row['user_phone'];
$customer_info['customer_type'] = $row['user_type'];
$customer_info['customer_shop_name'] = $row['user_shop_name'];
$customer_info['user_name'] = $row['user_name'];
array_push($response1,$customer_info);	
echo json_encode($response1);
}
else
{
$response = array();
$response1 = array();
$response["success"] = "false";
array_push($response1,$response);
echo json_encode($response1);
}
?>
