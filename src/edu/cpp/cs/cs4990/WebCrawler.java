package edu.cpp.cs.cs4990;
import java.net.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//import opennlp.tools.sentdetect.*;

public class WebCrawler {
    public static void main(String[] args) throws Exception {


    	int pageCount =0;
    		    	String fileName="";
    		    	String urlName="";
    		    	int pNum=0;
    		    	LinkedList listURL=new LinkedList(); 
    		    	LinkedList mainListURL=new LinkedList(); 
    		    	fileName+="C:\\Users\\Varoozhan 1\\Desktop\\Eclipse2\\WebCrawler\\repository\\page"+pNum+".txt";
    		    	urlName="https://www.quora.com/";
    		    	PrintWriter reportFile = new PrintWriter(new File("report.csv"));
    				StringBuilder reportB = new StringBuilder();
    		    	
    		    	
    		    	
    		    	
    			do {
    				if(pNum!=0) {
    					fileName="C:\\\\Users\\\\Varoozhan 1\\\\Desktop\\\\Eclipse2\\\\WebCrawler\\\\repository\\\\page"+pNum+".txt";
    					urlName=mainListURL.pop().toString();
    				}
    				
    				PrintWriter RROutputfile = new PrintWriter(new File(fileName));
    				StringBuilder RRbuilder = new StringBuilder();
    				
    		    	URL url = new URL(urlName);
    		        HttpURLConnection yc = (HttpURLConnection) url.openConnection();
    		        BufferedReader in;
    		        while(yc.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND 
    		        	|| yc.getResponseCode()==HttpURLConnection.HTTP_FORBIDDEN) {
    		        	
    		        	urlName=mainListURL.pop().toString();
    		        	url = new URL(urlName);
    		        	yc = (HttpURLConnection) url.openConnection();
    		        	//in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
    		        }
    		        	in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
    		        String inputLine;
    		        while ((inputLine = in.readLine()) != null) { 
    		           RRbuilder.append(inputLine);
    		           RRbuilder.append('\n');
    		        }
    		        
    		        in.close();
    		        RROutputfile.write(RRbuilder.toString());
    				RROutputfile.flush();
    				RROutputfile.close();
    				
    				
    				File input = new File(fileName);
    				Document doc = Jsoup.parse(input, "UTF-8");
    				
    				String str1 = doc.body().text();
    				String result= str1.replaceAll("[^a-zA-Z]+"," ");
    				System.out.println(result);
    				Elements links = doc.getElementsByTag("a");
    				 String linkHref = "";
    				 String linkText="";
    				 
    				for (Element link : links) {
    					   linkHref = link.attr("abs:href");
    					   linkText = link.text();
    					   if(linkHref!="")
    					   listURL.add(linkHref);
    					}
    				pageCount++;
    				System.out.println(pageCount+ " list: "+listURL.size());
    				System.out.println("mainlist: "+mainListURL.size());
    					
    					reportB.append(urlName);
    					reportB.append(',');
    					reportB.append(listURL.size());
    					reportB.append('\n');
    					
    					mainListURL.addAll(listURL);

//    				System.out.println(listURL);
//    				System.out.println(mainListURL);
    				pNum++;
    				listURL.clear();
    		    }while(pNum<=10);
    			 reportFile.write(reportB.toString());
    			 reportFile.flush();
    			 reportFile.close();
    		  

    	
		}

}
