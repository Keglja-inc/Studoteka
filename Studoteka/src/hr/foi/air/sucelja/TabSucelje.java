package hr.foi.air.sucelja;

import hr.foi.air.modeli.FakultetModel;
import hr.foi.air.modeli.InteresModel;

import java.util.ArrayList;

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
