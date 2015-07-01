package com.example.sucelja;

import java.util.ArrayList;

import com.example.modeli.FakultetModel;
import com.example.modeli.InteresModel;

/**
 * Su�elje u kojem su definirane metode koje se koriste za prijenos podataka
 * izme�u fragmenata
 * 
 * @author Ivan
 *
 */
public interface TabSucelje {

	public void SendData(ArrayList<InteresModel> data);

	public void SendData2(ArrayList<FakultetModel> data);

}
