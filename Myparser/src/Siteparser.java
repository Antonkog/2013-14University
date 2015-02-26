import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class  Siteparser {
	
	
	
	static String url = "http://www.povarenok.ru/recipes/show/";
	
	String num = null;

	
	static String ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30";
	
	static Document document;
	static String recipedesc="";
	static String recipesteps ="";
	static String recipestepsinshort ="";
	static String recipeingridients="";
	static String recipeingamount="";
	static String recipedestiny="";
	static String recipekitchen="";
	static String recipetaste="";
	static String recipetags="";
	
	
	
public static void main (String args[]){
	
    
	
	
    Integer[] array = new Integer[2500];
    for (int i = 0; i < 2500; i++) {
        array[i] = i+1;
    }
    
    List<Integer> list = Arrays.asList(array);
    Collections.shuffle(list);
    
    for (int i = 0; i < list.size(); i++) {
    	
        System.out.print(list.get(i) + " ");
   
    int a = list.get(i)+40000;
    
    
        url = "http://www.povarenok.ru/recipes/show/"+ a+"/";
		
    	
    	try {
    		
    		
    		 document =Jsoup.connect(url).userAgent(ua).timeout(0).get();
    		 
    		 System.out.println(a + "это номер рецепта к которому будет относиться следующее : ");
    		 
    		 
    			writerecipedesc(a);
    			
    			writereciepestepsinshort(a);
    			
    			writereciepesteps(a);
    		
    			writeingridients(a);
    		 
    			writeingridientsamount(a);
    		 
    			writedestiny(a);		
    			
    			writekithen(a);
    			
    			writeteste(a);
    			
     			
    			writeall();	
    			
    			
    			savetofile();
    			
    			

    			Random r = new Random();
    			int Low = 30;
    			int High = 50;
    			int R = r.nextInt(High-Low) + Low;
    			    			
    			Thread.sleep(R*100);
    			

    	} catch (IOException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	} catch (InterruptedException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
    
    }
    
    
    
    
	



private static void writekithen(int recnum) {
	// TODO Auto-generated method stub
	
Elements info = document.getElementsByClass("recipe-infoline");
		
	Element kitchen  = info.last();

	Iterator<Element> ite = kitchen.select("a").iterator();
	
	
	int ix=1;
	while(ite.hasNext()){
				  
		 
	recipekitchen=recipekitchen+recnum +" ; " + ix+" ; " + ite.next().text()+"\r\n";

		  
	
		  
	ix++;
		}
	
	
}


private static void writeteste(int recnum) {
	// TODO Auto-generated method stub

	Elements destinys = document.getElementsByClass("recipe-tags-tastes");
			
	
	Element grayTag =  destinys.select(".gray").first();
	
	 
	 int ix = 1;
	 for(Element hrefs : grayTag.select("a")){
	       
	        recipetaste=recipetaste+recnum + "; " + ix + "; " + hrefs.text()+"\r\n";
	        ix++;
	 }
	 
	 
	 Element secondgrayTag =  destinys.select(".gray").last();
	 
	 int ix2 = 1;
	 for(Element hrefs : secondgrayTag.select("a")){
	         
	        recipetags=recipetags+recnum + "; " + ix2 + "; " + hrefs.text()+"\r\n";
	        
	        ix2++;
	 }
	 

	 
	}


private static void writedestiny(int recnum) {
	// TODO Auto-generated method stub
	
	
	Elements destinys = document.getElementsByClass("recipe-destiny");
		
	
	Iterator<Element> ite = destinys.select("a").iterator();
	
	
	int ix=1;
	while(ite.hasNext()){
				  
		  

	recipedestiny=recipedestiny+recnum +" ; " + ix+" ; " + ite.next().text()+"\r\n";


	ix++;
		}

	 
	
}


private static void writeingridientsamount(int recnum) {
	// TODO Auto-generated method stub
	

	
	
	Elements ingridients = document.getElementsByClass("recipe-ing");
		
	
	Elements list  = ingridients.get(0).getElementsByClass("cat"); //.getElementsByIndexEquals(5);//.getElementsByTag("<li>");

	//Element names  = list.get(1).children().first();
	
	
	Iterator<Element> ite = list.select("[itemprop=amount]").iterator();

	int ix=1;
		 while(ite.hasNext()){
					  
			  
	
	recipeingamount=recipeingamount +recnum +" ; " + ix+" ; " + ite.next().text()+"\r\n";
			
ix++;
			}
	
	
}


public static void writerecipedesc( int recnum) {
	// Get the html document title
	

	
	
	String title = document.title();
	
	
	
	
	
	Elements deskel = document.getElementsByClass("recipe-short");
	
	String descst = deskel.text();
	
	
	

	Elements timeel = document.getElementsByClass("recipe-time-peaces");
	Elements timeelchild  = timeel.select("time"); 
	
	String timest = timeelchild.text();
	
	
	
	Elements mainimgel = document.getElementsByClass("recipe-img");
	
	Elements mainimgchild = mainimgel.select("[src]");

	
	String mainimgclildst = mainimgchild.attr("src");
	
	
	   
	
	   recipedesc = recipedesc +recnum +" ; " +title+" ; "+ descst + " ; " + timest +" ; "+ mainimgclildst + "\r\n"; 
	   
	   loadImage(mainimgclildst,"D:\\parser"+File.separator+ "mainimg"   +File.separator +recnum +"_"+"longimg");
	   
}




public static void writeingridients(int recnum) {
	// Using Elements to get the Meta data
	
	
	
	
	Elements ingridients = document.getElementsByClass("recipe-ing");
		
	
	Elements list  = ingridients.get(0).getElementsByClass("cat"); //.getElementsByIndexEquals(5);//.getElementsByTag("<li>");

	
	
	Iterator<Element> ite = list.select("a").iterator();

	int ix=1;
		 while(ite.hasNext()){
					  
			  
	recipeingridients = recipeingridients +recnum +" ; " + ix+" ; " + ite.next().text() + "\r\n"; 
ix++;
			}
	
	

}


public static void writereciepestepsinshort(int recnum) {
	

		
	Elements steps = document
			.select("div[class=recipe-text");
	// Locate the content attribute
	String desc = steps.text();
	
	Elements stepimgel = document.getElementsByClass("recipe-img");
	
	Elements stepimgchild = stepimgel.select("[src]");

	
	String stepimgclildst = stepimgchild.attr("src");
	
	
	recipestepsinshort = recipestepsinshort + recnum + ";" + desc  + ";" + stepimgclildst +  "\r\n"; 
	
	loadImage(stepimgclildst,"D:\\parser"+File.separator+ "shortimg"  +File.separator +recnum +"_"+"inshortimg");
}



public static void writereciepesteps(int recnum) {


	
	
	Elements tablesteps = document
			.select("div[class=recipe-steps");
	// Locate the content attribute
	
	Iterator<Element> ite = tablesteps.select("td").iterator();
	
	
	
	int ix=1;
		 while(ite.hasNext()){
			 
			 
			 String img =  ite.next().select("[src]").attr("src");
			 String imgurl=recnum +" ; " + ix+" ; " + img;
			 String text=ite.next().text();
			  
			
			 recipesteps=recipesteps+imgurl+";"+text+  "\r\n"; 
	
			
			
			 loadImage(img,"D:\\parser"+File.separator+ "longimg"   +File.separator +recnum+ix +"_"+"longimg");
			 		 
ix++;
			}
		
	ix=1;
	

}

public static void writeall (){
	System.out.println(recipedesc+recipesteps+recipedestiny+recipeingridients+recipeingamount+recipetaste+recipekitchen);
	
	 
}

public static void savetofile (){
	
	
			File file1 = new File("D:\\parser"+File.separator+"recipedesc.txt");
	  		File file2 = new File("D:\\parser"+File.separator+"recipesteps.txt");
			File file3 = new File("D:\\parser"+File.separator+"recipestepsinshort.txt");
			File file4 = new File("D:\\parser"+File.separator+"recipeingridients.txt");
			File file5 = new File("D:\\parser"+File.separator+"recipeingamount.txt");
			File file6 = new File("D:\\parser"+File.separator+"recipedestiny.txt");
			File file7 = new File("D:\\parser"+File.separator+"recipekitchen.txt");
			File file8 = new File("D:\\parser"+File.separator+"recipetaste.txt");
			File file9 = new File("D:\\parser"+File.separator+"recipetags.txt");
	try {
		
	
	if (!file1.exists()){
	    System.out.println("File is created!");
	    file1.createNewFile();
	    file2.createNewFile();
	    file3.createNewFile();
	    file4.createNewFile();
	    file5.createNewFile();
	    file6.createNewFile();
	    file7.createNewFile();
	    file8.createNewFile();
	    file9.createNewFile();
		  
	  
		
	  }else{
	    System.out.println("Files already exists.");
	  }


	FileWriter fw1 = new FileWriter(file1,true);
	FileWriter fw2 = new FileWriter(file2,true);
	FileWriter fw3 = new FileWriter(file3,true);
	FileWriter fw4 = new FileWriter(file4,true);
	FileWriter fw5 = new FileWriter(file5,true);
	FileWriter fw6 = new FileWriter(file6,true);
	FileWriter fw7 = new FileWriter(file7,true);
	FileWriter fw8 = new FileWriter(file8,true);
	FileWriter fw9 = new FileWriter(file9,true);
	
	
	BufferedWriter bw1 = new BufferedWriter(fw1);
	BufferedWriter bw2 = new BufferedWriter(fw2);
	BufferedWriter bw3 = new BufferedWriter(fw3);
	BufferedWriter bw4 = new BufferedWriter(fw4);
	BufferedWriter bw5 = new BufferedWriter(fw5);
	BufferedWriter bw6 = new BufferedWriter(fw6);
	BufferedWriter bw7 = new BufferedWriter(fw7);
	BufferedWriter bw8 = new BufferedWriter(fw8);
	BufferedWriter bw9 = new BufferedWriter(fw9);
	
	bw1.write(recipedesc);
	bw2.write(recipesteps);
	bw3.write(recipestepsinshort);
	bw4.write(recipeingridients);
	bw5.write(recipeingamount);
	bw6.write(recipedestiny);
	bw7.write(recipekitchen);
	bw8.write(recipetaste);
	bw9.write(recipetags);
	

	
	 recipedesc="";
	 recipesteps ="";
	 recipestepsinshort ="";
	 recipeingridients="";
	 recipeingamount="";
	 recipedestiny="";
	 recipekitchen="";
	 recipetaste="";
	 recipetags="";
	 
	bw1.close();
	bw2.close();
	bw3.close();
	bw4.close();
	bw5.close();
	bw6.close();
	bw7.close();
	bw8.close();
	bw9.close();
	
	
	
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	

	System.out.println("Done"); 

}


private static Image loadImage(String url, String filename) {
    try {
                
        
        BufferedImage img = ImageIO.read(new URL(url));
        File file = new File(filename);
        
        if (!file.exists()) {
            file.createNewFile();
        }
        
        ImageIO.write(img, "png", file);
        
        return img;
        
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

}
