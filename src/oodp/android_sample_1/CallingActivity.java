package oodp.android_sample_1;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class CallingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calling);
		TextView tb_opposite_number = (TextView)findViewById(R.id.opposite_number_info);
		TextView tb_start_time = (TextView)findViewById(R.id.call_start_time);
		tb_opposite_number.setText(intent.getStringExtra("call_opposite_number"));
		tb_start_time.setText(intent.getStringExtra("start_time"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calling, menu);
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
	
	public void bt_call_end_Click(View view){
		Intent intent = new Intent();
		TextView tv_start_time = (TextView)findViewById(R.id.call_start_time);
		TextView tv_opposite_number = (TextView)findViewById(R.id.opposite_number_info);
		long time = System.currentTimeMillis(); 
		
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String call_end_time = dayTime.format(new Date(time));
		
		intent.putExtra("start_time", tv_start_time.getText().toString());
		intent.putExtra("end_time", call_end_time);
		intent.putExtra("number", tv_opposite_number.getText().toString());
		
		setResult(RESULT_OK, intent);
		finish();
	}
}
