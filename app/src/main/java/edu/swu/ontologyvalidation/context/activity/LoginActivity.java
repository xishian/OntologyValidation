package edu.swu.ontologyvalidation.context.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import edu.swu.ontologyvalidation.R;
import edu.swu.ontologyvalidation.util.NetworkClient;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

	public static final String EXTRA_BITMAP_HEAD = "LoginActivity.EXTRA_BITMAP_HEAD";
	public static final String EXTRA_BITMAP_USER = "LoginActivity.EXTRA_BITMAP_USER";

	private EditText mEtUsername;
	private EditText mEtPassword;
	private Button mBtnLogin;
	private TextView mTvRegister;
	private ProgressBar mPbLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getSupportActionBar()!=null)
			getSupportActionBar().hide();
		setContentView(R.layout.activity_login);

		mEtUsername = (EditText)findViewById(R.id.input_email);
		mEtPassword = (EditText)findViewById(R.id.input_password);
		mBtnLogin = (Button)findViewById(R.id.btn_activity_loign_login);
		mBtnLogin.setOnClickListener(this);
		mTvRegister = (TextView)findViewById(R.id.link_signup);
		mTvRegister.setOnClickListener(this);
		mPbLogin = (ProgressBar)findViewById(R.id.pb_activity_login);



	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.btn_activity_loign_login:
				String username = mEtUsername.getText().toString();
				String password = mEtPassword.getText().toString();
				Toast.makeText(this,"登录失败",Toast.LENGTH_SHORT).show();
				//new LoginTask().execute(username,password);
				break;
			case R.id.link_signup:
				Toast.makeText(this,"注册功能还未开放，请联系管理员申请账号",Toast.LENGTH_SHORT).show();
				break;
		}
	}


	class LoginTask extends AsyncTask<String,Integer,Integer>{


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mPbLogin.setVisibility(View.VISIBLE);

		}

		@Override
		protected Integer doInBackground(String... params) {
			int result = 0;
			try {
				result = NetworkClient.login(params[0],params[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer integer) {
			super.onPostExecute(integer);
			mPbLogin.setVisibility(View.INVISIBLE);

			if(integer==1)
			{
				Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
//				User u = new User();
//				Intent intent = new Intent();
//				intent.putExtra(EXTRA_BITMAP_USER,u);
//				setResult(RESULT_OK,intent);
				finish();
			}else if(integer==101){
				Toast.makeText(LoginActivity.this,"登录失败，请检查密码是否输入正确",Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();

			}
		}
	}//end task
}
