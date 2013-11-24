package com.example.coolworld.listNodes;

import com.example.coolworld.ListNode;
import android.graphics.Bitmap;

public class SimpleListNode implements ListNode{
	private String localName;
	private Bitmap image;

	//friendly constructor
	public SimpleListNode(){
	}
	
	@Override
	public void setImage(Bitmap image) {
		this.image = image;
	}

	@Override
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	
	@Override
	public String getLocalName(){
		return localName;
	}
	
	@Override
	public Bitmap getImageBitmap(){
		return image;
	}		
}
