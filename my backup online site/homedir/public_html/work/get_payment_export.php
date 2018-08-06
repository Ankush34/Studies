<?php
date_default_timezone_set('Asia/Kolkata'); 
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");
$data = json_decode(file_get_contents('php://input'), true);
$array = $data["payment_types"];
$export = array();
foreach($array as $data_parsed)
{
 $payment_type = $data_parsed["payment_type"];
 if($payment_type == "Cash")
 {
   $date_today = date('d-m-Y');
   $sql = "select * from khurana_sales_orders where sales_order_date = '$date_today' GROUP BY sales_order_invoice_number";
   $result = mysqli_query($conn, $sql);
   $data = array();
   while($row = mysqli_fetch_array($result))
   {
   $product = array();
     $product["cash_payment_amount"] = $row["cash_payment_amount"];
     $promoter_email = $row["sales_order_customer_email"];
     $sql_user = "select user_name from khurana_sales_access where user_email = '$promoter_email'";
     $result_user  =mysqli_query($conn, $sql_user);
     $user_row = mysqli_fetch_assoc($result_user);
     $product["promoter_name"] = $user_row["user_name"];
     $product["customer_name"] = $row["sales_order_end_user_name"];
     $product["sales_order_number"] = $row["sales_order_invoice_number"];
     array_push($data, $product);
     
   }
   $export["cash_payments"] = $data;   
 }
 else if($payment_type == "Card")
 {
   $date_today = date('d-m-Y');
   $sql = "select * from khurana_sales_orders where sales_order_date = '$date_today' AND card_payment_id > 0 GROUP BY sales_order_invoice_number";
   $result = mysqli_query($conn, $sql);
   $data = array();
   while($row = mysqli_fetch_array($result))
   {
   $product = array();
   $card_payment_id = $row["card_payment_id"];
     $payment_info_sql = "select * from khurana_sales_card_payment where id = $card_payment_id";
     $result_payment_info = mysqli_query($conn, $payment_info_sql);
     $row_payment_info = mysqli_fetch_assoc($result_payment_info);
     
     $product["card_payment_amount"] = $row_payment_info["card_payment_amount"];
     $product["card_bank_name"] = $row_payment_info["card_bank_name"];
     $promoter_email = $row["sales_order_customer_email"];
     $sql_user = "select user_name from khurana_sales_access where user_email = '$promoter_email'";
     $result_user  =mysqli_query($conn, $sql_user);
     $user_row = mysqli_fetch_assoc($result_user);
     $product["promoter_name"] = $user_row["user_name"];
     $product["customer_name"] = $row["sales_order_end_user_name"];
     $product["sales_order_number"] = $row["sales_order_invoice_number"];
     array_push($data, $product);
     
   }
   $export["card_payments"] = $data;  
 }
 else if($payment_type == "Cheque")
 {
   $date_today = date('d-m-Y');
   $sql = "select * from khurana_sales_orders where sales_order_date = '$date_today' AND card_payment_id > 0 GROUP BY sales_order_invoice_number";
   $result = mysqli_query($conn, $sql);
   $data = array();
   while($row = mysqli_fetch_array($result))
   {
      $product = array();
      $cheque_payment_id = $row["cheque_payment_id"];
     $payment_info_sql = "select * from khurana_sales_cheque_payments where id = $cheque_payment_id";
     $result_payment_info = mysqli_query($conn, $payment_info_sql);
     $row_payment_info = mysqli_fetch_assoc($result_payment_info);
     $product["cheque_payment_amount"] = $row_payment_info["cheque_amount"];
     $product["cheque_bank_name"] = $row_payment_info["cheque_bank_name"];
     
     $promoter_email = $row["sales_order_customer_email"];
     $sql_user = "select user_name from khurana_sales_access where user_email = '$promoter_email'";
     $result_user  =mysqli_query($conn, $sql_user);
     $user_row = mysqli_fetch_assoc($result_user);
     $product["promoter_name"] = $user_row["user_name"];
     $product["customer_name"] = $row["sales_order_end_user_name"];
     $product["sales_order_number"] = $row["sales_order_invoice_number"];
     array_push($data, $product);
     
   }
   $export["cheque_payments"] = $data;  
 }
 else if($payment_type == "Finance")
 {
   $date_today = date('d-m-Y');
   $sql = "select * from khurana_sales_orders where sales_order_date = '$date_today' AND finance_payment_id > 0 GROUP BY sales_order_invoice_number";
   $result = mysqli_query($conn, $sql);
   $data = array();
   while($row = mysqli_fetch_array($result))
   {
      $product = array();
      $finance_payment_id = $row["finance_payment_id"];
     $payment_info_sql = "select * from khurana_sales_finance_payment where id = $finance_payment_id";
     $result_payment_info = mysqli_query($conn, $payment_info_sql);
     $row_payment_info = mysqli_fetch_assoc($result_payment_info);
     
     $product["finance_payment_amount"] = $row_payment_info["finance_amount"];
     $product["financer"] = $row_payment_info["financer"];
     $product["finance_file_number"] = $row_payment_info["finance_file_number"];
     $product["processing_fees"] = $row_payment_info["finance_down_payment"];
     
     
     $promoter_email = $row["sales_order_customer_email"];
     $sql_user = "select user_name from khurana_sales_access where user_email = '$promoter_email'";
     $result_user  =mysqli_query($conn, $sql_user);
     $user_row = mysqli_fetch_assoc($result_user);
     $product["promoter_name"] = $user_row["user_name"];
     $product["customer_name"] = $row["sales_order_end_user_name"];
     $product["sales_order_number"] = $row["sales_order_invoice_number"];
     array_push($data, $product);
     
   }
   $export["finance_payments"] = $data;  
 }
 else if($payment_type == "Paytm")
 {
  $date_today = date('d-m-Y');
   $sql = "select * from khurana_sales_orders where sales_order_date = '$date_today' AND paytm_payment_id > 0 GROUP BY sales_order_invoice_number";
   $result = mysqli_query($conn, $sql);
   $data = array();
   while($row = mysqli_fetch_array($result))
   {
      $product = array();
      $paytm_payment_id = $row["paytm_payment_id"];
     $payment_info_sql = "select * from khurana_sales_paytm_payments where id = $paytm_payment_id";
     $result_payment_info = mysqli_query($conn, $payment_info_sql);
     $row_payment_info = mysqli_fetch_assoc($result_payment_info);
     
     $product["paytm_payment_amount"] = $row_payment_info["paytm_payment_amount"];
     
     $promoter_email = $row["sales_order_customer_email"];
     $sql_user = "select user_name from khurana_sales_access where user_email = '$promoter_email'";
     $result_user  =mysqli_query($conn, $sql_user);
     $user_row = mysqli_fetch_assoc($result_user);
     $product["promoter_name"] = $user_row["user_name"];
     $product["customer_name"] = $row["sales_order_end_user_name"];
     $product["sales_order_number"] = $row["sales_order_invoice_number"];
     array_push($data, $product);
     
   }
   $export["paytm_payments"] = $data;
 }
}
$export["success"] = "true";
echo json_encode($export);
?>