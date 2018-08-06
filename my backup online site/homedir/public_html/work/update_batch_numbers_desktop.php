<?php
$conn = mysqli_connect("localhost","KhuranaSales","9270481042khurana","KhuranaSalesRastapeth") or die('Unable to Connect');
$db = mysqli_select_db($conn,"KhuranaSalesRastapeth") or die(mysqli_error());
$data_fetched = json_decode(file_get_contents('php://input'), true);
$array = $data_fetched["update"];

foreach($array as $data)
{
echo $data["batch_id"];
$batch_id = $data["batch_id"];
$query  = "update batch_numbers set ";
foreach($data["batch_details"] as $key => $value)
{
  $query = $query." `$key` = '".$value."',";
}
$query = substr($query, 0, -1);
$query = $query." where id = $batch_id";
$result = mysqli_query($conn, $query);
if($result)
{
 $status["success"] = "true";
 $response = array();
 array_push($response, $status);
 echo json_encode($response);
}
else
{
 $status["success"] = "false";
 $response = array();
 array_push($response, $status);
 echo json_encode($response);
}
}

?>