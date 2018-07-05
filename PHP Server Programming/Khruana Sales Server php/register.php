<?php
 
   $conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
   $db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$response = array();
    $Name = $_POST['Name'];
    $Shop = $_POST['Shop'];
    $Address = $_POST['Address'];    
    $EmailId = $_POST['EmailId'];
    $Password=$_POST['Password'];
    $Pan=$_POST['Pan'];
    $Phone = $_POST['Phone'];
    $Gst = $_POST['GST'];
    $userType = "Local" ;
    $permission = "Yes" ;
$q=mysqli_query($conn, "insert into khurana_sales_access values('".$EmailId."','".$Password."','".$userType."','".$permission."','".$Shop."','".$Address."','".$Name."','".$Pan."','".$Phone."','".$Gst."')");
if($q)
{
echo "successfully updated";
}
?>
