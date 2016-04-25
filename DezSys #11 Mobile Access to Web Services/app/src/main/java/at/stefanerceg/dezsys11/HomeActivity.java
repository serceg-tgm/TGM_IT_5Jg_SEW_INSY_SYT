package at.stefanerceg.dezsys11;

import android.app.Activity;
import android.os.Bundle;

import com.prgguru.dezsys11.R;

/**
 * 
 * Home Screen Activity
 */
public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Displays Home Screen
		setContentView(R.layout.home);
	}

}
