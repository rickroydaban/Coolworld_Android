package com.example.coolworld.threads;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

public class ImageDownloader implements Runnable{
	ImageView image;
	String imageUrl;
	ProgressDialog pd;
	private Bitmap bitmap;
	
	public ImageDownloader(Context context, ImageView image, String imageUrl){
		this.image = image;
		this.imageUrl = imageUrl;
		pd = new ProgressDialog(context);
		pd.setMessage("Downloading Images. Please Wait.");
		pd.show();
	}
	
	@Override
	public void run(){
		try {
			bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Handler(Looper.getMainLooper()).post(new Runnable() {				
	  	@Override
	  	public void run() {
				image.setImageBitmap(bitmap);
	  		pd.dismiss();
	  	}
  	});			
	}
}
