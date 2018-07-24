import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BatchDetails {
	public static ArrayList<ProductBatch> products_with_batch = new ArrayList<>();
	public static void main(String args[])
	{
		boolean in_batch =  false;
		try {
			File fXmlFile = new File("C:\\Users\\Lenovo\\Desktop\\batch details.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			doc = dBuilder.parse(fXmlFile);
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
//					System.out.println("Stock In: "+products_with_batch.get(i).total_quantity_in);
//					System.out.println("Stock Out: "+products_with_batch.get(i).total_quantity_out);
					System.out.println("Stock Clearance: "+products_with_batch.get(i).total_quantity_available);
//					System.out.println("Size of batch numbers : "+products_with_batch.get(i).batch_numbers_with_location.size());
				
				}
				stock_exist = false;
			}
			
		}		
		}
	
	}
}
