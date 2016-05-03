package oodp.android_sample_1;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class NewCallActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_call);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_call, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void bt_start_call_Click(View view){
		Intent intent = new Intent(this, CallingActivity.class);
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String call_start_time = dayTime.format(new Date(time));
		
		EditText tb_number = (EditText)findViewById(R.id.tb_call_opposite_number);
		intent.putExtra("call_opposite_number", tb_number.getText().toString());
		intent.putExtra("start_time", call_start_time);
		
		startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Intent intent = new Intent();
			String number = data.getStringExtra("number");
			String start_time = data.getStringExtra("start_time");
			String end_time = data.getStringExtra("end_time");
			intent.putExtra("number", number);
			intent.putExtra("start_time", start_time);
			intent.putExtra("end_time", end_time);
			
			setResult(RESULT_OK, intent);
			finish();
		}
	}
}
