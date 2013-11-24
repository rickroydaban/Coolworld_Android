package com.example.coolworld;

import android.graphics.Bitmap;

public interface ListNode {

	public void setImage(Bitmap image);
	public void setLocalName(String localName);
	public Bitmap getImageBitmap();
	public String getLocalName();
}
