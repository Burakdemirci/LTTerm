package ltterm.communication;

/**
 *
 * @author Burak Demirci
 */
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import javafx.scene.control.TextArea;
import ltterm.lineChart.LTTermLineChart;
import ltterm.excel.WriteExcel;
public class SerialConnection {
    
    private static TextArea textArea = new TextArea();
    private  OutputStream out;
    private static SerialPort serialPort ;
    private static int flowControlHw=0,headerSize=0;
    private static int dsrFlag=0,dtrFlag=0;
    private static int excelFlag=0;
    private static ArrayList<String> excelData=new ArrayList<>();
    private static ArrayList<String> headerData=new ArrayList<>();
    private static WriteExcel wrExcel = new WriteExcel();
    private static int counter =0,writeExFlag=0;
    private static String filePath= new String() ;
    private Integer chartFlag = new Integer(0);
    
    public SerialConnection()
    {
        super();
    }   
    
    public int connect(String portName,String speed, String dataBit,
        String stopBit, String parity,String FlowC ,TextArea textAr) throws Exception {
        
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();      
        textArea=textAr;
        int dataV=-1,stopV=-1,parityV=-1;
        int speedV= Integer.parseInt(speed);
        /*Selecting data bit*/
        switch(dataBit){
            case "8": dataV= SerialPort.DATABITS_8; break;
            case "7": dataV= SerialPort.DATABITS_7; break;
            case "6": dataV= SerialPort.DATABITS_6; break;
            case "5": dataV= SerialPort.DATABITS_5; break;   
        }    
        switch(stopBit){
            case "1":   stopV= SerialPort.STOPBITS_1;   break;
            case "2":   stopV= SerialPort.STOPBITS_2;   break;
            case "1.5": stopV= SerialPort.STOPBITS_1_5; break;   
        }
        switch(parity){
            case "NONE":  parityV = SerialPort.PARITY_NONE; break;
            case "ODD":   parityV = SerialPort.PARITY_ODD; break;
            case "EVEN":  parityV = SerialPort.PARITY_EVEN; break;
            case "MARK":  parityV = SerialPort.PARITY_MARK; break;
            case "SPACE": parityV = SerialPort.PARITY_SPACE; break;          
        }
        
        if (portIdentifier.isCurrentlyOwned()) {
           System.out.println("Error: Port is currently in use");
           return -1;
        }
        else{
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);
            if (commPort instanceof SerialPort) {
                serialPort = (SerialPort) commPort;
                
                
                serialPort.setSerialPortParams(speedV, dataV,stopV, parityV);               
                
                InputStream in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
                
                /*Set Flow control mode*/
                if (FlowC.equals("HARDWARE")) {
                    serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
                    flowControlHw=1;
                    dsrFlag=1;
                    dtrFlag=1;
                    serialPort.setDTR(true);
                }
                serialPort.addEventListener(new SerialReader(in));
                serialPort.notifyOnDataAvailable(true);  
            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
                return -1;
            }
        }   
        return 1;
    }
    
    public void disConnect()
    {
        serialPort.close();    
    }        
    public static class SerialReader implements SerialPortEventListener {

        private InputStream in;
        private byte[] buffer = new byte[1024];

        public SerialReader(InputStream in) {
            this.in = in;
        }

        public void serialEvent(SerialPortEvent event) {
            int data;
            try {
                int len = 0;
                if(dtrFlag==1){
                    if(serialPort.isDTR()){
                        while ((data = in.read()) > -1) {
                            if (data == '\n') {
                                break;
                            }
                            buffer[len++] = (byte) data;
                        }
                    }
                }
                else{
                    while ((data = in.read()) > -1) {
                        if (data == '\n') {
                            break;
                        }
                        buffer[len++] = (byte) data;
                    }
                }
                //System.out.print(new String(buffer, 0, len)+"\n");
                String inp = new String(buffer, 0, len-1);
                if(!inp.isEmpty())
                {   
                    try {
                        Thread.sleep(25);
                    } catch(Exception ex){}
                    textArea.appendText(inp+"\n");
                }
                if(excelFlag==2){
                    
                    if(headerData.size()<headerSize){
                        headerData.add(inp);
                    } else{
                        excelFlag=3;
                    }
                }
                if(excelFlag==3){
                    excelData.add(inp);
                    counter++;
                    if(counter==headerSize && writeExFlag==1)
                    {   try{
                           wrExcel.writeToExcel(filePath+"/LTTerm.xls", headerData, excelData);
                        }catch(Exception e){e.printStackTrace();}
                        counter=0;
                        excelData.clear();
                    } 
                }
                if(excelFlag==1){/*Write data excel*/
                    headerSize = parseInt(inp);
                    excelFlag=2;
                } 
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
        
    public void seialWrite(String data)
    {
        String input="";
        /**
         *  Input parse işlemleri burada olacak
         */
        
        
        serialWriteHelper(data);
    }
    
    public void serialWriteHelper(String input){  
        int c = -1, i = 0;
        if (flowControlHw == 1) {
            /*Tell the divice ready to sent data*/
            serialPort.setRTS(true);
            while (!serialPort.isCTS());
        }
        try {
            while (input.length() > i) {
                c = (int) input.charAt(i);
                this.out.write(c);
                i++;
            }
            this.out.write('\n');
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
      
    /**
     * Getter method for dsr
     * @return 
     */
    public int getDsr(){
        return dsrFlag;
    }
    /**
     *  Getter method for dtr flag
     * @return 
     */
    public int getDtr(){
        return dtrFlag;
    }
    
    
    /**
     *  Helper method for some controlling
     *  1) DTR / DSR control signals on off
     */
    public void controlSerial(int value)
    {
        switch(value){
            case 1: dtrFlag=1; serialPort.setDTR(true); break;
            case 2: dtrFlag=0; serialPort.setDTR(false);break;
            case 3: dsrFlag=1; break;
            case 4: dsrFlag=0; break;    
        }   
    }  
    
    public void setExcelFlag(int flag)
    {
        /**
         *  flag = 0 : No excel,chart operation
         *  flag = 1 : start read how much header (integer)
         *  flag = 2 : read these headers 
         *  flag = 3 : read data one by one 
         */
        if(flag==0){
            writeExFlag=0;
        }
        excelFlag=flag;
    }
    
    
    
    /*Send data  LTTermLineChart*/
    public ArrayList<String> setValtoChart(){
        ArrayList<String> data=new ArrayList<>();
        if(counter!=headerSize)
            return null;
        data=  excelData;
        counter = 0;
        excelData.clear();
        return data;    
    }
    
    /**
     *  Draw the line chart 
     * @throws Exception 
     */
    public void drawChart(LTTermLineChart chart) throws Exception{
        
        chartFlag=1;     
        ArrayList<String> da=new ArrayList<>();
        
        da.add("burak");
        da.add("Demirci");
        chart.setHeads(da);
    }
    
    /**
     *  Write data to the excel file
     */
    public void excelOperation(String path){
        filePath=path;
        writeExFlag=1;
    }
    
    public static int parseInt(String inp){
        int answer = 0, factor = 1;
        for (int i = inp.length()-1; i >= 0; i--) {
            answer += (inp.charAt(i) - '0') * factor;
            factor *= 10;
        }
        return answer;
    }
       
}
