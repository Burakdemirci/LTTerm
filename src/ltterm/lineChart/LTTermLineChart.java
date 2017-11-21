
package ltterm.lineChart;

import java.util.ArrayList;
import ltterm.communication.SerialConnection;

/**
 *
 * @author Burak Demirci
 */
public class LTTermLineChart {
    
    public SerialConnection fx ;
    
    LTTermLineChart(){}

    public LTTermLineChart(SerialConnection serial) {
      fx=serial; 
    }
    public ArrayList<String> setValtoChart(){
        ArrayList<String> data=new ArrayList<>();
        data= fx.setValtoChart();
        return data;
    }
    
    
    public void setHeads(ArrayList<String> heads) throws Exception{
        
        lineChart lineC = new lineChart(heads);
    }

}
