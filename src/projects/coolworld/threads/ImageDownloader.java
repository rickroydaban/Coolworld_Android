package projects.coolworld.threads;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

public class ImageDownloader implements Runnable{
	Activity activity;
	ImageView countryFlagView;
	String imageUrl;
	private Handler handler;
	private Bitmap bitmap;
	ProgressDialog progressDialog;
	
	public ImageDownloader(Activity a, ImageView pCountryFlag, String url, ProgressDialog p){
		activity = a;
		imageUrl = url;
		progressDialog = p;
		countryFlagView = pCountryFlag;
	  handler = new Handler(Looper.getMainLooper());
	  p.setMessage("Please Wait. Downloading Country Flags...");
	  p.show();
	}
	
	@Override
	public void run() {
    try {
      bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
  	  handler.post(new Runnable() {				
  	  	@Override
  	  	public void run() {
  	  		countryFlagView.setImageBitmap(bitmap);
  	  		progressDialog.hide();
  	  	}
  	  });
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }        
		
	}

}
