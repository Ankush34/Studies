package in.co.khuranasales.khuranasales.manageByInventoryModule.purchaseVoucherDashboardDataModels;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class purchaseVoucherParser {
	public static ArrayList<purchaseVoucher> purchase_vouchers = new ArrayList<>();
	
	public static void upload_purchase_vouchers()
	{
		JSONArray purchase_vouchers_array = new JSONArray();
		JSONObject purchase_vouchers_json_object = new JSONObject();
		
		for(int i = 0; i < purchase_vouchers.size(); i++)
		{
			JSONObject purchase_voucher_object = new JSONObject();
			purchaseVoucher purchase_voucher_instance = purchase_vouchers.get(i);
			
			
			try {
				purchase_voucher_object.put("purchase_address_location", purchase_voucher_instance.getPurchase_address_location().replaceAll("\n", " "));
				purchase_voucher_object.put("voucher_date", purchase_voucher_instance.getPurchase_date());
				purchase_voucher_object.put("company_state", purchase_voucher_instance.getCompany_state());
				purchase_voucher_object.put("purchase_country", purchase_voucher_instance.getPurchase_country());
				purchase_voucher_object.put("purchase_party_gstin", purchase_voucher_instance.getPurchase_party_gstin());
				purchase_voucher_object.put("purchase_party_name", purchase_voucher_instance.getPurchase_party_name());
				purchase_voucher_object.put("voucher_type", purchase_voucher_instance.getPurchase_voucher_type());
				purchase_voucher_object.put("voucher_number", purchase_voucher_instance.getPurchase_voucher_number());
				purchase_voucher_object.put("purchase_party_ledger_name", purchase_voucher_instance.getPurchase_party_ledger_name());
				purchase_voucher_object.put("company_name", purchase_voucher_instance.getCompany_name());
				purchase_voucher_object.put("company_tag", purchase_voucher_instance.getCompany_tag());
				purchase_voucher_object.put("company_state", purchase_voucher_instance.getCompany_state());
				purchase_voucher_object.put("purchase_voucher_co_signee_gsting", purchase_voucher_instance.getCo_assignee_gst_number());
				purchase_voucher_object.put("purchase_voucher_place_of_supply", purchase_voucher_instance.getPurchase_place_of_supply());
				purchase_voucher_object.put("purchase_time_of_invoice", purchase_voucher_instance.getPurchase_time_of_invoice());
				purchase_voucher_object.put("purchase_voucher_co_signee_state_name", purchase_voucher_instance.getPurchase_co_assignee_state_name());				
				purchase_voucher_object.put("voucher_guid", purchase_voucher_instance.getPurchase_guid());
				purchase_voucher_object.put("voucher_created_by", purchase_voucher_instance.getPurchase_voucher_created_by());
				purchase_voucher_object.put("voucher_alter_id", purchase_voucher_instance.getPurchase_voucher_alter_id());
				purchase_voucher_object.put("voucher_master_id", purchase_voucher_instance.getPurchase_voucher_master_id());
				purchase_voucher_object.put("voucher_key", purchase_voucher_instance.getPurchase_voucher_key());
				purchase_voucher_object.put("co_signee_gstin", purchase_voucher_instance.getCo_assignee_gst_number());
				
				JSONArray purchase_voucher_ledgers_array = new JSONArray();
				for(int k = 0; k < purchase_voucher_instance.purchase_voucher_ledgers.size() ; k++)
				{
					purchaseVoucherLedger ledger_instance = purchase_voucher_instance.purchase_voucher_ledgers.get(k);
					JSONObject ledger_object = new JSONObject();
					
					ledger_object.put("ledger_name", ledger_instance.getLedger_name());
					ledger_object.put("ledger_amount", ledger_instance.getLedger_amount());
					
					purchase_voucher_ledgers_array.put(ledger_object);
				}
				purchase_voucher_object.put("purchase_voucher_ledgers", purchase_voucher_ledgers_array);
				
				JSONArray purchase_voucher_items = new JSONArray();
				for(int l = 0 ;l < purchase_voucher_instance.purchase_voucher_items.size(); l++)
				{
					JSONObject item = new JSONObject();
					purchaseVoucherProduct product = purchase_voucher_instance.purchase_voucher_items.get(l);
					item.put("product_name", product.getProduct_name());
					item.put("product_total_amount_spent", product.getTotal_amount_spent());
					item.put("product_total_quantity_purchase", product.getTotal_purchase_quantity());
					item.put("product_total_billed_quantity", product.getBilled_purchase_quantity());
					item.put("product_discount_amount", product.getDiscount_amount());
					item.put("product_item_price", product.getItem_price());
					item.put("item_ledger_name", product.getProduct_ledger_name());
					
					JSONArray batches_array = new JSONArray();
					for(int m = 0; m < product.purchase_voucher_item_batches.size() ; m++)
					{
						JSONObject batch = new JSONObject();
						purchaseVoucherItemBatch batch_instance = product.purchase_voucher_item_batches.get(m);
						batch.put("batch_number", batch_instance.getBatch_number());
						batch.put("batch_bought_location", batch_instance.getBatch_location());
						batch.put("batch_destination_location", batch_instance.getBatch_destination_location());
						batches_array.put(batch);
					}
					
					item.put("product_batches", batches_array);
					purchase_voucher_items.put(item);
				}
				
				purchase_voucher_object.put("purchase_voucher_items", purchase_voucher_items);
				purchase_vouchers_array.put(purchase_voucher_object);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		try {
			purchase_vouchers_json_object.put("purchase_vouchers",purchase_vouchers_array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(purchase_vouchers_json_object.toString());
	}
	public static void main(String args[])
	{
		try
		{
			String outputFile=  "/home/amura/Desktop/khurana_sales_report_rnd/purchase_day_book.xml";
			 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			 Document doc = dBuilder.parse(outputFile);
			 doc.getDocumentElement().normalize();
			 System.out.println("Root element :"+ doc.getDocumentElement().getNodeName());
			 NodeList nodes_inside_body =  doc.getElementsByTagName("BODY");
			 System.out.println("nodes count: "+nodes_inside_body.getLength());
			 NodeList nodes_inside_node_lists =  doc.getElementsByTagName("TALLYMESSAGE");
			 for(int i  = 0 ; i < nodes_inside_node_lists.getLength();i++)
			 {
				 Node node = nodes_inside_node_lists.item(i);
				 System.out.println("Size of tally tag"+node.getChildNodes().getLength());
				 for(int j = 0; j < node.getChildNodes().getLength();j++)
				 {
					 System.out.println("nodes in tally message: "+node.getChildNodes().item(j).getNodeName());
					 if(node.getChildNodes().item(j).getNodeName().equals("VOUCHER"))
						 {
						 	purchaseVoucher purchase_voucher = new purchaseVoucher();
							 Node voucher = node.getChildNodes().item(j);
							 System.out.println("size of voucher tag"+voucher.getChildNodes().getLength());
							 Element docElement = (Element)voucher;
							 NodeList address_nodes  = docElement.getElementsByTagName("BASICBUYERADDRESS.LIST");
							 if(address_nodes.getLength() > 0)
							 {
								 NodeList nodes_inside_address = address_nodes.item(0).getChildNodes();
								 String address = "";
								 for(int k = 0; k < nodes_inside_address.getLength();k++ )
								 {
									
									 System.out.println("address_node"+nodes_inside_address.item(k).getNodeName());
									 if(nodes_inside_address.item(k).getNodeName().equals("BASICBUYERADDRESS"))
									 {
										 System.out.println("address_inside: "+nodes_inside_address.item(k).getTextContent());			 
										 address = address + "\n "+nodes_inside_address.item(k).getTextContent();
									 }
								 }
								 System.out.println("Address Complete"+address);
								 purchase_voucher.setPurchase_address_location(address);
							 }
							 NodeList date_nodes  = docElement.getElementsByTagName("DATE");
							 NodeList date_nodes_inside = date_nodes.item(0).getChildNodes();
							 for(int l = 0; l < date_nodes_inside.getLength(); l ++)
							 {
								 System.out.println("date node: "+date_nodes_inside.item(l).getTextContent());
								 purchase_voucher.setPurchase_date(date_nodes_inside.item(l).getTextContent());
							 }
							 
							 NodeList guid_list = docElement.getElementsByTagName("GUID");
							 System.out.println("GUID:"+guid_list.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setPurchase_guid(guid_list.item(0).getChildNodes().item(0).getTextContent());
							 
							 NodeList state_list = docElement.getElementsByTagName("STATENAME");
							 System.out.println("STATE:"+state_list.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setPurchase_state(state_list.item(0).getChildNodes().item(0).getTextContent());
							 
							 NodeList gst_list = docElement.getElementsByTagName("PARTYGSTIN");
							 System.out.println("GSTIN:"+gst_list.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setPurchase_party_gstin(gst_list.item(0).getChildNodes().item(0).getTextContent());
							 
							 NodeList voucher_type_list = docElement.getElementsByTagName("VOUCHERTYPENAME");
							 System.out.println("Voucher Type:"+voucher_type_list.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setPurchase_voucher_type(voucher_type_list.item(0).getChildNodes().item(0).getTextContent());
							 
							 NodeList party_ledger_name_list = docElement.getElementsByTagName("PARTYLEDGERNAME");
							 System.out.println("Ledger Name:"+party_ledger_name_list.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setPurchase_party_ledger_name(party_ledger_name_list.item(0).getChildNodes().item(0).getTextContent());
							
							 NodeList voucher_number_list = docElement.getElementsByTagName("VOUCHERNUMBER");
							 System.out.println("VOUCHERNUMBER:"+voucher_number_list.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setPurchase_voucher_number(voucher_number_list.item(0).getChildNodes().item(0).getTextContent());
							
							 NodeList place_of_supply_list = docElement.getElementsByTagName("PLACEOFSUPPLY");
							 if(place_of_supply_list.getLength() > 0 && place_of_supply_list.item(0).getChildNodes().getLength() > 0)
							 {
								 System.out.println("Supply Place Name:"+place_of_supply_list.item(0).getChildNodes().item(0).getTextContent());
								 purchase_voucher.setPurchase_place_of_supply(place_of_supply_list.item(0).getChildNodes().item(0).getTextContent());		 
							 }
							 else
							 {
								 System.out.println("Supply Place Name:"+"N/A");
								 purchase_voucher.setPurchase_place_of_supply("N/A");		 		 
							 }
							 
							 NodeList voucher_key = docElement.getElementsByTagName("VOUCHERKEY");
							 System.out.println("Voucher Key:"+voucher_key.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setPurchase_voucher_key(voucher_key.item(0).getChildNodes().item(0).getTextContent());
							 
							 NodeList alter_id = docElement.getElementsByTagName("ALTERID");
							 System.out.println("Alter ID:"+alter_id.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setPurchase_voucher_alter_id(alter_id.item(0).getChildNodes().item(0).getTextContent());
							 
							 
							 NodeList co_sginee_gst_number = docElement.getElementsByTagName("CONSIGNEEGSTIN");
							 System.out.println("CONSIGNEEGSTIN:"+co_sginee_gst_number.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setCo_assignee_gst_number(co_sginee_gst_number.item(0).getChildNodes().item(0).getTextContent());
							 
							 NodeList date_time_of_invoice = docElement.getElementsByTagName("BASICDATETIMEOFINVOICE");
							 System.out.println("BASICDATETIMEOFINVOICE:"+date_time_of_invoice.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setPurchase_time_of_invoice(date_time_of_invoice.item(0).getChildNodes().item(0).getTextContent());
							 
							 NodeList co_signee_state_name = docElement.getElementsByTagName("CONSIGNEESTATENAME");
							 System.out.println("COSIGNEESTATE:"+co_signee_state_name.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setPurchase_co_assignee_state_name(co_signee_state_name.item(0).getChildNodes().item(0).getTextContent());
							 
							 
							 NodeList master_id = docElement.getElementsByTagName("MASTERID");
							 System.out.println("Master ID:"+master_id.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setPurchase_voucher_master_id(master_id.item(0).getChildNodes().item(0).getTextContent());
							 
							 NodeList entered_by = docElement.getElementsByTagName("ENTEREDBY");
							 System.out.println("Entered BY:"+entered_by.item(0).getChildNodes().item(0).getTextContent());
							 purchase_voucher.setPurchase_voucher_created_by(entered_by.item(0).getChildNodes().item(0).getTextContent());
						
							 NodeList narration = docElement.getElementsByTagName("NARRATION");
							 if(narration.item(0).getChildNodes().item(0) != null )
							 {
								 System.out.println("Narration :"+narration.item(0).getChildNodes().item(0).getTextContent());
								 purchase_voucher.setPurchase_voucher_narration(narration.item(0).getChildNodes().item(0).getTextContent());	 
							 }
							 else
							 {
								 System.out.println("Narration :  N/A");
								 purchase_voucher.setPurchase_voucher_narration("N/A");	 		 
							 }
							 
							 NodeList purchased_items = docElement.getElementsByTagName("ALLINVENTORYENTRIES.LIST");
							 System.out.println("purchased items total:"+purchased_items.getLength());
							 for(int s = 0 ; s < purchased_items.getLength(); s++)
							 {
								 Node product_node = purchased_items.item(s);
								 Element product_node_element = (Element)product_node;
								 purchaseVoucherProduct product = new purchaseVoucherProduct();
								 
								 NodeList product_item_name  = product_node_element.getElementsByTagName("STOCKITEMNAME");
								 System.out.println("Product Name: "+product_item_name.item(0).getChildNodes().item(0).getTextContent());
								 product.setProduct_name(product_item_name.item(0).getChildNodes().item(0).getTextContent());
								 
								 NodeList product_item_rate  = product_node_element.getElementsByTagName("RATE");
								 System.out.println("Product Item rate: "+product_item_rate.item(0).getChildNodes().item(0).getTextContent());
								 product.setItem_price(product_item_rate.item(0).getChildNodes().item(0).getTextContent().split("/")[0]);
								 
								 NodeList product_item_discount  = product_node_element.getElementsByTagName("DISCOUNT");
								 System.out.println("Product Item discount: "+product_item_discount.item(0).getChildNodes().item(0).getTextContent());
								 product.setDiscount_amount(product_item_discount.item(0).getChildNodes().item(0).getTextContent());
								 
								 NodeList product_total_spent  = product_node_element.getElementsByTagName("AMOUNT");
								 System.out.println("Product Item Spent: "+product_total_spent.item(0).getChildNodes().item(0).getTextContent());
								 String amount = product_total_spent.item(0).getChildNodes().item(0).getTextContent();
								 product.setTotal_amount_spent(amount.substring(1, amount.length()-1));
								 
								 
								 NodeList product_ledger_name  = product_node_element.getElementsByTagName("LEDGERNAME");
								 System.out.println("Product ledger name: "+product_ledger_name.item(0).getChildNodes().item(0).getTextContent());
								 product.setProduct_ledger_name(product_ledger_name.item(0).getChildNodes().item(0).getTextContent());
								 
								 
								 NodeList product_item_quantity  = product_node_element.getElementsByTagName("ACTUALQTY");
								 System.out.println("Product Item quantity: "+product_item_quantity.item(0).getChildNodes().item(0).getTextContent());
								 product.setTotal_purchase_quantity(product_item_quantity.item(0).getChildNodes().item(0).getTextContent());
								 
								 NodeList product_item_billed_quantity  = product_node_element.getElementsByTagName("BILLEDQTY");
								 System.out.println("Product Item billed quantity: "+product_item_billed_quantity.item(0).getChildNodes().item(0).getTextContent());
								 product.setBilled_purchase_quantity(product_item_billed_quantity.item(0).getChildNodes().item(0).getTextContent());
								 
								 
								 NodeList batch_allocation_lists = product_node_element.getElementsByTagName("BATCHALLOCATIONS.LIST");
								 
								 for(int f = 0 ; f < batch_allocation_lists.getLength(); f++)
								 {
									 Node batch_node = batch_allocation_lists.item(f);
									 Element batch_node_element = (Element)batch_node;
									 
									 NodeList godown_name = batch_node_element.getElementsByTagName("GODOWNNAME");
									 NodeList batch_name = batch_node_element.getElementsByTagName("BATCHNAME");
									 NodeList destination_godown = batch_node_element.getElementsByTagName("DESTINATIONGODOWNNAME");
									 
									 purchaseVoucherItemBatch batch = new purchaseVoucherItemBatch();
									 batch.setBatch_number(batch_name.item(0).getChildNodes().item(0).getTextContent());
									 batch.setBatch_location(godown_name.item(0).getChildNodes().item(0).getTextContent());
									 batch.setBatch_destination_location(destination_godown.item(0).getChildNodes().item(0).getTextContent());
									 
									 product.purchase_voucher_item_batches.add(batch);
								 }
								 purchase_voucher.purchase_voucher_items.add(product);
							 }
							 
							 
							 NodeList ledgers_undertaken = docElement.getElementsByTagName("LEDGERENTRIES.LIST");
							 for(int d = 0; d < ledgers_undertaken.getLength();d++)
							 {
								 Node ledger_node =ledgers_undertaken.item(d);
								 Element ledger_node_element = (Element)ledger_node;
								 
								 NodeList ledger_name = ledger_node_element.getElementsByTagName("LEDGERNAME");
								 NodeList ledger_amount = ledger_node_element.getElementsByTagName("AMOUNT");
								 
								 purchaseVoucherLedger ledger = new purchaseVoucherLedger();
								 ledger.setLedger_name(ledger_name.item(0).getChildNodes().item(0).getTextContent());
								 System.out.println("Ledger Name: "+ledger_name.item(0).getChildNodes().item(0).getTextContent());
								 
								 ledger.setLedger_amount(ledger_amount.item(0).getChildNodes().item(0).getTextContent());
								 System.out.println("ledger amount: "+ledger_amount.item(0).getChildNodes().item(0).getTextContent());
								 
								purchase_voucher.purchase_voucher_ledgers.add(ledger);		 
							 }
							 purchase_vouchers.add(purchase_voucher);
							 
							 
						 }
						 else if(node.getChildNodes().item(j).getNodeName().equals("COMPANY"))
						 {
							 for(int m = 0; m<purchase_vouchers.size();m++)
							 {
								 Node company = node.getChildNodes().item(j);
								 Element company_element = (Element)company;
								 NodeList nodes_company_info = company_element.getElementsByTagName("REMOTECMPINFO.LIST");
								 Node current_company_info = nodes_company_info.item(0);
								 Element company_info_element = (Element)current_company_info;
								 NodeList company_tags = company_info_element.getElementsByTagName("NAME");
								 purchase_vouchers.get(m).setCompany_tag(company_tags.item(0).getTextContent());
								 System.out.println("CompanyTag"+company_tags.item(0).getTextContent());
								 
								 NodeList company_names = company_info_element.getElementsByTagName("REMOTECMPNAME");
								 purchase_vouchers.get(m).setCompany_name(company_names.item(0).getTextContent());
								 System.out.println("CompanyName"+company_names.item(0).getTextContent());
								 
								 NodeList company_state = company_info_element.getElementsByTagName("REMOTECMPSTATE");
								 purchase_vouchers.get(m).setCompany_state(company_state.item(0).getTextContent());
								 System.out.println("CompanyState"+company_state.item(0).getTextContent());
								 	 
							 }
							
						 }
				 }
			 }
			 System.out.println("nodes count: "+nodes_inside_node_lists.getLength());
			System.out.println("Nodes Count: "+purchase_vouchers.size());	 
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		upload_purchase_vouchers();
	}

}
