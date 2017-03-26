package edu.swu.ontologyvalidation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import edu.swu.ontologyvalidation.R;
import edu.swu.ontologyvalidation.entity.Question;

/**
 * Created by Tuister on 2016/8/16.
 */
public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder> {
	private static final String TAG = "QuestionListAdapter";
    private Context mContext;
    private List<Question> mQuestions;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;


	//定义item的监听器
    public interface OnItemClickListener {
        void onClick(View view, int position);
        void onLongClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
		mOnItemClickListener = listener;
    }

    public QuestionListAdapter(Context mContext, List<Question> mQuestions) {
        this.mContext = mContext;
        this.mQuestions = mQuestions;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.question_list_item, parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Question question = mQuestions.get(position);
        holder.tvBody.setText(question.getText_zh());//body就是text
        holder.tvInfo.setText(question.getAnswer());
        if (question.isChecked())
            holder.cbCheck.setChecked(true);
        else
            holder.cbCheck.setChecked(false);

    }

    @Override
	public int getItemCount() {
		return mQuestions.size();
	}

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvBody;
        public TextView tvInfo;
        public CheckBox cbCheck;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
			tvBody = (TextView)itemView.findViewById(R.id.tv_body);
			tvBody.setEnabled(false);
			tvInfo = (TextView)itemView.findViewById(R.id.tv_info);
			tvInfo.setEnabled(false);
			cbCheck  = (CheckBox)itemView.findViewById(R.id.cb_check);
			cbCheck.setEnabled(false);
        }

        @Override
        public void onClick(View view) {
                Log.i(TAG, "Item "+getAdapterPosition()+" clicked.");
                mOnItemClickListener.onClick(view, getAdapterPosition());
		}
	}

}


