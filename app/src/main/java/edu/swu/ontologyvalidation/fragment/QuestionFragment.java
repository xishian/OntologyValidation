package edu.swu.ontologyvalidation.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import edu.swu.ontologyvalidation.R;
import edu.swu.ontologyvalidation.data.QuestionLab;
import edu.swu.ontologyvalidation.entity.Question;


public class QuestionFragment extends Fragment{
	private static final String TAG="QuestionFragment";
	private static final String BUNDLE_QUESTION_ID = "QuestionFragment.BUNDLE_QUESTION_ID";

    private TextView mTvText;  //问题主体
    private SwipeRefreshLayout mRefreshResult;
    private ListView mLvResult;

    private Question mQuestion;




    public static QuestionFragment newInstance(int id) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_QUESTION_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

        if (getArguments() != null) {
            int id = getArguments().getInt(BUNDLE_QUESTION_ID);
            mQuestion = QuestionLab.getQuestion(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_check,container,false);
		mRefreshResult = (SwipeRefreshLayout)rootView.findViewById(R.id.refresh_result);
		mLvResult = (ListView) rootView.findViewById(R.id.lv_result);
        mTvText = (TextView)rootView.findViewById(R.id.tv_question_text);

		mTvText.setText(mQuestion.getText_zh());
		mLvResult.setEmptyView(inflater.inflate(R.layout.empty_view_answer_list,container,false));
		mRefreshResult.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new FetchAnswerTask().execute(mQuestion.getId());
			}
		});

		if(QuestionLab.getAnswer(mQuestion.getId())!=null)
		{
			try {
				JSONArray array = new JSONArray(QuestionLab.getAnswer(mQuestion.getId()));
				List<String> answerList = new ArrayList<>();
				for(int i = 0;i<array.length();i++)
				{
					answerList.add(array.get(i).toString());
				}
				ArrayAdapter<String> adapter  = new ArrayAdapter<>(
						getActivity(),android.R.layout.simple_list_item_1, answerList);

				mLvResult.setAdapter(adapter);
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e(TAG,"convert to json array failed");
			}
		}else
			new FetchAnswerTask().execute(mQuestion.getId());

        return rootView;
    }

	class FetchAnswerTask extends AsyncTask<Integer,Void,String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mRefreshResult.setRefreshing(true);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			try {
				JSONArray array = new JSONArray(result);
				List<String> answerList = new ArrayList<>();
				for(int i = 0;i<array.length();i++)
				{
					answerList.add(array.get(i).toString());
				}
				ArrayAdapter<String> adapter  = new ArrayAdapter<>(
						getActivity(),android.R.layout.simple_list_item_1, answerList);

				mLvResult.setAdapter(adapter);
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e(TAG,"convert to json array failed");
			}

			mRefreshResult.setRefreshing(false);
		}

		@Override
		protected String doInBackground(Integer... params) {

			return QuestionLab.getAnswerFromNetwork(params[0]);
		}


	}
}
