package oodp.android_sample_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class NewPeopleActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_people);
		
		Intent intent = getIntent();

		/*
		 * intent로 전달받은 문자열을 통해 EditText의 초기 문자열을 설정하는 코드입니다.
		 */
		EditText tb_name = (EditText)findViewById(R.id.tb_name);
		EditText tb_email = (EditText)findViewById(R.id.tb_receiver);
		EditText tb_group = (EditText)findViewById(R.id.tb_group);
		EditText tb_number = (EditText)findViewById(R.id.tb_massage);
		
		tb_name.setText(intent.getStringExtra("name"));
		tb_number.setText(intent.getStringExtra("number"));
		tb_email.setText(intent.getStringExtra("email"));
		tb_group.setText(intent.getStringExtra("group"));
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.new_people, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if ( id == R.id.action_settings )
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void bt_accept_Click(View view)
	{
		Intent intent = new Intent();
		
		/*
		 * EditText로 입력받은 문자열을 전달하기 위해 intent에 담는 코드입니다.
		 */
		EditText tb_name = (EditText)findViewById(R.id.tb_name);
		EditText tb_email = (EditText)findViewById(R.id.tb_receiver);
		EditText tb_number = (EditText)findViewById(R.id.tb_massage);
		EditText tb_group = (EditText)findViewById(R.id.tb_group);
		
		intent.putExtra("name", tb_name.getText().toString());
		intent.putExtra("email", tb_email.getText().toString());
		intent.putExtra("number", tb_number.getText().toString());
		intent.putExtra("group", tb_group.getText().toString());
		
		/*
		 * 이 Activity의 작업을 성공적으로 종료하고 이전 Activity로 돌아가기 위한 코드입니다.
		 */
		setResult(RESULT_OK, intent);
		finish();
	}
}
