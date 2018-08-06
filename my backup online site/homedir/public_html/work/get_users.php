<?php
 
/*
 * Following code will list all the users
 */
 $response = array();
$response1=array();
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$product = array();
$query = "SELECT * FROM khurana_sales_access where user_type != 'Customer' ";
$result = mysqli_query($conn, $query) or die(mysqli_error());
if (mysqli_num_rows($result)>0) {
    while ($row = mysqli_fetch_array($result)) {
        $product2["name"] = $row["user_name"];
        $product2["contact"] = $row["user_phone"];
        $product2["user_type"] = $row["user_type"];
        $product2["shop_name"] = $row["user_shop_name"];
        $product2["user_id"] = $row["user_id"];
        $product2["firebaseId"] = $row["firebase_id"];
        array_push($response1,$product2);
       }
echo json_encode($response1);
}
else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>



