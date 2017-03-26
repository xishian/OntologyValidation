package edu.swu.ontologyvalidation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.swu.ontologyvalidation.R;
import edu.swu.ontologyvalidation.adapter.CountListAdapter;
import edu.swu.ontologyvalidation.context.activity.ChartActivity;
import edu.swu.ontologyvalidation.data.QuestionLab;
import edu.swu.ontologyvalidation.entity.CountItem;
import edu.swu.ontologyvalidation.util.QuestionsCount;

/**
 * Created by Tuister on 2016/8/16.
 */
public class StatisticsFragment extends Fragment {

	private static final  String TAG = "StatisticsFragment";
	private static final String TAG_FRAGMENT_CHART_FRAGMENT = "TAG_FRAGMENT_CHART_FRAGMENT";

    private ListView mLvCount;
//    private PieChart mPieChart;
	private QuestionsCount mCount;
	private List<CountItem> mListCount;
	private Button mBtnShowGraph;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mCount = new QuestionsCount(QuestionLab.getQuestions());
		mListCount = new ArrayList<>();
		mListCount.add(new CountItem("Total:",mCount.getTotal()));
		mListCount.add(new CountItem("Not Checked:",mCount.getNotChecked()));
		mListCount.add(new CountItem("True:",mCount.getTrue()));
		mListCount.add(new CountItem("False:",mCount.getFalse()));
		mListCount.add(new CountItem("Not Sure:",mCount.getNotSure()));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistics,container,false);
		mLvCount = (ListView)rootView.findViewById(R.id.lv_count);
        CountListAdapter adapter = new CountListAdapter(getActivity(), mListCount);
        mLvCount.setAdapter(adapter);


		mBtnShowGraph = (Button)rootView.findViewById(R.id.btn_fragment_statistics_show_graph);
		mBtnShowGraph.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG,"show pie chart");
				startActivity(new Intent(getActivity(), ChartActivity.class));
			}
		});
		return rootView;
    }


}
