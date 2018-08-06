<?php
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");

$sql = "SELECT COLUMN_NAME 
		FROM INFORMATION_SCHEMA.COLUMNS
		WHERE TABLE_NAME =  'batch_numbers'
		ORDER BY ORDINAL_POSITION
		LIMIT 0 , 30 ";
		
$result = mysqli_query($conn, $sql);
$data = array();

if(mysqli_num_rows($result) > 0)
{
$i = 0 ;
while($row = mysqli_fetch_array($result))
{

if($i == 0 || $i == 1)
{
 $i = $i+1;
continue;
}
 $columns["Column"] = $row["COLUMN_NAME"];
 array_push($data, $columns);
  $i = $i+1;
}

echo json_encode($data);
}


?>