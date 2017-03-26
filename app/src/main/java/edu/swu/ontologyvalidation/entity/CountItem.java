package edu.swu.ontologyvalidation.entity;

/**
 * Created by Tuister on 2017/3/2.
 */

public class CountItem {
	private String mLabel;
	private int mCountInfo;

	public CountItem(String label,int count)
	{
		mLabel = label;
		mCountInfo = count;
	}

	public int getCountInfo() {
		return mCountInfo;
	}

	public void setCountInfo(int mCountInfo) {
		this.mCountInfo = mCountInfo;
	}

	public String getLabel() {
		return mLabel;
	}

	public void setLabel(String mLabel) {
		this.mLabel = mLabel;
	}
}
