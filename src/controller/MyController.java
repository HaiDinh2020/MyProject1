package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modul.Call;
import modul.Telephonist;
import modul.MySqlConnection;
import modul.PhoneNumber;


public class MyController implements Initializable{
	
	 @FXML 
	 private TableView<PhoneNumber> phoneNumberTV;
	 @FXML 
	 TableColumn<PhoneNumber, Integer> phoneNumberCol;
	 private ObservableList<PhoneNumber> phoneNumberList;
	 @FXML
	 private Button call;
	 private Telephonist telephonist[] = new Telephonist[10];
	 
	 @FXML 
	 private TableView<Telephonist> telephonistFreeTV;
	 @FXML 
	 TableColumn<Telephonist, Integer> idTelephonistFreeCol;
	 @FXML 
	 TableColumn<Telephonist, Button> free;
	 private ObservableList<Telephonist> telephonistFreeList;
	 
	 @FXML 
	 private TableView<Telephonist> telephonistBusyTV;
	 @FXML 
	 TableColumn<Telephonist, Integer> idTelephonistBusyCol;
	 @FXML 
	 TableColumn<Telephonist, Button> busy;
	 private ObservableList<Telephonist> telephonistBusyList;
	 
	 
	 @FXML 
	 private TableView<Call> callTV;
	 @FXML
	 TableColumn<Call, Integer> idTelephonistCol;
	 @FXML
	 TableColumn<Call, Button> turnoff;
	 @FXML
	 TableColumn<Call, Button> startRecord;
	 private Button buttonStatus ;
	 private Button buttonRecord;
	 private ObservableList<Call> callList;
	 
	 @FXML
	 private Label noticeLabel;
	 
	 JavaSoundRecorder javaSoundRecorder;
	 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i< 10; i++) {
			telephonist[i] = new Telephonist(1000 + i);
			telephonist[i].getIsBusy().setOnAction(this::startBusy);
			
		}
		telephonistFreeList = FXCollections.observableArrayList(
				telephonist[0],telephonist[1],telephonist[2],telephonist[3],telephonist[4],telephonist[5],telephonist[6]
		);
		idTelephonistFreeCol.setCellValueFactory(new PropertyValueFactory<Telephonist, Integer>("id"));
		free.setCellValueFactory(new PropertyValueFactory<Telephonist, Button>("isBusy"));
		telephonistFreeTV.setItems(telephonistFreeList);
		
		telephonistBusyList = FXCollections.observableArrayList(
		
		);
		idTelephonistBusyCol.setCellValueFactory(new PropertyValueFactory<Telephonist, Integer>("id"));
		busy.setCellValueFactory(new PropertyValueFactory<Telephonist, Button>("isBusy"));
		telephonistBusyTV.setItems(telephonistBusyList);
		
		phoneNumberList = FXCollections.observableArrayList(
				
		);
		phoneNumberCol.setCellValueFactory(new PropertyValueFactory<PhoneNumber, Integer>("phonNumber"));
		phoneNumberTV.setItems(phoneNumberList);
		
		callList = FXCollections.observableArrayList(
				
		);
		idTelephonistCol.setCellValueFactory(new PropertyValueFactory<Call, Integer>("idTelephonist"));
		turnoff.setCellValueFactory(new PropertyValueFactory<Call, Button>("buttonStatus"));
		startRecord.setCellValueFactory(new PropertyValueFactory<Call, Button>("buttonRecord"));;
		callTV.setItems(callList);
		
	}

	// sinh so ngau nhien them vao sdtList
	public void call(ActionEvent e) {
		PhoneNumber newsdt = new PhoneNumber();
		Random generator = new Random();
		long value = generator.nextLong((999999999 - 100000000) + 1) + 100000000;
		newsdt.setPhonNumber(value);
		if (phoneNumberList.size() < 5) {
			phoneNumberList.add(newsdt);
		} else {
			noticeLabel.setText("Unable to call, please wait a few minutes!");
		}
		System.out.println("size of sdtList: " + phoneNumberList.size());
		controllTable();
	}
	
	// them cuoc goi dang dien ra vao Bang cuocGoiList 
	public void controllTable () {
		if (!phoneNumberList.isEmpty()  && !telephonistFreeList.isEmpty()) {
			Date d2 = new Date();
			Time timeStart = new Time(d2.getTime());
			buttonStatus = new Button();
			buttonRecord = new Button();
			setButton(buttonStatus);
			setButton(buttonRecord);
			
			Call c1 = new Call(telephonistFreeList.get(0).getId(),timeStart , null, phoneNumberList.get(0).getPhonNumber(), buttonStatus, buttonRecord);
			c1.getButtonStatus().setOnAction(this::handleButtonAction);
			c1.getButtonRecord().setOnAction(this::StartRecording);
			callList.addAll(c1);
			telephonistFreeList.remove(0);
			phoneNumberList.remove(0);
			if (phoneNumberList.size() < 5) {
				noticeLabel.setText(null);
			}
		} 
	}
	
	public void setButton(Button button) {
		button.setStyle("-fx-background-color: #00ff40;  -fx-text-fill: #ffffff;");
	}
	
	// tắt cuộc gọi và thêm idDTV vào rỗi, cho lại và cuocGoiList nếu có sdt chờ
	private void handleButtonAction (ActionEvent event) { 
		Button b = (Button) event.getSource();
		b.setText("TurnOn");
		for (Call i : callList) {
			if (i.getButtonStatus().getText() == "TurnOn") {
				Telephonist telephonist = new Telephonist(i.getIdTelephonist());
				telephonist.getIsBusy().setOnAction(this::startBusy);;
				telephonistFreeList.add(telephonist);
				
				Date d1 = new Date();	
				Time timeFinish = new Time(d1.getTime());
				long difference = d1.getTime() - i.getTimeStart().getTime();
				long timeReceive = difference / 1000 % 60;
				MySqlConnection t1 = new MySqlConnection();
				t1.insertTelephonist(i.getIdTelephonist() , i.getTimeStart(), timeFinish, timeReceive, Long.toString(i.getPhoneNumber()));
				callList.remove(i);
				controllTable();
				break;
			}
		}
	}
	
	private void startBusy (ActionEvent event) {
		Button b = (Button) event.getSource();
		if (b.getText() == "Free") {
			b.setText("Busy");
			for (Telephonist i : telephonistFreeList) {
				if (i.getIsBusy().getText() == "Busy") {
					telephonistBusyList.add(i);
					telephonistFreeList.remove(i);
				}
			}
		} else if (b.getText() == "Busy") {
			b.setText("Free");
			for (Telephonist i : telephonistBusyList) {
				if (i.getIsBusy().getText() == "Free") {
					telephonistFreeList.add(i);
					telephonistBusyList.remove(i);
					controllTable();
				}
			}
		}
	}
	
    public void StartRecording(ActionEvent event)  {
    	Button start = (Button) event.getSource();
    	DateFormat dfm = new SimpleDateFormat("yyyyMMdd");
    	
    	if (start.getText().equals("Start")) {  
    		//getTelephonNumber, getIdTelephonist
    		int id = 0;
    		long sdt = 0;
    		for (Call i : callList) {
    			if (i.getButtonRecord().getText().equals("Start")) {
    				id = i.getIdTelephonist();
    				sdt = i.getPhoneNumber();
    			}
    		}
            javaSoundRecorder  = new JavaSoundRecorder();
            javaSoundRecorder.setWavFile(new File("fileAudio/" + id + "_" + sdt + "_" + dfm.format(new Date()) +".wav"));
            Thread thread = new Thread(javaSoundRecorder);
            thread.start();

            start.setText("Stop");
        }
        else {
            javaSoundRecorder.finish();
            javaSoundRecorder.cancel();

            start.setText("Start");                
        }

    }
	
	public void detail (ActionEvent event) throws IOException  {
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Button button = (Button) event.getSource();
		FXMLLoader loader = new FXMLLoader();			
		loader.setLocation(getClass().getClassLoader().getResource("fxml/Detail.fxml"));			
		loader.load();
		Parent dtvParen = loader.getRoot();
		
		DetailController DetailController = (DetailController) loader.getController();
		DetailController.setIDDienThoaiVien(button.getText());
		DetailController.showInfor();
		
		Stage modal_dialog = new Stage(StageStyle.DECORATED);
        modal_dialog.initModality(Modality.WINDOW_MODAL);
        modal_dialog.initOwner(stage);
        modal_dialog.setTitle("Detail");
        Image image = new Image(new FileInputStream("src/images/icon.png"));
        modal_dialog.getIcons().add(image);
		Scene scene = new Scene(dtvParen);	
		modal_dialog.setScene(scene);
		modal_dialog.show();
		
	}
	
	public void exportCSVFile() {
		MySqlConnection mySqlConnection ;
		ResultSet rs;
		mySqlConnection = new MySqlConnection();
		rs = mySqlConnection.getInformation();
		
		DateFormat dfm =new  SimpleDateFormat("yyyyMMdd");

	    try {
	        File file = new File("fileCSV/" + dfm.format(new Date()) + ".csv");
	        FileWriter fw = new FileWriter(file, true);
	        BufferedWriter writer = new BufferedWriter( fw );

	        while (rs.next()) {
	        	String text = rs.getInt("idTelephonist") + "," + rs.getTime("timeStart") + "," + rs.getTime("timeFinish") 
	        					+ "," + rs.getLong("timeReceive") + "," + rs.getString("phoneNumber");
	            writer.write(text);
	            writer.newLine();
	        }
	        System.out.println("export successfully!");
	        writer.close();
	        mySqlConnection.deleteInfor();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }	
	}
}














