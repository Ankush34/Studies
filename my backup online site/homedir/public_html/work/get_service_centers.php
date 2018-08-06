<?php
 
/*
 * Following code will list all the users
 */
$response = array();
$response1=array();
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$product = array();
$query = "SELECT * FROM service_centres";
$result = mysqli_query($conn, $query) or die(mysqli_error($conn));
if (mysqli_num_rows($result)>0) {
    while ($row = mysqli_fetch_array($result)) {
        $product2["name"] = $row["shop_name"];
        $product2["address"] = $row["address"];
        $product2["brand"] = $row["brand"];
        $product2["phone"] = $row["phone"];
        $product2["opens"] = $row["opens_at"];
        $product2["closes"] = $row["closes_at"];
        $product2["success"] = "true";
        array_push($response1,$product2);
       }
echo json_encode($response1);
}
else {
    // no products found
    $response["success"] = "false";
    $response["message"] = "No service centers found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>
