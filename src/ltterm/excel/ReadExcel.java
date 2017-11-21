
package ltterm.excel;
/**
 *
 * @author Burak Demirci
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Computer
 */
public class ReadExcel {
    
    public ArrayList<ArrayList<String>> readFromExcel(String inputFile)
            throws BiffException, IOException{
        
        ArrayList<ArrayList<String>> value = new ArrayList<ArrayList<String>>();
        FileInputStream inputWorkbook = new FileInputStream(inputFile);
        Workbook w =null;
        try {              
            w=Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(0);
            // Loop over first 10 column and lines
            
            for (int j = 0; j < sheet.getColumns(); j++) {
                ArrayList v = new ArrayList();
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell cell = sheet.getCell(j, i);
                    CellType type = cell.getType();
                    if (type == CellType.LABEL) {
                        v.add(cell.getContents());
                    }
                    if (type == CellType.NUMBER) {
                        v.add(cell.getContents());
                    }
                    if (type == CellType.BOOLEAN){
                        v.add(cell.getContents());
                    }
                    if (type == CellType.DATE){
                        v.add(cell.getContents());
                    }
                }
                value.add(v);
            }
            inputWorkbook.close();
        } catch (BiffException e) {
            e.printStackTrace();
        } 
        return value;
    }                
}
