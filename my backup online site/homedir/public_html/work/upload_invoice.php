<?php
if(!empty($_POST['data'])){
    $data = $_POST['data'];
    $email = $_GET["email"];
    $fname = $email." ".date("Y-m-d")." ".time()." invoice.pdf"; // name the file
    $file = fopen("invoices_generated/" .$fname, 'w'); // open the file path
    fwrite($file, $data); //save data
    fclose($file);
} else {
    echo "No Data Sent";
}
?>