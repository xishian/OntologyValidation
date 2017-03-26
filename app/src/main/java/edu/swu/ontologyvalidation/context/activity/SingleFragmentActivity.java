package edu.swu.ontologyvalidation.context.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import edu.swu.ontologyvalidation.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

	private Fragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();

		if(mFragment==null)
			mFragment = createFragment();

		if(transaction!=null&mFragment!=null)
			transaction.add(R.id.activity_fragment,mFragment).commit();



	}

	protected abstract Fragment createFragment();
}
