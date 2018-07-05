<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/2.3.2/jspdf.plugin.autotable.js"></script>
<script src="http://khuranasales.co.in/work/khurana_sales_logo.js"></script>	
<script src="https://momentjs.com/downloads/moment.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore-min.js"></script>
<?php
if($_SERVER['REQUEST_METHOD'] == 'GET')
{
      $customer_email = $_GET["customer_email"];
      $customer_name = $_GET["customer_name"];
      $customer_phone = $_GET["customer_phone"];
      $promoter_email = $_GET["promoter_email"];
      $customer_gst = $_GET["customer_gst"];
}
if($_SERVER['REQUEST_METHOD'] == 'POST')
{
      $customer_email = $_POST["customer_email"];
      $customer_name = $_POST["customer_name"];
      $customer_phone = $_POST["customer_phone"];
      $promoter_email = $_POST["promoter_email"];
      $customer_gst = $_POST["customer_gst"];
}
?>
<script type="text/javascript">
var invoice_data = "";
var promoter_email = '<?php echo $promoter_email; ?>';
$.ajax({
  method: "GET",
  async: false,
  url: "http://khuranasales.co.in/work/khurana_sales_invoice_data.php",
  data: { email: promoter_email}
})
  .done(function( recieved ) {
    invoice_data = recieved
  });
var customer_data = "";
$.ajax({
  method: "GET",
  async: false,
  url: "http://khuranasales.co.in/work/khurana_sales_invoice_customer_info.php",
  data: { email: promoter_email }
})
  .done(function( recieved ) {
    customer_data = recieved
  });
console.log(customer_data)
var orders_recieved = JSON.parse(invoice_data);
var customer_recieved = JSON.parse(customer_data);

var order_recieved_table_columns = ["Order Date", "Product Name", "Quantity", "MRP Price","MOP Price","KS Price"];
var order_recieved_table_rows = new Array();
let taxable_amount = 0;
for (var order of orders_recieved) {
order_recieved_table_rows.push([order["order_date"], order["product_name"], order["order_quantity"], order["order_per_price_mop"], order["order_per_price_mrp"], order["order_per_price_mop"]])
taxable_amount = parseInt(order["order_per_price_ks"]) + parseInt(taxable_amount) ; 
 }
var doc = new jsPDF('p', 'pt', 'a4');
doc.rect(5, 5, doc.internal.pageSize.width - 10, doc.internal.pageSize.height - 10, 'S');
console.log(order_recieved_table_rows)
var customer_column = ["Bill To"];
console.log(customer_recieved[0]["user_name"]);
var end_user_name = '<?php echo $customer_name ; ?>'  ;
var end_user_contact = '<?php echo $customer_phone ; ?>' ;
var end_user_gst = '<?php echo $customer_gst ; ?>' ;
var customer_row = [["Customer Name: "+end_user_name], ["Contact No: "+end_user_contact], ["Customer GST No:"+end_user_gst]];
doc.text("Retail invoice", 240, 30)

doc.rect(15,60,doc.internal.pageSize.width - 30,100,"S")

doc.rect(270,181,doc.internal.pageSize.width - 285,64,"S")

doc.addImage(image_data, 'JPEG',30, 70, 80, 80);

doc.setFont("helvetica");
doc.setFontType("bold");
doc.setFontSize(13);

doc.text(doc.internal.pageSize.width - 140, 80, 'Khurana Sales');

doc.setFont("courier");
doc.setFontType("normal")
doc.setFontSize(9);

doc.text("Shop no 11 kumar sadan building near apollo theatre, 444 somwar peth pune", doc.internal.pageSize.width - 420, 100);

doc.text("Phone no: 7304123456, Email: khuranasales2015@gmail.com", doc.internal.pageSize.width - 325, 120);

doc.text("GSTIN: 27AAOHM4180P!ZE, State: 27- Maharashtra",doc.internal.pageSize.width - 285, 140)

doc.autoTable(customer_column, customer_row, {margin: {top: 180,left: 13},styles: {overflow: 'linebreak', columnWidth: 255}});

doc.autoTable(order_recieved_table_columns, order_recieved_table_rows, {margin: {top: 285,left: 13}});

invoice_number_parts = new Array ;
let todays_date = moment().format("l");
invoice_number_parts.push(todays_date.split("/").join(""));
invoice_number_parts.push("CN"+customer_recieved[0]["user_id"]);
invoice_number_parts.push("INV1");

doc.text("Invoice Number: "+invoice_number_parts.join("-"),doc.internal.pageSize.width -190,220);

doc.text("Todays Date: "+todays_date,doc.internal.pageSize.width - 130,240);

let tax_columns = ["Tax Type", "Taxable", "Tax %","Tax"]
tax = (taxable_amount * 6)/100 ;
let tax_rows = [["CGST",taxable_amount,"6.0%",tax],["SGST",taxable_amount,"6.0%",tax]]
doc.autoTable(tax_columns, tax_rows, {margin: {top: 450,left: 13},styles: {overflow: 'linebreak', columnWidth:75}});

subtotal = parseInt(taxable_amount) ;
total = parseInt(taxable_amount) + parseInt(tax) ;
total_columns = ["Billing Amount"];
total_rows = [["Subtotal:    "+subtotal],["Total Amount:    "+total]];
doc.autoTable(total_columns, total_rows, {margin: {top: 450,left: 321},styles: {overflow: 'linebreak', columnWidth:245}});

var a = ['','one ','two ','three ','four ', 'five ','six ','seven ','eight ','nine ','ten ','eleven ','twelve ','thirteen ','fourteen ','fifteen ','sixteen ','seventeen ','eighteen ','nineteen '];
var b = ['', '', 'twenty','thirty','forty','fifty', 'sixty','seventy','eighty','ninety'];

function inWords (num) {
    if ((num = num.toString()).length > 9) return 'overflow';
    n = ('000000000' + num).substr(-9).match(/^(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
    if (!n) return; var str = '';
    str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'crore ' : '';
    str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'lakh ' : '';
    str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'thousand ' : '';
    str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'hundred ' : '';
    str += (n[5] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'only ' : '';
    return str;
}
let amount_in_words = inWords(total);

doc.autoTable(["Invoice Amount In Words"], [[amount_in_words]], {margin: {top: 530,left: 13},styles: {overflow: 'linebreak', columnWidth:300}});

doc.autoTable(["Terms And Conditions"], [["In case of any issue please visit the nearest authorised service center"]], {margin: {top: 600,left: 13},styles: {overflow: 'linebreak', columnWidth:300}});

doc.autoTable(["Bank Details Khurana Sales"], [["Bank Name: IndusInd Bank"],["Account No: 259913131313"],["IFSC Code: INDB0000002"]], {margin: {top: 670,left: 13},styles: {overflow: 'linebreak', columnWidth:300}});

doc.save("invoice.pdf");

$.ajax({
  method: "POST",
  async: false,
  url: `http://khuranasales.co.in/work/upload_invoice.php?email= ${promoter_email}`,
  data: { data: doc.output()}
})
</script>
<?php  echo "success"; ?>