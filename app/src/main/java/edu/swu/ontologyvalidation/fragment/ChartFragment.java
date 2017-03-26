package edu.swu.ontologyvalidation.fragment;


import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import edu.swu.ontologyvalidation.R;
import edu.swu.ontologyvalidation.data.QuestionLab;
import edu.swu.ontologyvalidation.util.QuestionsCount;

public class ChartFragment extends Fragment implements OnChartValueSelectedListener {
	private static final String ARGS_COUNT_DATA = "ChartFragment.ARGS_COUNT_DATA";
	private QuestionsCount mCount;

	private PieChart mPieChart;

	public ChartFragment() {}

	public ChartFragment getInstance(QuestionsCount count)	{
		ChartFragment fragment = new ChartFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(ARGS_COUNT_DATA,count);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getArguments()!=null)
			mCount = (QuestionsCount)getArguments().get(ARGS_COUNT_DATA);
		else
			mCount = new QuestionsCount(QuestionLab.getQuestions());
	}

	@Override
	public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_chart,container,false);
		mPieChart = (PieChart)v.findViewById(R.id.pc_chart);
		configurePieChart();
		initPieData();

		return v;
	}
	
	private void configurePieChart(){
		Resources r = getResources();


		mPieChart.setUsePercentValues(true);
		mPieChart.getDescription().setEnabled(false);
		mPieChart.setExtraOffsets(5, 10, 5, 5);

		mPieChart.setDragDecelerationFrictionCoef(0.95f);

		mPieChart.setTransparentCircleRadius(50f);
		mPieChart.setCenterTextSize(24);
		mPieChart.setCenterTextTypeface(Typeface.SANS_SERIF);

		mPieChart.setCenterText(r.getString(R.string.app_name));

		mPieChart.setDrawHoleEnabled(true);
		mPieChart.setHoleColor(Color.WHITE);

		mPieChart.setTransparentCircleColor(Color.WHITE);
		mPieChart.setTransparentCircleAlpha(110);

		mPieChart.setHoleRadius(58f);
		mPieChart.setTransparentCircleRadius(61f);

		mPieChart.setDrawCenterText(true);

		mPieChart.setRotationAngle(0);
		// enable rotation of the chart by touch
		mPieChart.setRotationEnabled(true);
		mPieChart.setHighlightPerTapEnabled(true);

		mPieChart.setOnChartValueSelectedListener(this);


		mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

		Legend l = mPieChart.getLegend();
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
		l.setOrientation(Legend.LegendOrientation.VERTICAL);
		l.setDrawInside(false);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(0f);
		l.setYOffset(0f);

		// entry label styling
		mPieChart.setEntryLabelColor(Color.WHITE);
		mPieChart.setEntryLabelTextSize(12f);
	}

	private void initPieData(){
		Resources r = getResources();

		List<PieEntry> pieEntries = new ArrayList<>();

//		pieEntries.add(new PieEntry(mCount.getTrue(),r.getString(R.string.true_label)));
//		pieEntries.add(new PieEntry(mCount.getFalse(),r.getString(R.string.false_label)));
//		pieEntries.add(new PieEntry(mCount.getNotSure(),r.getString(R.string.not_sure_label)));
//		pieEntries.add(new PieEntry(mCount.getNotChecked(),r.getString(R.string.not_checked_label)));

		pieEntries.add(new PieEntry(12f,r.getString(R.string.true_label)));
		pieEntries.add(new PieEntry(33f,r.getString(R.string.false_label)));
		pieEntries.add(new PieEntry(25f,r.getString(R.string.not_sure_label)));
		pieEntries.add(new PieEntry(30f,r.getString(R.string.not_checked_label)));


		PieDataSet dataSet = new PieDataSet(pieEntries,"");
		dataSet.setSliceSpace(1.0f);
		ArrayList<Integer> colors = new ArrayList<>();

		for (int c : ColorTemplate.MATERIAL_COLORS)
			colors.add(c);

		colors.add(ColorTemplate.getHoloBlue());

		dataSet.setColors(colors);
		//dataSet.setSelectionShift(0f);

		PieData data = new PieData(dataSet);
		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(11f);
		data.setValueTextColor(Color.WHITE);

		// undo all highlights
		mPieChart.highlightValues(null);
		mPieChart.setData(data);
		mPieChart.invalidate();
	}


	@Override
	public void onValueSelected(Entry e, Highlight h) {

	}

	@Override
	public void onNothingSelected() {

	}
}
