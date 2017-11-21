
package ltterm.excel;
/**
 *
 * @author Burak Demirci
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author Computer
 */
public class WriteExcel {  
    
    public void writeToExcel(String path,ArrayList<String> headers,
        ArrayList<String> data) throws WriteException, BiffException{
        
        WritableWorkbook wbook = null;
        File out = new File(path); 
        ArrayList<String> lastData = new ArrayList<>();
        
        try {
           
            if(out.exists()){
                ArrayList<ArrayList<String>> value = new ArrayList<ArrayList<String>>();
                ReadExcel readE = new ReadExcel();
                value= readE.readFromExcel(path);
                                
                if(value.size()==headers.size()){
                    for(int i=1; i<value.get(0).size(); i++){
                        for(int j=0; j<value.size(); j++){
                            lastData.add(value.get(j).get(i));
                        }
                    }
                }
            }
            
            for(int i=0; i<data.size(); i++){
                lastData.add(data.get(i));
            }
            
            wbook = Workbook.createWorkbook(out);
            // create an Excel sheet
            WritableSheet excelSheet = wbook.createSheet("LTTerm", 0);
            
            /*----- Write the values ---------*/
            
            for(int i=0; i<headers.size(); i++){
                excelSheet.addCell(new Label(i,0,headers.get(i)));
            }
            int k=0;
            for(int i=0; i<lastData.size()/headers.size(); i++){
                
                for(int j=0; j<headers.size(); j++){
                    excelSheet.addCell(new Label(j,i+1,lastData.get(k)));
                    k++;
                }
            }
            wbook.write();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

            if (wbook != null) {
                try {
                    wbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
    }      
}
