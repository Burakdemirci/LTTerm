package ltterm;
/**
 *
 * @author Burak Demirci
 */
import gnu.io.CommPortIdentifier;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ltterm.communication.SerialConnection;
import ltterm.lineChart.LTTermLineChart;

public class FXMLController implements Initializable {
    
    private SerialConnection serial = new SerialConnection();
    @FXML
    private ComboBox<String>port = new ComboBox();
    @FXML
    private TextField speed = new TextField (); 
    @FXML
    private TextArea textArea = new TextArea();
    @FXML
    private Button serial_btn = new Button();
    @FXML
    private TextField input= new TextField();    
    @FXML
    private Label PortL= new Label();
    @FXML
    private TextField directoy_show = new TextField();
    @FXML
    private Button excel_btn = new Button();
    @FXML
    private Button liveChart = new Button();
    @FXML
    private Button stop_excel = new Button();    
    @FXML
    private Button countup_btn1 = new Button();
    @FXML
    private Button countdwn_btn1 = new Button();
    @FXML
    private TextField count1text =new TextField();
    @FXML
    private TextField system_call_text = new TextField();
    @FXML 
    private  ComboBox<String> parity = new ComboBox();
    @FXML
    private ComboBox<String> data = new ComboBox();
    @FXML
    private ComboBox<String> stop = new ComboBox();
    @FXML
    private ComboBox<String> flowControl=new ComboBox();
    @FXML
    private Button dtr_btn = new Button();
    @FXML
    private Button dsr_btn = new Button();
    @FXML 
    private Button multi_1 = new Button();
    @FXML 
    private Button multi_2 = new Button();
    @FXML 
    private Button multi_3 = new Button();
    @FXML 
    private Button multi_4 = new Button();
    @FXML 
    private Button multi_5 = new Button();
    @FXML 
    private Button multi_6 = new Button();
    @FXML 
    private Button multi_7 = new Button();
    @FXML 
    private Button multi_8 = new Button();
    @FXML 
    private Button multi_9 = new Button();
    @FXML 
    private Button multi_10 = new Button();
    @FXML 
    private Button multi_11 = new Button();
    @FXML 
    private Button multi_12 = new Button();
    @FXML 
    private Button multi_13 = new Button();
    @FXML 
    private Button multi_14 = new Button();
    @FXML 
    private Button multi_15 = new Button();
    @FXML 
    private Button multi_16 = new Button();
    @FXML 
    private Button multi_17 = new Button();
    @FXML 
    private Button multi_18 = new Button();
    @FXML 
    private static TextField multi1_Name = new TextField(); 
    @FXML
    private static TextField multi1_Command = new TextField();
    @FXML
    private TextField multi2_Name = new TextField();
    @FXML
    private TextField multi2_Command = new TextField();
    @FXML
    private TextField multi3_Name = new TextField();
    @FXML
    private TextField multi3_Command = new TextField();
    @FXML
    private TextField multi4_Name = new TextField();
    @FXML
    private TextField multi4_Command = new TextField();
    @FXML
    private TextField multi5_Name = new TextField();
    @FXML
    private TextField multi5_Command = new TextField();
    @FXML
    private TextField multi6_Name = new TextField();
    @FXML
    private TextField multi6_Command = new TextField();
    @FXML
    private TextField multi7_Name = new TextField();
    @FXML
    private TextField multi7_Command = new TextField();
    @FXML
    private TextField multi8_Name = new TextField();
    @FXML
    private TextField multi8_Command = new TextField();
    @FXML
    private TextField multi9_Name = new TextField();
    @FXML
    private TextField multi9_Command = new TextField();
    @FXML
    private TextField multi10_Name = new TextField();
    @FXML
    private TextField multi10_Command = new TextField();
    @FXML
    private TextField multi11_Name = new TextField();
    @FXML
    private TextField multi11_Command = new TextField();
    @FXML
    private TextField multi12_Name = new TextField();
    @FXML
    private TextField multi12_Command = new TextField();
    @FXML
    private TextField multi13_Name = new TextField();
    @FXML
    private TextField multi13_Command = new TextField();
    @FXML
    private TextField multi14_Name = new TextField();
    @FXML
    private TextField multi14_Command = new TextField();
    @FXML
    private TextField multi15_Name = new TextField();
    @FXML
    private TextField multi15_Command = new TextField();
    @FXML
    private TextField multi16_Name = new TextField();
    @FXML
    private TextField multi16_Command = new TextField();
    @FXML
    private TextField multi17_Name = new TextField();
    @FXML
    private TextField multi17_Command = new TextField();
    @FXML
    private TextField multi18_Name = new TextField();
    @FXML
    private TextField multi18_Command = new TextField();
    @FXML 
    private static Button m1_set = new Button();
    
    private static int count1=0,connected=-1,excelFlag=0;
    
    private static String strr;
    
    //LTTermLineChart lineChart = new LTTermLineChart();

    /*----------------------------------------------------------------------*/
    
    public void count1Up(ActionEvent event){
       count1++;
       count1text.setText(String.valueOf(count1));
    }
    public void count1Down(ActionEvent event){
        count1--;
        count1text.setText(String.valueOf(count1));
    }
    
    public void count2Up(ActionEvent event){}
    public void count2Down(ActionEvent event){}
    
    public void SystemCallButton_1(ActionEvent event)throws IOException{
        
        if(!system_call_text.getText().isEmpty()){
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(system_call_text.getText());
        }
    }
    
    public void SystemCallButton_2(ActionEvent event)throws IOException{}
    public void SystemCallButton_3(ActionEvent event)throws IOException{}
    
    
    public void Settings(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Fxmls/Setting.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("LTTerm Serial Settings");
        stage.setScene(scene);
        stage.show();                   
    }
        
    /**
     *  connect the serial port 
     * @param event 
     */
    public void handleSerialButon(ActionEvent event) {
        
        if(connected==-1){
            String speedStr = speed.getText();
            if (flowControl.getValue().compareTo("HARDWARE") == 0) {
                dtr_btn.setStyle("-fx-font-weight: bold; -fx-text-fill: #00ff00");
                dsr_btn.setStyle("-fx-font-weight: bold; -fx-text-fill: #00ff00");
            }
            try {
                textArea.appendText("Connected.\n");
                connected = serial.connect(port.getValue(), speedStr, data.getValue(),
                        stop.getValue(), parity.getValue(), flowControl.getValue(), textArea);

                if (connected != -1) {

                    excel_btn.setDisable(false);
                    dsr_btn.setDisable(false);
                    dtr_btn.setDisable(false);
                    liveChart.setDisable(false);
                    input.setDisable(false);
                    stop_excel.setDisable(false);
                    countdwn_btn1.setDisable(false);
                    countup_btn1.setDisable(false);
                    count1text.setDisable(false);
                    serial_btn.setText("Disconnect");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            
        
        }
    }
    /**
     *  initialize the application start value
     * @param url
     * @param rb 
     */    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /*Add combox to value*/
        parity.getItems().addAll("NONE","ODD","EVEN","MARK","SPACE");
        parity.setValue("NONE");
        /*---------------------*/
        data.getItems().addAll("8","7","6","5");
        data.setValue("8");
        /*--------------------*/
        stop.getItems().addAll("1","2","1.5");
        stop.setValue("1");
        /*-------------------*/
        flowControl.getItems().addAll("NONE","HARDWARE");
        flowControl.setValue("NONE");
        /*-------------------*/
        port.setValue("NULL");

        /*--------------------*/        
        directoy_show.setText(System.getProperty("user.home")); 
        /*Set text field and textArea font and colour*/
        textArea.setStyle("-fx-background-color: purple; -fx-text-inner-color: green;");
        textArea.setFont(Font.font(null, FontWeight.BOLD, 12));
        input.setStyle("-fx-text-inner-color: blue;");
        input.setFont(Font.font(null, FontWeight.BOLD, 17)); 
        count1text.setText(String.valueOf(count1));

        /*------------------------------------*/
        liveChart.setDisable(true);
        excel_btn.setDisable(true);
        input.setDisable(true);
        stop_excel.setDisable(true);
        countdwn_btn1.setDisable(true);
        countup_btn1.setDisable(true);
        count1text.setDisable(true);
        /*-----------------------------------*/
        if(listSerialPorts()< 1)
        {
            textArea.setText("Warning !!\nThere is no available serial port\n");
            textArea.appendText("\n\n\t\tPress Connect button for search port\n");
            /*Wait until serial port plugin */
        }
        
        SetAllListener();
    }   
    public void loadData(){
    
    
    
    }
    
    public void SetAllListener(){
         
        multi_1.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                MouseButton button = event.getButton();
                if (button == MouseButton.PRIMARY) {
                    //serial.seialWrite(multi1_Command.getText());
                    input.setText("BURADAAA");
                } else if (button == MouseButton.SECONDARY) {
                    
                    try {
                        Multi_FXMLoader("multi_1");
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
                }
            }
        });
        m1_set.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            String str;
            @Override
            public void handle(MouseEvent event) {
                
                strr=multi1_Name.getText();
                System.out.println(strr+"**");
                multi_1.setText(strr);             
                
            }
        });
        
        
        input.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                serial.seialWrite(input.getText());
                textArea.appendText("@LTTerm > " + input.getText() + "\n");
                input.setText("");
            }
        });
    }
        
    /**
     * FXML loader for multi buttons
     * @param name multi button id
     * @throws IOException 
     */
    public void Multi_FXMLoader(String name) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Fxmls/multi/"+name+".fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("LTTerm "+name+" button set");
        stage.setScene(scene);
        stage.show();
    }
            
    
    /*---------------------------------------------------------------------*/
    /**
     * Finding available ports
     * @return port list
     */
    public int listSerialPorts(){
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
        int availablePort=0;
        port.getItems().removeAll(port.getItems());
        while (ports.hasMoreElements()) {
            CommPortIdentifier portV = (CommPortIdentifier) ports.nextElement();
            if (portV.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                port.getItems().add(portV.getName());
                port.setValue(portV.getName());
                availablePort++;
            }
        }
        return availablePort;
    }       
    /**
    *  Live Chart buton event
    * @param event 
    */
    public void liveChartButn(ActionEvent event) throws Exception {
        serial.seialWrite("-excel");
        serial.setExcelFlag(1);
        LTTermLineChart chart= new LTTermLineChart(serial);
        serial.drawChart(chart);
        textArea.appendText("@LTTerm > "+"-excel\n");
    }
    /**
     *  Write data to the excel file
     * @param event 
     */
    public void handleExcel(ActionEvent event){
        if(excelFlag==0){
            serial.excelOperation(directoy_show.getText());
            serial.setExcelFlag(1);
            serial.seialWrite("-excel");
            textArea.appendText("@LTTerm > "+"-excel\n");
            excelFlag=1;
        }else
            serial.setExcelFlag(0);
        
    }
    
    /**
     *  Choosing excel file directory
     * @param event 
     */
    public void choseDirectory(ActionEvent event){
    
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Directory");
        chooser.setInitialDirectory(new File(directoy_show.getText()));
        Stage child = new Stage();        
        File dir = chooser.showDialog(child);
        if (dir != null) {
            String selectedDir = dir.toString();
            directoy_show.setText(dir.getPath());
        }     
    }
    
    /**
     *  controlling DSR signal
     * @param event 
     */
    public void handleDSRButon(ActionEvent event){
        
        if(serial.getDsr()==0){
            dsr_btn.setStyle("-fx-font-weight: bold; -fx-text-fill: #00ff00");
            serial.controlSerial(3);
        }else{
            dsr_btn.setStyle("");
            serial.controlSerial(4);
        }    
    }
    /**
     *  controlling DTR signal
     * @param event 
     */
    public void handleDTRButon(ActionEvent event){
        if (serial.getDtr() == 0){
            dtr_btn.setStyle("-fx-font-weight: bold; -fx-text-fill: #00ff00");
            serial.controlSerial(1);
        } else {
            dtr_btn.setStyle("");
            serial.controlSerial(2);
        }
    }  
}
