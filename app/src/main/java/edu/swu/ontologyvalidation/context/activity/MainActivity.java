package edu.swu.ontologyvalidation.context.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import edu.swu.ontologyvalidation.R;
import edu.swu.ontologyvalidation.fragment.StatisticsFragment;
import edu.swu.ontologyvalidation.fragment.QuestionListFragment;
import edu.swu.ontologyvalidation.fragment.UserFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

	private RadioButton mBtnList;
	private RadioButton mBtnCount;
	private RadioButton mBtnSetttings;
	private ActionBar mActionBar;
	Fragment mListFragment;
	Fragment mCountFragment;
	Fragment mSettingFragment;
	private boolean willExit = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mActionBar = getSupportActionBar();
		mActionBar.setShowHideAnimationEnabled(false);

		mBtnList = (RadioButton) findViewById(R.id.btn_list);
		mBtnList.setOnClickListener(this);
		mBtnCount = (RadioButton) findViewById(R.id.btn_count);
		mBtnCount.setOnClickListener(this);
		mBtnSetttings = (RadioButton) findViewById(R.id.btn_settings);
		mBtnSetttings.setOnClickListener(this);

		//设置初始的fragment
		if(mListFragment==null)
			mListFragment = new QuestionListFragment();
		changeFragment(mListFragment);
	}

	@Override
	public void onClick(View view) {
		Resources r = getResources();
		switch (view.getId()) {
			case R.id.btn_list:
				if (mListFragment == null)
					mListFragment = new QuestionListFragment();
				if(!mActionBar.isShowing())
					mActionBar.show();
				mActionBar.setTitle(r.getString(R.string.fragment_list_title));
				changeFragment(mListFragment);
				break;
			case R.id.btn_count:
				if (mCountFragment == null)
					mCountFragment = new StatisticsFragment();
				if(!mActionBar.isShowing())
					mActionBar.show();
				mActionBar.setTitle(r.getString(R.string.fragment_statistics_title));
				changeFragment(mCountFragment);
				break;
			case R.id.btn_settings:
				if (mSettingFragment == null)
					mSettingFragment = new UserFragment();
				if(!mActionBar.isShowing())
					mActionBar.show();
				mActionBar.setTitle(r.getString(R.string.fragment_user_title));
				changeFragment(mSettingFragment);
				break;
		}

	}

	private void changeFragment(Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fl_content, fragment);
		transaction.commit();
	}

	@Override
	public void onBackPressed() {
		// press twice to exit
		Timer tExit;
		if (!willExit) {
			willExit = true; // ready to exit
			Toast.makeText(
					this,
					getResources().getString(R.string.tip_exit_app),
					Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					willExit = false; // cancel exit
				}
			}, 2000); // 2 seconds cancel exit task

		} else {
			finish();
			// call fragments and end streams and services
			System.exit(0);
		}
	}


}
