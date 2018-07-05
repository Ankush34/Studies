<?php
 
/*
 * Following code will list all the products
 */
 $response = array();
$response1=array();
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$product = array();
$search = isset($_GET['search']) && $_GET['search'] != '' ? $_GET['search'] : "";
$query = "SELECT Name FROM khurana_sales_stock where Name Like '".$search."%' ";
$result = mysqli_query($conn, $query) or die(mysqli_error());
if (mysqli_num_rows($result)>0) {
    while ($row = mysqli_fetch_array($result)) {
        $product2["Name"] = $row["Name"];
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



