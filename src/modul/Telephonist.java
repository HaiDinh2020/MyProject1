package modul;

import javafx.scene.control.Button;

public class Telephonist {
	private int id;
	private Button isBusy;
	private Call call;
	
	public Telephonist() {
	}

	public Telephonist(int id) {
		this.id = id;
		isBusy = new Button("Free");
		isBusy.setStyle("-fx-background-color: #00ff40;  -fx-text-fill: #ffffff;");
	}
	
	public Call getCall() {
		return call;
	}

	public void setCall(Call call) {
		this.call = call;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Button getIsBusy() {
		return isBusy;
	}

	public void setIsBusy(Button isBusy) {
		this.isBusy = isBusy;
	}
	
	
}
