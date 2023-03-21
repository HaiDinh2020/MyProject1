package modul;

import java.sql.Time;

import javafx.scene.control.Button;

public class Call {
	private int idTelephonist;
	private Time timeStart;
	private Time timeFinish;
	private Long timeReceive;
	private long phoneNumber;
	private Button buttonStatus;
	private Button buttonRecord;
	
	public Call(int idTelephonist, Time timeStart, Time timeFinish, long phoneNumber, Button buttonStatus, Button buttonRecord ) {
		this.idTelephonist = idTelephonist;
		this.timeStart = timeStart;
		this.timeFinish = timeFinish;
		this.phoneNumber = phoneNumber;
		this.buttonStatus = buttonStatus;
		this.buttonStatus.setText("TurnOff");
		this.buttonRecord = buttonRecord;
		this.buttonRecord.setText("Start");
	}
	
	public Call() {
	}

	public int getIdTelephonist() {
		return idTelephonist;
	}

	public void setIdTelephonist(int idTelephonist) {
		this.idTelephonist = idTelephonist;
	}

	public Time getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}

	public Time getTimeFinish() {
		return timeFinish;
	}

	public void setTimeFinish(Time timeFinish) {
		this.timeFinish = timeFinish;
	}

	
	public Long getTimeReceive() {
		return timeReceive;
	}

	public void setTimeReceive(Long timeReceive) {
		this.timeReceive = timeReceive;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Button getButtonStatus() {
		return buttonStatus;
	}

	public void setButtonStatus(Button buttonStatus) {
		this.buttonStatus = buttonStatus;
	}

	public Button getButtonRecord() {
		return buttonRecord;
	}

	public void setButtonRecord(Button buttonRecord) {
		this.buttonRecord = buttonRecord;
	}
	
}
