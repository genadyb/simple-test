package com.example.simpletest;

public class MediaObject {

	String mName;
	String mPath;
	
	public MediaObject(String name, String path) {
		mName = name;
		mPath = path;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//return super.toString();
		return mName;
	}

	/**
	 * @return the mName
	 */
	public String getmName() {
		return mName;
	}

	/**
	 * @param mName the mName to set
	 */
	public void setmName(String mName) {
		this.mName = mName;
	}

	/**
	 * @return the mPath
	 */
	public String getmPath() {
		return mPath;
	}

	/**
	 * @param mPath the mPath to set
	 */
	public void setmPath(String mPath) {
		this.mPath = mPath;
	}

}
