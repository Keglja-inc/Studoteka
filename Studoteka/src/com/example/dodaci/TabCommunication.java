package com.example.dodaci;

import java.util.ArrayList;

import com.example.modeli.FakultetiModel;
import com.example.modeli.Interes;

public interface TabCommunication {
	
	public void SendData(ArrayList<Interes> data);
	
	public void SendData2(ArrayList<FakultetiModel> data);

}
