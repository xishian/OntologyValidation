package edu.swu.ontologyvalidation.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.swu.ontologyvalidation.entity.Question;

/**
 * Created by Tuister on 2016/8/16.
 */
public class GetDataFromFile {
    private Context context;

    public GetDataFromFile(Context context){
        this.context = context;
    }

    public List<Question> getQuestionList() {
        List<Question> questions = new ArrayList<Question>();
//        A/
        StringBuilder builder = new StringBuilder("");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("question_temp.json"), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
//            Gson gson = new Gson();
//            questions = gson.fromJson(builder.toString(), new TypeToken<List<Question>>() {
//            }.getType());
            Log.i("Tag", "the question size is " + questions.size());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return questions;
    }

}
