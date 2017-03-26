package edu.swu.ontologyvalidation.entity;

import java.io.Serializable;

/**
 * Created by Tuister on 2017/3/4.
 */

public class User implements Serializable{
	private String mUsername;
	private String mSign;
	private byte[] mHeadIcon;

	public User(){}


	public User(String mUsername, String mSign) {
		this.mUsername = mUsername;
		this.mSign = mSign;
	}

	public String getmUsername() {
		return mUsername;
	}

	public void setmUsername(String mUsername) {
		this.mUsername = mUsername;
	}

	public String getmSign() {
		return mSign;
	}

	public void setmSign(String mSign) {
		this.mSign = mSign;
	}

	public byte[] getmHeadIcon() {
		return mHeadIcon;
	}

	public void setmHeadIcon(byte[] mHeadIcon) {
		this.mHeadIcon = mHeadIcon;
	}
}
