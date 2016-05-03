package oodp.android_sample_1;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class NewSmsActivity extends Activity {
	SQLiteDatabase db;
	SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_sms);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_sms, menu);
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
	
	// 메세지 보내기 버튼 클릭
	public void bt_do_send_Click(View view){
		Intent intent  = new Intent();
		EditText tb_receiver = (EditText)findViewById(R.id.tb_receiver);
		EditText tb_message = (EditText)findViewById(R.id.tb_message);
		
		intent.putExtra("receiver", tb_receiver.getText().toString());
		intent.putExtra("message", tb_message.getText().toString());
		
		setResult(RESULT_OK, intent);
		finish();
	}
	
	
}
