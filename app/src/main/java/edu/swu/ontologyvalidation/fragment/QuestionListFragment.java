package edu.swu.ontologyvalidation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.swu.ontologyvalidation.R;
import edu.swu.ontologyvalidation.adapter.QuestionListAdapter;
import edu.swu.ontologyvalidation.context.activity.QuestionPagerActivity;
import edu.swu.ontologyvalidation.data.QuestionLab;
import edu.swu.ontologyvalidation.entity.Question;

public class QuestionListFragment extends Fragment implements QuestionListAdapter.OnItemClickListener {
	private static final String TAG="QuestionListFragment";
	private List<Question> mQuestions;
	private QuestionListAdapter mAdapter;
	
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
//    private EditText mEditSearch;
//	private ImageButton mBtnSearch;


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
    public View onCreateView(final LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question_list, container, false);
		mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
		mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_question_list);

        //设置RecyclerView
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
		mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setHasFixedSize(false); // set variable size
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setLayoutManager(mLayoutManager);
		if(QuestionLab.getQuestions()!=null)
		{
			mQuestions = QuestionLab.getQuestions();
			mAdapter = new QuestionListAdapter(getActivity(),mQuestions);
			mAdapter.setOnItemClickListener(this);
			mRecyclerView.setAdapter(mAdapter);
		}else {
			//应用初始化时要加载一次问题列表
			new QuestionListTask(getActivity()).execute();
		}
		//设置SwipeRefreshLayout
		mSwipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_dark,
				android.R.color.holo_blue_light,
				android.R.color.holo_green_dark,
				android.R.color.holo_green_light
		);
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				//从网络获取数据
				new QuestionListTask(getActivity()).execute();
			}
		});
        return rootView;
    }

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_question_list,menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId())
		{
			case R.id.menu_item_sync:
				//执行一些同步的操作，将本地数据库里的内容（目前还是内存里的mQuestions对象）同步到远程服务器
				Toast.makeText(getActivity(), R.string.start_sync, Toast.LENGTH_SHORT).show();
				break;
		}
		return true;
	}

	@Override
	public void onClick(View view, int position) {
		Intent intent = new Intent(getActivity(), QuestionPagerActivity.class);
		intent.putExtra(QuestionPagerActivity.EXTRA_QUESTION_LOCATION, position);
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG,"onResume: dataSet changed");
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLongClick(View view, int position) {

	}

	class QuestionListTask extends AsyncTask<Void,Integer ,List<Question>>{

		private List<Question> questions = new ArrayList<>();

		public QuestionListTask(Context context){

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mSwipeRefreshLayout.setRefreshing(true);
		}

		@Override
		protected void onPostExecute(List<Question> questions) {
			super.onPostExecute(questions);
			//要从网络获取数据，暂时为了测试先用文件代替
			mQuestions = questions;
			Log.i(TAG,"onPostExecute questions size :"+questions.size());
			mAdapter = new QuestionListAdapter(getActivity(), mQuestions);
			mAdapter.setOnItemClickListener(QuestionListFragment.this);
			mRecyclerView.setAdapter(mAdapter);


			mSwipeRefreshLayout.setRefreshing(false);
		}

		@Override
		protected List<Question> doInBackground(Void... params) {

			OkHttpClient okHttpClient = new OkHttpClient();
			Request request = new Request.Builder().url(QuestionLab.URL_ALL_QUESTION).build();

			Call call = okHttpClient.newCall(request);
			try {
				//执行execute时会阻塞
				Response response = call.execute();
				String result = new String(response.body().bytes(),"GBK");
				Log.i(TAG,"Received: "+result);
				Gson gson = new Gson();
				questions= gson.fromJson(result,new TypeToken<ArrayList<Question>>(){}.getType());
				if(questions!=null&&questions.size()>0)
				{
					QuestionLab.setQuestions(questions);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return questions;
		}
	}

}
