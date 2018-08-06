 <?php
$conn = mysqli_connect("localhost", "KhuranaSales", "9270481042khurana") or die(mysqli_error());
$db = mysqli_select_db($conn, "KhuranaSalesRastapeth");

 date_default_timezone_set('Asia/Kolkata'); 
 
 $content = trim(file_get_contents("php://input"));
 $decoded = json_decode($content, true);
 $user_email = $decoded['user_email'];
 $payment = $decoded['payment_type'];
 if($payment == 'Cash')
 {
 $payment_amount = $decoded['cash_payment_amount'];
 
 $data = array();
 $data["amount"] = $payment_amount;
 $query = "update khurana_sales_orders set cash_payment_amount = '$payment_amount' where sales_order_customer_email = '$user_email' and sales_order_status = 'VIEWED' ";
 $result = mysqli_query($conn, $query);
 if($result)
 {
 $data['success'] = true;
 }
 echo json_encode($data);
 }
 
 
 if($payment == 'Card')
 {
 $card_no = $decoded['card_no'];
 $name_on_card = $decoded['name_on_card'];
 $card_payment_amount = $decoded['card_payment_amount'];
 $card_bank_name = $decoded['card_bank_name'];
 $data = array();
 $data["amount"] = $card_payment_amount;
 $data["card_no"] = $card_no;
 $data["name_on_card"]= $name_on_card;
 $data['card_bank_name'] = $card_bank_name;
 $date_of_payment = date('Y-m-d');
 $query_insert = "insert into khurana_sales_card_payment (card_number, name_on_card, card_payment_amount, card_bank_name, Date) values ('$card_no',  '$name_on_card', '$card_payment_amount', '$card_bank_name', '$date_of_payment')";
 $result_of_insert = mysqli_query($conn, $query_insert);
 if($result_of_insert)
{
$data['inserted'] = true;
$last_id = $conn->insert_id;
$data['inserted_id'] = $last_id;
$update_order_query = "update khurana_sales_orders set card_payment_id = '$last_id' where sales_order_customer_email = '$user_email' AND sales_order_status = 'VIEWED'";
$result_of_update_query  = mysqli_query($conn, $update_order_query);
if($result_of_update_query)
{
$date['update_in_order'] = true;
}
} 
 echo json_encode($data);
}
 
 
 if($payment == 'Cheque')
 {
 $cheque_number = $decoded['cheque_number'];
 $cheque_bank_name = $decoded['cheque_bank_name'];
 $cheque_amount = $decoded['cheque_amount'];
 $cheque_beneficiary = $decoded['cheque_beneficiary'];
 $data = array();
 $data["cheque_number"] = $cheque_number;
 $data["cheque_bank_name"] = $cheque_bank_name;
 $data["cheque_amount"]= $cheque_amount;
 $data['cheque_beneficiary'] = $cheque_beneficiary;
  $date_of_payment = date('Y-m-d');
 $query_insert = "insert into khurana_sales_cheque_payments(cheque_number, cheque_amount, cheque_beneficiary, cheque_bank_name, Date) values ('$cheque_number',  '$cheque_amount', '$cheque_beneficiary', '$cheque_bank_name', '$date_of_payment')";
 $result_of_insert = mysqli_query($conn, $query_insert);
 if($result_of_insert)
{
$data['inserted'] = true;
$last_id = $conn->insert_id;
$data['inserted_id'] = $last_id;

$update_order_query = "update khurana_sales_orders set cheque_payment_id = '$last_id' where sales_order_customer_email = '$user_email' AND sales_order_status = 'VIEWED'";
$result_of_update_query  = mysqli_query($conn, $update_order_query);
if($result_of_update_query)
{
$date['update_in_order'] = true;
}
}
 echo json_encode($data);
}


 if($payment == 'Paytm')
 {
 $paytm_number = $decoded['paytm_number'];
 $paytm_account_name = $decoded['paytm_account_name'];
 $paytm_amount = $decoded['paytm_amount'];
 $data = array();
 $data["paytm_number"] = $paytm_number;
 $data["paytm_account_name"] = $paytm_account_name;
 $data["paytm_amount"] = $paytm_amount;
  $date_of_payment = date('Y-m-d');
  $query_insert = "insert into khurana_sales_paytm_payments(paytm_payment_number, paytm_payment_account_name, paytm_payment_amount, Date) values ('$paytm_number',  '$paytm_account_name', '$paytm_amount', '$date_of_payment')";
 $result_of_insert = mysqli_query($conn, $query_insert);
 if($result_of_insert)
{
$data['inserted'] = true;
$last_id = $conn->insert_id;
$data['inserted_id'] = $last_id;

$update_order_query = "update khurana_sales_orders set paytm_payment_id = '$last_id' where sales_order_customer_email = '$user_email' AND sales_order_status = 'VIEWED'";
$result_of_update_query  = mysqli_query($conn, $update_order_query);
if($result_of_update_query)
{
$date['update_in_order'] = true;
}
}
 echo json_encode($data);
 }
 
 
 
 if($payment == 'Finance')
 {
 $finance_beneficiary = $decoded['finance_beneficiary'];
 $financer_name = $decoded['financer_name'];
 $finance_amount = $decoded['finance_amount'];
 $down_payment_amount = $decoded['processing_payment_amount'];
 $finance_file_number = $decoded['finance_file_number'];
  $data = array();
 $data["finance_beneficiary"] = $finance_beneficiary;
 $data["financer_name"] = $financer_name;
 $data["finance_amount"] = $finance_amount;
  $date_of_payment = date('Y-m-d');
  $query_insert = "insert into khurana_sales_finance_payment(finance_beneficiary, financer, finance_amount, finance_down_payment, finance_file_number, Date) values ('$finance_beneficiary',  '$financer_name', '$finance_amount', '$down_payment_amount', '$finance_file_number', '$date_of_payment')";
 $result_of_insert = mysqli_query($conn, $query_insert);
 if($result_of_insert)
{
$data['inserted'] = true;
$last_id = $conn->insert_id;
$data['inserted_id'] = $last_id;

$update_order_query = "update khurana_sales_orders set finance_payment_id = '$last_id' where sales_order_customer_email = '$user_email' AND sales_order_status = 'VIEWED'";
$result_of_update_query  = mysqli_query($conn, $update_order_query);
if($result_of_update_query)
{
$date['update_in_order'] = true;
}
}
 echo json_encode($data);
 }

?>