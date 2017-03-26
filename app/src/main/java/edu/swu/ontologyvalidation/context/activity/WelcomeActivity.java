package edu.swu.ontologyvalidation.context.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import edu.swu.ontologyvalidation.R;
import edu.swu.ontologyvalidation.data.QuestionLab;

public class WelcomeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getSupportActionBar()!=null)
			getSupportActionBar().hide();
		setContentView(R.layout.activity_welcome);

		//start load
		QuestionLab.loadQuestions();

		/* This is a delay template */
		new CountDownTimer(2000, 100) {
			@Override
			public void onTick(long millisUntilFinished) {
				// Animation can be here.
			}

			@Override
			public void onFinish() {
				// time-up, and jump
				Intent intent = new Intent();
				intent.setClass(WelcomeActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.hold); // fade in animation
				finish(); // destroy itself
			}
		}.start();

	}

	@Override
	public void onBackPressed() {
		// hijack
	}
}
