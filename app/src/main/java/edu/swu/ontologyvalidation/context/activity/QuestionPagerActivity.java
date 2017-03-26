package edu.swu.ontologyvalidation.context.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import edu.swu.ontologyvalidation.R;
import edu.swu.ontologyvalidation.data.QuestionLab;
import edu.swu.ontologyvalidation.entity.Question;
import edu.swu.ontologyvalidation.fragment.QuestionFragment;

public class QuestionPagerActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG  = "QuestionPagerActivity";
    public static final String EXTRA_QUESTION_ID="QuestionPagerActivity.EXTRA_QUESTION_ID";
	public static final String EXTRA_QUESTION_LOCATION="QuestionPagerActivity.EXTRA_QUESTION_LOCATION";

    private ViewPager mViewPager;
	private Button mTrueButton,mFalseButton,mNotSureButton;

    private FragmentManager mFragmentManager;
	private ActionBar mActionBar;

	private List<Question> mQuestions;
	private int nowQuestionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_pager);
        mQuestions = QuestionLab.getQuestions();
        mViewPager = (ViewPager) findViewById(R.id.vp_check);
		mTrueButton = (Button)findViewById(R.id.btn_yes);
		mTrueButton.setOnClickListener(this);
		mFalseButton = (Button)findViewById(R.id.btn_no);
		mFalseButton.setOnClickListener(this);
		mNotSureButton = (Button)findViewById(R.id.btn_not_sure);
		mNotSureButton.setOnClickListener(this);

        mFragmentManager = getSupportFragmentManager();
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("QuestionPagerActivity");

        mViewPager.setAdapter(new FragmentStatePagerAdapter(mFragmentManager) {
            @Override
            public Fragment getItem(int position) {
                int id = mQuestions.get(position).getId();
                return QuestionFragment.newInstance(id);
            }

            @Override
            public int getCount() {
				return mQuestions.size();
			}
        });

		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				nowQuestionId = mQuestions.get(position).getId();
				int matched  = mQuestions.get(position).getMatched();
				String result = matched==0?"False":(matched==1?"True":"Not Sure");
				mActionBar.setTitle("Question "+nowQuestionId+" - "+result);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

        int position = getIntent().getIntExtra(EXTRA_QUESTION_LOCATION,0);
		nowQuestionId = mQuestions.get(position).getId();
		Log.i(TAG,"The question id is "+nowQuestionId);
		mViewPager.setCurrentItem(position);
//		//跳转到指定的Question界面
//		for(int i = 0;i<mQuestions.size();i++){
//            if(mQuestions.get(i).getId()==nowQuestionId){
//                mViewPager.setCurrentItem(i);
//                break;
//            }
//        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_question_pager,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Resources r = getResources();
		switch (item.getItemId())
		{
			case R.id.menu_item_more:
				MaterialDialog dialog = new MaterialDialog.Builder(this).title(r.getString(R.string.materials_about_question))
						.content(QuestionLab.getQuestion(nowQuestionId).getAnswer())
						.positiveText("OK")
						.positiveColorRes(R.color.fabRed)
						.titleColorRes(R.color.default_text_color_black)
						.build();
				dialog.show();
				break;

		}
		return true;
	}

	@Override
	public void onClick(View v) {
		QuestionLab.getQuestion(nowQuestionId).setChecked(true);
		switch (v.getId())
		{
			case R.id.btn_yes:
				QuestionLab.getQuestion(nowQuestionId).setMatched(1);
				mActionBar.setTitle("Question "+nowQuestionId+" - True");
				break;
			case R.id.btn_not_sure:
				QuestionLab.getQuestion(nowQuestionId).setMatched(2);
				mActionBar.setTitle("Question "+nowQuestionId+" - Not Sure");
				break;
			case R.id.btn_no:
				QuestionLab.getQuestion(nowQuestionId).setMatched(0);
				mActionBar.setTitle("Question "+nowQuestionId+" - False");
				break;
		}
	}
}
