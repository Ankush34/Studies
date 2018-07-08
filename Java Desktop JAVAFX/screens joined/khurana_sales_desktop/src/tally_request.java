import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
public class tally_request {
	public static File outputFile ;
	public static ArrayList<ProductBatch> products_with_batch = new ArrayList<>();
	public void SendToTallyBatch() throws IOException
	{
		String Url = "http://localhost:9000/"; 

		String SOAPAction = "";

		String  item_wise_deep="<ENVELOPE>"+
				 "<HEADER>"+
				 "<TALLYREQUEST>Export Data</TALLYREQUEST>"+
				 "</HEADER>"+
				 "<BODY>"+
				 "<EXPORTDATA>"+
				 "<REQUESTDESC>"+
				 "<STATICVARIABLES>"+
				 "<SVCURRENTCOMPANY>'Khurana Sales - 2017-2018'</SVCURRENTCOMPANY>"+
				 "<SVEXPORTFORMAT>$$SysName:XML</SVEXPORTFORMAT>"+
				 "<EXPLODEALLLEVELS>YES</EXPLODEALLLEVELS>"+
				 "<EXPLODEFLAG>YES</EXPLODEFLAG>"+
				 "<DSPSHOWALLACCOUNTS>Yes</DSPSHOWALLACCOUNTS>"+
				 "<DSPSHOWOPENING>Yes</DSPSHOWOPENING>"+
				 "<DSPSHOWINWARDS>YES</DSPSHOWINWARDS>"+
				 "<DSPSHOWOUTWARDS>YES</DSPSHOWOUTWARDS>"+
				 "<DSPSHOWCLOSING>Yes</DSPSHOWCLOSING>"+
				 "<ISITEMWISE>Yes</ISITEMWISE>"+
				 "</STATICVARIABLES>"+
				 "<REPORTNAME>Stock Summary</REPORTNAME>"+
				 "</REQUESTDESC>"+
				 "</EXPORTDATA>"+
				 "</BODY>"+
				 "</ENVELOPE>";

		// Create the connection where we're going to send the file.
		URL url = new URL(Url);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) connection;


		ByteArrayInputStream bin = new ByteArrayInputStream(item_wise_deep.getBytes());
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		// Copy the SOAP file to the open connection.

		copy_new(bin,bout); 

		byte[] b = bout.toByteArray();

		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", SOAPAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		// Everything's set up; send the XML that was read in to b.
		OutputStream out = httpConn.getOutputStream();
		out.write(b);
		out.close();

		// Read the response and write it to standard out.

		InputStreamReader isr =
		new InputStreamReader(httpConn.getInputStream());
		BufferedReader in = new BufferedReader(isr);

		String inputLine;
		Date date = new Date();
		 outputFile = new File("C:\\Users\\Public\\"+date.toString().replaceAll("[:, ]","")+"batch.xml");	
		FileOutputStream fos=new FileOutputStream(outputFile,true);
	
		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
			fos.write((inputLine+"\n").getBytes());
			fos.flush();
			}
		in.close();
	}

public static void copy_new(InputStream in, OutputStream out)
throws IOException {

// do not allow other threads to read from the
// input or write to the output while copying is
// taking place

synchronized (in) {
synchronized (out) {

byte[] buffer = new byte[256];
while (true) {
int bytesRead = in.read(buffer);
if (bytesRead == -1) {
break;
}
out.write(buffer, 0, bytesRead);
}
}
}
}
public static void sendToGoDaddyBatch()
{
	boolean in_batch =  false;
	try {
		 outputFile = new File("/home/amura/Desktop/batch details.xml");	
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc;
		doc = dBuilder.parse(outputFile);
		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList envelope = doc.getElementsByTagName("ENVELOPE");
		System.out.println(envelope.getLength());
		NodeList list_child_nodes = envelope.item(0).getChildNodes();
		System.out.println("length: "+list_child_nodes.getLength());
		boolean repeating = false;
		for(int i = 0; i < list_child_nodes.getLength() ; i ++)
		{
			if(list_child_nodes.item(i).getNodeName().equals("DSPACCNAME"))
			{
				System.out.println("At Index "+i+" Name: "+list_child_nodes.item(i).getChildNodes().item(1).getTextContent());
				ProductBatch productBatch = new ProductBatch();
				productBatch.name = list_child_nodes.item(i).getChildNodes().item(1).getTextContent();
				products_with_batch.add(productBatch);
				in_batch = false;
			}
			if(list_child_nodes.item(i).getNodeName().equals("SSBATCHNAME"))
			{
				in_batch = true;
			    NodeList child_nodes_batch = list_child_nodes.item(i).getChildNodes();
			    System.out.println("Node Name1 : "+child_nodes_batch.item(1).getNodeName());
			    System.out.println("Node Name2 : "+child_nodes_batch.item(3).getNodeName());
			    System.out.println("Batch Number: "+child_nodes_batch.item(1).getTextContent());
			    System.out.println("GoDown Location: "+child_nodes_batch.item(3).getTextContent());
			    System.out.println("StockINFO Location"+list_child_nodes.item(i+2).getNodeName());
			    System.out.println("Entering Stock info for current Batch");
			    System.out.println("At DSPSTKINFO Index "+(i+2)+" Length: "+list_child_nodes.item(i+2).getChildNodes().getLength());
				NodeList nodes_new = list_child_nodes.item(i+2).getChildNodes();
				for(int j = 0; j < list_child_nodes.item(i+2).getChildNodes().getLength();j++)
				{
				
					if(nodes_new.item(j).getNodeName().equals("DSPSTKOP"))
					{
					 NodeList child_nodes = nodes_new.item(j).getChildNodes();
					 System.out.println("Length at DSPSTKOP "+child_nodes.getLength());
					System.out.println("QTY: "+child_nodes.item(1).getTextContent());
					System.out.println("RATE: "+child_nodes.item(3).getTextContent());
					System.out.println("AMTA: "+child_nodes.item(5).getTextContent());
					}
					if(nodes_new.item(j).getNodeName().equals("DSPSTKIN"))
					{

						 NodeList child_nodes = nodes_new.item(j).getChildNodes();
						 System.out.println("Length at DSPSTKIN "+child_nodes.getLength());
							System.out.println("QTY: "+child_nodes.item(1).getTextContent());
							System.out.println("RATE: "+child_nodes.item(3).getTextContent());
							System.out.println("AMTA: "+child_nodes.item(5).getTextContent());
					}
					if(nodes_new.item(j).getNodeName().equals("DSPSTKOUT"))
					{

						 NodeList child_nodes = nodes_new.item(j).getChildNodes();
						 System.out.println("Length at DSPSTKOUT "+child_nodes.getLength());
							System.out.println("QTY: "+child_nodes.item(1).getTextContent());
							System.out.println("RATE: "+child_nodes.item(3).getTextContent());
							System.out.println("AMTA: "+child_nodes.item(5).getTextContent());
					}
					if(nodes_new.item(j).getNodeName().equals("DSPSTKCL"))
					{

						 NodeList child_nodes = nodes_new.item(j).getChildNodes();
						 System.out.println("Length at DSPSTKCL "+child_nodes.getLength());
							System.out.println("QTY: "+child_nodes.item(1).getTextContent());
							System.out.println("RATE: "+child_nodes.item(3).getTextContent());
							System.out.println("AMTA: "+child_nodes.item(5).getTextContent());
					if(child_nodes.item(1).getTextContent().equals(""))
					{
						
					}
					else
					{
						 if(products_with_batch.get(products_with_batch.size()-1).batch_numbers_with_location.get(child_nodes_batch.item(3).getTextContent()) == null)
							{
						    	ArrayList<String> batch = new ArrayList<>();
								batch.add(child_nodes_batch.item(1).getTextContent());
								products_with_batch.get(products_with_batch.size()-1).batch_numbers_with_location.put(child_nodes_batch.item(3).getTextContent(), batch); 
							}
							else
							{
								products_with_batch.get(products_with_batch.size()-1).batch_numbers_with_location.get(child_nodes_batch.item(3).getTextContent()).add(child_nodes_batch.item(1).getTextContent());
							}
					}
					}

				}
				i = i + 2;
			}
			if(list_child_nodes.item(i).getNodeName().equals("DSPSTKINFO") && in_batch == false)
			{
				System.out.println("At DSPSTKINFO Index "+i+" Length: "+list_child_nodes.item(i).getChildNodes().getLength());
				NodeList nodes_new = list_child_nodes.item(i).getChildNodes();
				for(int j = 0; j < list_child_nodes.item(i).getChildNodes().getLength();j++)
				{
					if(nodes_new.item(j).getNodeName().equals("DSPSTKOP"))
					{
					 NodeList child_nodes = nodes_new.item(j).getChildNodes();
					 System.out.println("Length at DSPSTKOP "+child_nodes.getLength());
					System.out.println("QTY: "+child_nodes.item(1).getTextContent());
					System.out.println("RATE: "+child_nodes.item(3).getTextContent());
					System.out.println("AMTA: "+child_nodes.item(5).getTextContent());
					}
					if(nodes_new.item(j).getNodeName().equals("DSPSTKIN"))
					{

						 NodeList child_nodes = nodes_new.item(j).getChildNodes();
						 System.out.println("Length at DSPSTKIN "+child_nodes.getLength());
							System.out.println("QTY: "+child_nodes.item(1).getTextContent());
							System.out.println("RATE: "+child_nodes.item(3).getTextContent());
							System.out.println("AMTA: "+child_nodes.item(5).getTextContent());
							products_with_batch.get(products_with_batch.size()-1).total_quantity_in = child_nodes.item(1).getTextContent(); 
							
					}
					if(nodes_new.item(j).getNodeName().equals("DSPSTKOUT"))
					{

						 NodeList child_nodes = nodes_new.item(j).getChildNodes();
						 System.out.println("Length at DSPSTKOUT "+child_nodes.getLength());
							System.out.println("QTY: "+child_nodes.item(1).getTextContent());
							System.out.println("RATE: "+child_nodes.item(3).getTextContent());
							System.out.println("AMTA: "+child_nodes.item(5).getTextContent());

							products_with_batch.get(products_with_batch.size()-1).total_quantity_out = child_nodes.item(1).getTextContent(); 
					}
					if(nodes_new.item(j).getNodeName().equals("DSPSTKCL"))
					{

						 NodeList child_nodes = nodes_new.item(j).getChildNodes();
						 System.out.println("Length at DSPSTKCL "+child_nodes.getLength());
							System.out.println("QTY: "+child_nodes.item(1).getTextContent());
							System.out.println("RATE: "+child_nodes.item(3).getTextContent());
							System.out.println("AMTA: "+child_nodes.item(5).getTextContent());
							products_with_batch.get(products_with_batch.size()-1).total_quantity_available =child_nodes.item(1).getTextContent(); 
					}

				}
			}
		}
		
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch(Exception e)
	{
		e.printStackTrace();
	}
			
	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
for(int i  = 0 ; i < products_with_batch.size();i++)
{
	Boolean stock_exist = false;
	HashMap<String,ArrayList<String>> hashmap = products_with_batch.get(i).batch_numbers_with_location;
	if(hashmap.keySet().size() != 0 )
	{
		System.out.println("");
		for(Map.Entry<String, ArrayList<String>> entry : hashmap.entrySet())
		{
			String godown = entry.getKey();
			System.out.println("");
			System.out.println("Godown: "+godown);
			for(int l = 0;l <entry.getValue().size();l++ )
			{
				stock_exist = true;
				System.out.println("Batch: "+entry.getValue().get(l));
			}
			if(stock_exist)
			{
				System.out.println("Name : "+products_with_batch.get(i).name);
//				System.out.println("Stock In: "+products_with_batch.get(i).total_quantity_in);
//				System.out.println("Stock Out: "+products_with_batch.get(i).total_quantity_out);
				System.out.println("Stock Clearance: "+products_with_batch.get(i).total_quantity_available);
//				System.out.println("Size of batch numbers : "+products_with_batch.get(i).batch_numbers_with_location.size());
			
			}
			stock_exist = false;
		}
		
	}		
	}

String DB_URL = "jdbc:mysql://khuranasales.co.in:3306/KhuranaSalesRastapeth";
System.out.println(DB_URL);
String USER = "KhuranaSales";
String PASS = "9270481042khurana";
Connection conn = null;
System.out.println("Connecting to database...");
try{
Class.forName("com.mysql.jdbc.Driver");
conn = DriverManager.getConnection(DB_URL,USER,PASS);
java.sql.Statement stmt=conn.createStatement();
for(int d=0;d<products_with_batch.size();d++)
{ 
	String query = "select * from khurana_sales_stock where Name='"+products_with_batch.get(d).name+"'";
	System.out.println(query);
	ResultSet res=(ResultSet)stmt.executeQuery(query);
    if(res.next())
{
    if(!res.getString("Name").equals(null))
    {
    	System.out.println("True present:  "+products_with_batch.get(d).name);
    	int product_id = res.getInt("product_id");
    	int Batch_id = res.getInt("BatchId");
    	Statement insert_statement = conn.createStatement();
    	if(products_with_batch.get(d).batch_numbers_with_location.size() == 0)
    	{
    		continue;
    	}
    	if(Batch_id != 0)
    	{
    		Statement stmt_update  = conn.createStatement();
        	String query_update = "update batch_numbers set "; 
    		for(Map.Entry<String, ArrayList<String>> entry : products_with_batch.get(d).batch_numbers_with_location.entrySet())
        	{
    			
        		query_update = query_update +" `"+entry.getKey()+"` = '"+String.join(",", entry.getValue())+"',";
        	}
        	query_update = query_update.substring(0,query_update.length()-1);
        	query_update = query_update + " where id="+Batch_id;
        	System.out.println("query: "+query_update);
            int updated =  stmt_update.executeUpdate(query_update);
             if(updated == 1)
             {
            	 System.out.println("successfully updated record");
             }
             continue;
          }
    	Statement stmt_insert  = conn.createStatement();
    	String into_columns ="(product_id,";
    	String into_values = "("+product_id+",";
    	for(Map.Entry<String, ArrayList<String>> entry : products_with_batch.get(d).batch_numbers_with_location.entrySet())
    	{
    		System.out.println(entry.getKey().equals(""));
    		into_columns = into_columns +"`"+entry.getKey()+"`"+",";
    		String values = "";
    		for(int i = 0 ; i <entry.getValue().size();i++)
    		{
    			values = values + entry.getValue().get(i)+",";
    		}
    		values = values.substring(0, values.lastIndexOf(","));
    		into_values = into_values + "'"+values +"'"+",";
    	}
    	
    	into_columns = into_columns.substring(0, into_columns.lastIndexOf(","));
    	into_columns = into_columns+")";
    	into_values = into_values.substring(0, into_values.lastIndexOf(","));
    	into_values = into_values + ")";
    	String insert_query = "insert into batch_numbers "+into_columns+" values "+into_values+"";
    	System.out.println("query: "+insert_query);
    	stmt_insert.executeUpdate(insert_query);
    	 ResultSet rs = stmt_insert.getGeneratedKeys();
         if(rs.next())
         {
             int last_inserted_id = rs.getInt(1);
          	System.out.println("id: inserted: "+last_inserted_id);
          	int peaces_available = Integer.parseInt(products_with_batch.get(d).total_quantity_available.split(" ")[0]);
         String query_update_product = "update `khurana_sales_stock` set BatchId = "+last_inserted_id+",Stock="+peaces_available+" where product_id = "+product_id;
        int updated =  stmt_insert.executeUpdate(query_update_product);
         if(updated == 1)
         {
        	 System.out.println("successfully updated record");
         }
         }
    	
    }
}
    else
    {
    	System.out.println("Not Present: "+products_with_batch.get(d).name);
    }
}
}catch(Exception e)
{
	e.printStackTrace();
}
//	if(res.getInt("total")==1)
//    {
//        System.out.println(res.getInt("total"));    	
//        System.out.println("updating...");
//        stmt.execute("update khurana_sales_stock set stock = "+stock.elementAt(d)+" where Name = '"+products.elementAt(d)+"'");
//    }
//    else
//    {
//    	if(products.elementAt(d).charAt(0)=='0' || products.elementAt(d).charAt(0)=='1')
//    	{
//    		continue;
//    	}
//    	System.out.println(0);
//   System.out.println("inserting...");
//    	stmt.execute("insert into khurana_sales_stock(Name,Stock) values('"+products.elementAt(d)+"','"+stock.elementAt(d)+"')");
//    }
//	System.out.println("adding to database...");
//}}
//catch (Exception e){
//	e.printStackTrace();
//}
}

	public void SendToTally() throws IOException
	{
		String Url = "http://localhost:9000/"; 

		String SOAPAction = "";

		String Voucher ="<ENVELOPE>"+ 
"<HEADER>"+ 
"<VERSION>1</VERSION>"+ 
"<TALLYREQUEST>Export</TALLYREQUEST>"+ 
"<TYPE>Data</TYPE>"+ 
"<ID>Stock Summary</ID>"+ 
"</HEADER>"+ 
"<BODY>"+ 
"<DESC>"+ 
"<STATICVARIABLES>"+ 
"<EXPLODEFLAG>Yes</EXPLODEFLAG>"+ 
"<SVEXPORTFORMAT>$$SysName:XML</SVEXPORTFORMAT>"+ 
"<SVCURRENTCOMPANY>'Khurana Sales - 2017-2018'</SVCURRENTCOMPANY>"+ 
"</STATICVARIABLES>"+
"</DESC>"+ 
"</BODY>"+ 
"</ENVELOPE>" 
;
		// Create the connection where we're going to send the file.
		URL url = new URL(Url);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) connection;


		ByteArrayInputStream bin = new ByteArrayInputStream(Voucher.getBytes());
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		// Copy the SOAP file to the open connection.

		copy(bin,bout); 

		byte[] b = bout.toByteArray();

		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", SOAPAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		// Everything's set up; send the XML that was read in to b.
		OutputStream out = httpConn.getOutputStream();
		out.write(b);
		out.close();

		// Read the response and write it to standard out.

		InputStreamReader isr =
		new InputStreamReader(httpConn.getInputStream());
		BufferedReader in = new BufferedReader(isr);

		String inputLine;
		Date date = new Date();
		 outputFile = new File("C:\\Users\\Public\\"+date.toString().replaceAll("[:, ]","")+".txt");	
		FileOutputStream fos=new FileOutputStream(outputFile,true);
	
		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
			fos.write((inputLine+"\n").getBytes());
			fos.flush();
			}
		in.close();
	}

public static void copy(InputStream in, OutputStream out)
throws IOException {

// do not allow other threads to read from the
// input or write to the output while copying is
// taking place

synchronized (in) {
synchronized (out) {

byte[] buffer = new byte[256];
while (true) {
int bytesRead = in.read(buffer);
if (bytesRead == -1) {
break;
}
out.write(buffer, 0, bytesRead);
}
}
}
}
public static void sendToGoDaddy()
{
 Vector<String> products=new Vector<>(); 
 Vector<Integer> stock=new Vector<>();
 try { 
	 Date date = new Date(); 
 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
 Document doc = dBuilder.parse(outputFile);
 doc.getDocumentElement().normalize();
 System.out.println("Root element :"+ doc.getDocumentElement().getNodeName());
 NodeList nList = doc.getElementsByTagName("DSPACCNAME");
 System.out.println("----------------------------");
 for (int temp = 0; temp < nList.getLength(); temp++) {
  Node nNode = nList.item(temp);
  System.out.println("\nCurrent Element :" + nNode.getNodeName());
  if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
	        System.out.println("First Name : "+ eElement
	.getElementsByTagName("DSPDISPNAME")
	.item(0)
	.getTextContent());
	products.addElement(eElement.getElementsByTagName("DSPDISPNAME").item(0).getTextContent());
    }
   }
 NodeList nList1 = doc.getElementsByTagName("DSPSTKINFO");
 System.out.println("----------------------------");
 for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
 NodeList nNode2 = (NodeList) nList1.item(temp1);
 for (int temp2 = 0; temp2 < nNode2.getLength(); temp2++) {
 Node nNode3 = nNode2.item(temp2);
 if (nNode3.getNodeType() == Node.ELEMENT_NODE) {
 Element eElement1 = (Element) nNode3;
 System.out.println("qantity : "+ eElement1.getElementsByTagName("DSPCLQTY").item(0).getTextContent());
 String q=eElement1.getElementsByTagName("DSPCLQTY").item(0).getTextContent();
 String arr[]=q.split(" ");
 try{
      stock.addElement(Integer.parseInt(arr[0]));
 }catch(NumberFormatException nfe)
       {
	 stock.addElement(0);
  }}}}
	}catch (Exception e) {
		e.printStackTrace();
	}
System.out.println("Size for Products: "+products.size());
System.out.println("Size for stock: "+stock.size());
String DB_URL = "jdbc:mysql://khuranasales.co.in:3306/KhuranaSalesRastapeth";
System.out.println(DB_URL);
String USER = "KhuranaSales";
String PASS = "9270481042khurana";
Connection conn = null;
System.out.println("Connecting to database...");
try{
Class.forName("com.mysql.jdbc.Driver");
conn = DriverManager.getConnection(DB_URL,USER,PASS);
java.sql.Statement stmt=conn.createStatement();
for(int d=0;d<products.size();d++)
{ 
	String query = "select count(*) as total from khurana_sales_stock where Name='"+products.elementAt(d)+"'";
	System.out.println(query);
	ResultSet res=(ResultSet)stmt.executeQuery(query);
   res.next();
	if(res.getInt("total") >= 0)
    {
        System.out.println(res.getInt("total"));    	
        System.out.println("updating...");
        stmt.execute("update khurana_sales_stock set stock = "+stock.elementAt(d)+" where Name = '"+products.elementAt(d)+"'");
    }
    else
    {
    	if(products.elementAt(d).charAt(0)=='0' || products.elementAt(d).charAt(0)=='1')
    	{
    		continue;
    	}
    	System.out.println(0);
   System.out.println("inserting...");
   String brand = products.elementAt(d).split(" ")[0];
    	stmt.execute("insert into khurana_sales_stock(Brand,Name,Stock) values('"+brand+"','"+products.elementAt(d)+"','"+stock.elementAt(d)+"')");
    }
	System.out.println("adding to database...");
}}
catch (Exception e){
	e.printStackTrace();
}
}
public static void main(String[] args) throws Exception {
tally_request r = new tally_request();
//r.SendToTally();
//r.sendToGoDaddy();
//r.SendToTallyBatch();
r.sendToGoDaddyBatch();
}
}
