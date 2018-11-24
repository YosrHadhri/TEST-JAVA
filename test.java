package ParseText;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class test {
/****************** method that converts a string type that can a have multiple forms, to a date type with a unique form yyyy-MM-dd *****************/ 
	public static LocalDate parseDate(String strDate) throws Exception
	{
	        SimpleDateFormat[] formats =
	                new SimpleDateFormat[] {
	                		new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH), 
	                		new SimpleDateFormat("MMMM dd, yyyyy", Locale.ENGLISH),
	                        new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH), 
	                        new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH), 
	                        new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH),
	                        new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH),
	                        new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH),
	                        new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH),
	                         };
	        String NEW_FORMAT = "yyyy-MM-dd";
	        Date parsedDate = null;
            for (int i = 0; i < formats.length; i++)
	        {
	            try
	            {   
	                parsedDate = formats[i].parse(strDate);
	                formats[i].applyPattern(NEW_FORMAT);
	                 String newDateString = formats[i].format(parsedDate);
	                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
	                 LocalDate date = LocalDate.parse(newDateString, formatter);
	                 
	                return date ;
	            }
	            catch (ParseException e)
	            {
	                continue;
	            }
	        }
	    
	    throw new Exception("Unknown date format: '" + strDate + "'");
	}
/******************************End of the method parseDate**********************************/	
	
	
/************** Method that returns a list of dates found in inputText sorted in ascending order*************** */	
static ArrayList<LocalDate> Listoutput() throws Exception {
	ArrayList<String>  allMatches = new ArrayList<String>();
	ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
	InputStream flux=new FileInputStream("C:\\Users\\dell\\Desktop\\inputText.txt"); 
	InputStreamReader lecture=new InputStreamReader(flux);
	BufferedReader buff=new BufferedReader(lecture);
	String ligne;
	while ((ligne=buff.readLine())!=null){    
		/* Define the forms of the possible dates with the regular expression  */ 
		   Matcher m = Pattern.compile("((\\d{4})[- /.](02)[- /.](0[1-9]|[2][0-9]))|" // the form yyyy-MM-dd OR yyyy/MM/dd OR yyyy.MM.dd for the month of February
				   +"((0[1-9]|[2][0-9])[- /.](02)[- /.](\\\\d{4}))|"                  // the form dd-MM-yyyy OR  dd/MM/yyyy OR dd.MM.yyyy for the month of February
				   + "((\\d{4})[- /.](0[13578]|1[02])[- /.](0[1-9]|[12][0-9]|3[01]))|" //the form yyyy-MM-dd OR yyyy/MM/dd OR yyyy.MM.dd for months that contain 31 days
				   +"((0[1-9]|[12][0-9]|3[01])[- /.](0[13578]|1[02])[- /.](\\\\d{4}))|" // the form dd-MM-yyyy OR  dd/MM/yyyy OR dd.MM.yyyy for months that contain 31 days
				   + "((\\d{4})[- /.](0[469]|1[1])[- /.](0[1-9]|[12][0-9]|3[0]))|" //the form yyyy-MM-dd ou yyyy/MM/dd ou yyyy.MM.dd for months that contain 30 days
				   +"((0[1-9]|[12][0-9]|3[0])[- /.](0[469]|1[1])[- /.](\\\\d{4}))|" // the form dd-MM-yyyy ou  dd/MM/yyyy ou dd.MM.yyyy for months that contain 30 days
		           + "((January|March|May|July|August|October|December)[' '](0[1-9]|[12][0-9]|3[01])[','][' '](\\d{4}))" //the form MMMM dd, yyyy for months that contain 31 days
		           + "|((April|June|September|November)[' '](0[1-9]|[12][0-9]|3[0])[','][' '](\\d{4}))" // the form MMMM dd, yyyy for months that contain 31 days
		           + "|((February)[' '](0[1-9]|[2][0-9])[','][' '](\\d{4}))" // the form MMMM dd, yyyy for the month of February
		           + "|((0[1-9]|[12][0-9]|3[01])[' '](January|March|May|July|August|October|December)[' '](\\d{4}))" // the form dd MMMM yyyy for months that contain 31 days
		           + "|((0[1-9]|[12][0-9]|3[0])[' '](April|June|September|November)[' '](\\d{4}))" // the form dd MMMM yyyy for months that contain 30 days
		           + "|((0[1-9]|[12][0-9])[' '](February)[' '](\\d{4}))").matcher(ligne); //the form dd MMMM yyyy for the month of February
		  
		   /* If a date form defined above exists in inputText, add the date in ArrayList allMatches  */
		     
		   while (m.find()) {
		    allMatches.add(m.group());} }
		  
		  
		  for(String el : allMatches ){
		          dates.add(parseDate(el));
		          Collections.sort(dates);}
		          return dates;}
	
/***********************************End of the method output() ***************************************/


/****************************the method of counting dates************************* */
public static void comptage (ArrayList<LocalDate> dates){       
int S = 1 ; 
System.out.println((dates.get(0)).getYear()+":");
System.out.println("-"+(dates.get(0)).getMonth().getValue());
int j = 1 ; 
while(  j <dates.size() )	{
	if( dates.get(j).getYear() !=  dates.get(j-1).getYear()) { // not same year
		System.out.println("-"+dates.get(j-1).getDayOfMonth()+" ("+S+")");  
		System.out.println((dates.get(j)).getYear()+":");
		System.out.println("-"+(dates.get(j)).getMonth().getValue());
		S=1;
		j++ ; }
	
	else if( dates.get(j).getYear() ==  dates.get(j-1).getYear()) {  //same year
		if( dates.get(j-1).getMonth().getValue() !=  dates.get(j).getMonth().getValue()) { // same year and not same month
			S = 1 ; 
			System.out.println("-"+dates.get(j-1).getDayOfMonth()+" ("+S+")"); 
			System.out.println("-"+(dates.get(j)).getMonth().getValue());
			j++; }
		else if( dates.get(j-1).getMonth().getValue() ==  dates.get(j).getMonth().getValue()){ // same year and same month
			if (dates.get(j-1).getDayOfMonth() != dates.get(j).getDayOfMonth()) { // same year, same month and not same day
				System.out.println("-"+dates.get(j-1).getDayOfMonth()+" ("+S+")");
				j++; }
			else if (dates.get(j-1).getDayOfMonth() == dates.get(j).getDayOfMonth()) {   //same year, same month and same day
				S++ ; 
				j++; 
				}
		}
}
	if (dates.size()==j) { System.out.println("-"+dates.get(j-1).getDayOfMonth()+" ("+S+")"); } 
	}
        }

/***************************End of the method comptage()**************************/

public static void main( String [] args) throws Exception {
	ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
		dates= Listoutput(); 
		comptage(dates) ; 
		}

}
