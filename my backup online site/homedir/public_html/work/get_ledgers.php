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
$query = "SELECT * FROM khurana_sales_ledgers where (Name Like '".$search."' OR Mobile LIKE '".$search."' OR Phone Like '".$search."') AND type = 'LEDGER' GROUP BY Email ";
$result = mysqli_query($conn, $query) or die(mysqli_error());
if (mysqli_num_rows($result)>0) {
    while ($row = mysqli_fetch_array($result)) {
        $product2["Name"] = $row["Name"];
        $product2["Mobile"] = $row["Mobile"];
        $product2["Email"] = $row["Email"];
        $product2["GSTIN"] = $row["GSTIN"];
        $product2["Address"] = $row["Address"];
        $product2["Parent"] = $row["Parent"];
        $product2["State"] = $row["State"];
        $product2["Phone"] = $row["Phone"];
        $product2["type"] = $row["Type"];
        array_push($response1,$product2);

          }

echo json_encode($response1);


}
else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No ledgers found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>


