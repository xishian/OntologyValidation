package edu.swu.ontologyvalidation.util;

import java.io.Serializable;
import java.util.List;

import edu.swu.ontologyvalidation.entity.Question;

/**
 * Created by Tuister on 2017/3/2.
 */

public class QuestionsCount implements Serializable {
	private List<Question> mQuestions;
	private int mTrue;
	private int mFalse;
	private int mNotSure;
	private int mNotChecked;
	private int mTotal;


	public QuestionsCount(List<Question> questions)
	{
		this.mQuestions = questions;
		count();
	}

	public void count(){
		mTotal = mQuestions.size();
		int qTrue = 0,qFalse=0,qNotSure=0,qNotChecked=0;

		for(Question q:mQuestions)
		{
			if(!q.isChecked())
				qNotChecked++;
			else if(q.getMatched()==0)
				qFalse++;
			else if(q.getMatched()==1)
				qTrue++;
			else
				qNotSure++;
		}
		mTrue = qTrue;
		mFalse = qFalse;
		mNotChecked = qNotChecked;
		mNotSure = qNotSure;
	}

	public int getTrue() {
		return mTrue;
	}

	public void setTrue(int mTrue) {
		this.mTrue = mTrue;
	}

	public int getFalse() {
		return mFalse;
	}

	public void setFalse(int mFalse) {
		this.mFalse = mFalse;
	}

	public int getNotSure() {
		return mNotSure;
	}

	public void setNotSure(int mNotSure) {
		this.mNotSure = mNotSure;
	}

	public int getNotChecked() {
		return mNotChecked;
	}

	public void setNotChecked(int mNotChecked) {
		this.mNotChecked = mNotChecked;
	}

	public int getTotal() {
		return mTotal;
	}

	public void setTotal(int mTotal) {
		this.mTotal = mTotal;
	}

	@Override
	public String toString() {
		return "QuestionsCount{" +
				"mTrue=" + mTrue +
				", mFalse=" + mFalse +
				", mNotSure=" + mNotSure +
				", mNotChecked=" + mNotChecked +
				", mTotal=" + mTotal +
				'}';
	}
}
