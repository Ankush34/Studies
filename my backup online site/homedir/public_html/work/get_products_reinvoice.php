<?php

$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");

$invoice_number  = $_GET['sales_order_invoice_number'];

$query_bought_products = "select * from khurana_sales_orders where sales_order_invoice_number  = $invoice_number  ";

$bought_products = mysqli_query($conn, $query_bought_products);

$product_names = array();
$customer_name = "";
$customer_phone = array();
$invoice_date = "";
$customer_address = "";
$promoter_email = "";
$total_payment  = "" ;
$customer_gst  = "";
$batch_selected = array();
$bought_count = array();
$cash_payment = 0;
$finance_payment = 0;
$card_payment = 0;
$paytm_payment = 0;
$cheque_amount = 0;
$paytm_amount =0;
$card_amount = 0;
$cash_amount = 0;
$finance_amount = 0;
$financer = "";
if(mysqli_num_rows($bought_products)>0)
{
$i =0 ;
while($row = mysqli_fetch_array($bought_products))
{
 $product_names[$i] = $row["sales_order_product_name"];
 $customer_name = $row["sales_order_end_user_name"];
 $customer_phone = $row["sales_order_end_user_phone"];
 $invoice_date = $row["sales_order_date"];
 $customer_address  = $row["sales_order_address"];
 $promoter_email = $row["sales_order_customer_email"];
 $total_payment = $row["sales_order_price"];
 $customer_gst = $row["sales_order_end_user_gst_no"];
 $batch_selected[$i] = $row["batches_selected"];
 $bought_count[$i] = $row["sales_order_quantity"];
 $cash_amount = $row["cash_payment_amount"];
 $finance_payment = $row["finance_payment_id"];
 $cheque_payment = $row["cheque_payment_id"];
 $card_payment = $row["card_payment_id"];
 $paytm_payment = $row["paytm_payment_id"];
 
 $i = $i+1;
}
}

if($paytm_payment != -1)
{
  $paytm_query = "select paytm_payment_amount from khurana_sales_paytm_payments where id = $paytm_payment";
  $paytm_result = mysqli_query($conn,$paytm_query);
  $paytm_row  = mysqli_fetch_assoc($paytm_result);
  $paytm_amount = $paytm_row["paytm_payment_amount"];
  
}
if($finance_payment != -1)
{
 $finance_query = "select finance_amount,financer from khurana_sales_finance_payment where id = $finance_payment";
  $finance_result = mysqli_query($conn,$finance_query);
  $finance_row  = mysqli_fetch_assoc($finance_result);
  $finance_amount = $finance_row["finance_amount"];
  $financer = $finance_row["financer"];
}
if($cheque_payment != -1)
{
 $cheque_query = "select cheque_amount from khurana_sales_cheque_payments where id = $cheque_payment";
  $cheque_result = mysqli_query($conn,$cheque_query);
  $cheque_row  = mysqli_fetch_assoc($cheque_result);
  $cheque_amount = $cheque_row["cheque_amount"];
}
if($card_payment != -1)
{
 $card_query = "select card_payment_amount from khurana_sales_card_payment where id = $card_payment";
  $card_result = mysqli_query($conn,$card_query);
  $card_row  = mysqli_fetch_assoc($card_result);
  $card_amount = $card_row["card_payment_amount"];
}
$product_hash =array();
$main_products_hash = array();
for($k = 0 ; $k < sizeof($product_names); $k++)
{
     $product_stock_query = "select * from khurana_sales_stock where Name = '$product_names[$k]'";
     $product_info = mysqli_query($conn, $product_stock_query);
     $product_row = mysqli_fetch_assoc($product_info);
     $product_hash["batches_selected"] = $batch_selected[$k];
     $product_hash["product_name"] =  $product_row["Name"];
     $product_hash["price_mop"] = $product_row["Price_MOP"];
     $product_hash["product_count"] = $bought_count[$k];
     $product_hash["product_hsn"] = $product_row["Product_HSN"];
     array_push($main_products_hash, $product_hash);
}

$json_data = array();
$json_data["customer_name"] = $customer_name;
$json_data["customer_phone"] = $customer_phone;
$json_data["invoice_date"]= $invoice_date;
$json_data["customer_address"] = $customer_address;
$json_data["promoter_email"] = $promoter_email;
$json_data["total_payment"] = $total_payment;
$json_data["customer_gst"] = $customer_gst;
$json_data["products_info"] = $main_products_hash;
$json_data["cash_payment"] =$cash_amount;
$json_data["paytm_payment"] = $paytm_amount;
$json_data["card_payment"]  = $card_amount;
$json_data["finance_payment"] = $finance_amount;
$json_data["cheque_payment"] = $cheque_amount;
$json_data["financer"] = $financer;


echo json_encode([$json_data]);




?>