package edu.swu.ontologyvalidation.context.activity;

import android.support.v4.app.Fragment;

import edu.swu.ontologyvalidation.fragment.ChartFragment;

public class ChartActivity extends SingleFragmentActivity {


	@Override
	protected Fragment createFragment() {
		return new ChartFragment();
	}
}
