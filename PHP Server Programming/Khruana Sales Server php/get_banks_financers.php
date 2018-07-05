<?php
 
$type = $_GET['info'];
if($type == 'Banks')
{
$query = "select * from banks";
}
if($type == 'Financers')
{
$query = "select * from financers";
}
$con = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($con,"KhuranaSalesRastapeth") or die(mysqli_error());


$result = mysqli_query($con,$query) or die(mysqli_error($con));
if (mysqli_num_rows($result)>0) {
    $response1=array();
    if($type == 'Financers')
    {
     while ($row = mysqli_fetch_array($result)) {
        $product2["financer_name"] = $row["financer_name"];
        array_push($response1,$product2);
          }
    }
    else
    {
     while ($row = mysqli_fetch_array($result)) {
        $product2["bank_name"] = $row["bank_name"];
        array_push($response1,$product2);
          }
    }
echo json_encode($response1);
}
else {
    $response = array();
    $response["success"] = 0;
    $response["message"] = "No banks or financers found";
    echo json_encode($response);
}
?>



