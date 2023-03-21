package controller;


import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modul.Call;
import modul.MySqlConnection;


public class DetailController implements Initializable{
	@FXML
	private Label idLabel;
	@FXML
	private Button backButton;
	@FXML
	private TextField totalTimeTextField;
	@FXML 
	private DatePicker datePicker1;
	@FXML 
	private DatePicker datePicker2;
	
	@FXML
	private TableView<Call> detail;
	@FXML 
	TableColumn<Call, Long> timeReceive;
	@FXML 
	TableColumn<Call, Time> timeStart;
	@FXML 
	TableColumn<Call, Time> timeFinish;
	@FXML 
	TableColumn<Call, String> phoneNumber;
	private ObservableList<Call> detailList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		detailList = FXCollections.observableArrayList(
				
		);
		timeReceive.setCellValueFactory(new PropertyValueFactory<Call, Long>("timeReceive"));
		timeStart.setCellValueFactory(new PropertyValueFactory<Call, Time>("timeStart"));
		timeFinish.setCellValueFactory(new PropertyValueFactory<Call, Time>("timeFinish"));
		phoneNumber.setCellValueFactory(new PropertyValueFactory<Call, String>("phoneNumber"));
		detail.setItems(detailList);
		
	}
	
	public void setIDDienThoaiVien(String id) {
		idLabel.setText(String.valueOf(id));
	}
	
	public void showInfor() {
		MySqlConnection telephonist = new MySqlConnection();
		ResultSet rs = telephonist.getInformation();
		try {
			if (rs != null) {
				while (rs.next()) {
					if (rs.getInt("idTelephonist") == Integer.parseInt(idLabel.getText())) {
						Call c1 = new Call();
						c1.setTimeStart(rs.getTime("timeStart"));
						c1.setTimeFinish(rs.getTime("timeFinish"));
						c1.setTimeReceive(rs.getLong("timeReceive"));
						c1.setPhoneNumber(Long.parseLong(rs.getString("phoneNumber")));
						detailList.add(c1);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void goBack (ActionEvent event) throws IOException  {
		backButton.getScene().getWindow().hide();
	}
	
	// caclulate total time and show 
	public long totalTimeReceived (int id, Date dateFind ) {
		long totalTime = 0 ;
		
		String line = "";  
		String splitBy = ","; 
		
		DateFormat dfm = new SimpleDateFormat("yyyyMMdd");
			 
		try {
			FileReader fileReader = new FileReader("fileCSV/"+ dfm.format(dateFind) + ".csv");
			if (fileReader != null) {
				BufferedReader br = new BufferedReader(fileReader);
			
				while ((line = br.readLine()) != null) {
					String[] stringLine = line.split(splitBy);
					
					if (Integer.parseInt(stringLine[0])  == id) {
						
						totalTime = totalTime +  Long.parseLong(stringLine[3]);
						Call cg = new Call();
						SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
						Date date = sdf.parse(stringLine[1]);
						Time time = new Time(date.getTime());
						cg.setTimeStart(time);
						Date date2 = sdf.parse(stringLine[2]);
						Time time2 = new Time(date2.getTime());
						cg.setTimeFinish(time2);
						cg.setTimeReceive(Long.parseLong(stringLine[3]));
						cg.setPhoneNumber(Long.parseLong(stringLine[4]));
						detailList.add(cg);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return totalTime;
	}
	
	public void find() {
		detailList.clear();
		
		Calendar calendar1 =  Calendar.getInstance();
		LocalDate localDate = datePicker1.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date date1 = Date.from(instant);

		LocalDate localDate2 = datePicker2.getValue();
		Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
		Date date2 = Date.from(instant2);
		
		calendar1.setTime(date1);
		long totalTime = 0;
		while (date1.before(date2)) {
			totalTime += totalTimeReceived(Integer.parseInt(idLabel.getText()), date1);
			calendar1.add(Calendar.DATE, 1);
			date1 = calendar1.getTime();
		}
		totalTime += totalTimeReceived(Integer.parseInt(idLabel.getText()), date2);
		totalTimeTextField.setText(String.valueOf(totalTime));
	}
}











