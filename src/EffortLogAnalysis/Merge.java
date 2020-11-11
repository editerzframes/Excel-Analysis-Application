package EffortLogAnalysis;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Iterator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;





public class Merge {
	
		/*public static final String SAMPLE_XLSX_FILE_PATH[]= {  "Amandeep_SD Faculty Effort Log v2.xlsx", "Anil_SD Faculty Effort Log v2.xlsx", "Anuj_SD Faculty Effort Log v2.xlsx",  "Gunjan_SD Faculty Effort Log v2.xlsx",
	            		  "Hira_SD Faculty Effort Log v2.xlsx","Kamal_SD Faculty Effort Log v2.xlsx", "Manju_SD Faculty Effort Log v2.xlsx","Manoj_SD Faculty Effort Log v2.xlsx",
	            		  "Meghna_SD Faculty Effort Log v2.xlsx", "Neera_SD Faculty Effort Log v2.xlsx", "Nikita_SD Faculty Effort Log v2.xlsx", "Rajeev_SD Faculty Effort Log v2.xlsx",
	            		  "Ritu_SD Faculty Effort Log v2.xlsx", "Rohini_SD Faculty Effort Log v2.xlsx"};
	            		  
	            		*/
	              static XSSFSheet s[] = new XSSFSheet[50] ;
	              Collection<File> fl;
	              static XSSFWorkbook wk;

	              
	              
	              public Merge()    {       }
	              
	              Merge(String effortlog[], int size)
	              {
	                  FileOutputStream fos = null;
	                  try {
	                               

	                        		 fos = new FileOutputStream("EffortLogData.xlsx");                        //Create a new excel file with name "NewSample.xlsx"
	                        		            wk = new XSSFWorkbook();
	                                
	                                for(int i= 0; i < size ; i++)
	                                {                                       
	                               String[] words = effortlog[i].split("_");
	                               s[i] = wk.createSheet(words[0]);
	                                Sheet s1 = wk.getSheetAt(0);                        
	                                DataFormatter df = new DataFormatter();
	                                int rnum=0;
	                                Iterator<Row> ri = s1.rowIterator();
	                               
	                                while (ri.hasNext())
	                                {
	                                    Row row = ri.next();
	                                                                               
	                                    Row row1 = s[i].createRow(rnum);
	                                    // let's iterate over the columns of the current row.   
	                                    Iterator<Cell> ci = row.cellIterator();
	                                    int a=0;
	                                   
	                                   rnum++;
	                                }
	 
	                                }   
	                               wk.write(fos);
	                                wk.close();
	                                fos.flush();
	                                fos.close();
	                  } catch (Exception e) {
	                                e.printStackTrace();
	                  }
	     }
	     
	    

	              public static void main(String args[])  {
	                           Merge m1 = new Merge();
	                         
	                                 System.out.println("The File Has Been Created.");
	              }

	              
	}


