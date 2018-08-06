<?php 
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$target_path = "uploads/";
$name = $_GET["product_name"];
echo $name;
$target_path = $target_path . basename( $_FILES['uploadedfile']['name']); 

if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $target_path)) { 
    echo "The file ". basename( $_FILES['uploadedfile']['name'])." has been uploaded"; 
} else{ 
    echo "There was an error uploading the file, please try again!"; 
}

$query = "select * from khurana_sales_stock where Name = '$name'";
$result = mysqli_query($conn, $query);
$result_row = mysqli_fetch_assoc($result);
$links = $result_row["links"];
$links_array = explode(",",$links);
array_push($links_array,"http://khuranasales.co.in/work/uploads/".$_FILES['uploadedfile']['name']);
$links_new = join(",",$links_array);
$query_update = "update khurana_sales_stock set links = '$links_new' where Name = '$name'";
$result_update = mysqli_query($conn, $query_update);
if($result_update)
{
$result = array();
$response = array();
$response["success"] = "true";
array_push($result, $response);
echo json_encode($response);
}
else
{
$result = array();
$response = array();
$response["success"] = "false";
array_push($result, $response);
echo json_encode($response);
}
?>