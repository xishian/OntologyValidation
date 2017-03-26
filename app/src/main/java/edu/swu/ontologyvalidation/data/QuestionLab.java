package edu.swu.ontologyvalidation.data;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.swu.ontologyvalidation.entity.Question;
import edu.swu.ontologyvalidation.util.GetDataFromFile;

/**
 * Created by Tuister on 2016/9/5.
 */
public  class QuestionLab {
    private static final String TAG = "QuestionLab";


    /*
    * URL sample:
    * http://localhost:8080/ontologyvalidation/answer/1001
    * http://localhost:8080/ontologyvalidation/question/all
    *http://localhost:8080/ontologyvalidation/question/1001
    * */
    private static final String SERVER_IP = "52.192.206.211";
    public static final String URL_ALL_QUESTION ="http://"+SERVER_IP+":8080/ontologyvalidation/question/all";
    public static final String URL_ONE_QUESTION ="http://"+SERVER_IP+":8080/ontologyvalidation/question/";
    public static final String URL_ONE_ANSWER ="http://"+SERVER_IP+":8080/ontologyvalidation/answer/";

    private static List<Question> mQuestions;
	private static Map<Integer ,String> mAnswers = new HashMap<>();
    private static Map<Integer, Question> mQuestionMap  = new HashMap<>();
    private static Context mContext;

    public static QuestionLab sQuestionLab;
	private static OkHttpClient okHttpClient = new OkHttpClient();

    private QuestionLab(){

        mQuestionMap = new HashMap<>();
    }

    public QuestionLab getInstance(){
        if(sQuestionLab==null)
            sQuestionLab = new QuestionLab();
        return sQuestionLab;
    }

    //应用启动时调用此方法从网络获取数据
    public static void loadQuestions(){
        Log.i(TAG,"start load questions from network");

        Request request = new Request.Builder().url(URL_ALL_QUESTION).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
				String result = new String(response.body().bytes(),"GBK");
                Gson gson = new Gson();
                mQuestions = gson.fromJson(result,new TypeToken<ArrayList<Question>>(){}.getType());
                if(mQuestions!=null&&mQuestions.size()>0){
                    Log.i(TAG,"received "+mQuestions.size()+" questions");
                    for(Question q:mQuestions){
                        mQuestionMap.put(q.getId(),q);
                    }
                }
            }
        });

    }

	public static String  getAnswer(int id) {
			return mAnswers.get(id);
	}

	public static String getAnswerFromNetwork(int id){
		Log.i(TAG,"get answer for "+id);
		String result = null;
		Request request = new Request.Builder().url(URL_ONE_ANSWER+id).build();

		Call call = okHttpClient.newCall(request);

		try {
			Response response = call.execute();
			result = new String(response.body().bytes(),"GBK");
			mAnswers.put(id,result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

    public static List<Question> getQuestions(){
        return mQuestions;
    }

    public static List<Question> getQuestionsFromFile(Context mContext){

        if(mQuestions==null){
            GetDataFromFile data = new GetDataFromFile(mContext);
            mQuestions = data.getQuestionList();
        }

        return mQuestions;
    }

    public static void setQuestions(List<Question> questions) {
        mQuestions = questions;
		if(mQuestionMap==null)
			mQuestionMap = new HashMap<>();
        for(Question q:mQuestions){
            mQuestionMap.put(q.getId(),q);
        }
    }

    public static Question getQuestion(int questionId){
        if(mQuestionMap==null){
            loadQuestions();
        }
        return mQuestionMap.get(questionId);
    }



}
