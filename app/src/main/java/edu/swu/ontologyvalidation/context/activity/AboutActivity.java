package edu.swu.ontologyvalidation.context.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.swu.ontologyvalidation.R;

public class AboutActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getSupportActionBar()!=null)
			getSupportActionBar().hide();
		setContentView(R.layout.activity_about);
	}
}
