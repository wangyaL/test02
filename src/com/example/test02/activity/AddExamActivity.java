package com.example.test02.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.test02.R;

public class AddExamActivity extends Activity {
	private Button bplay,bpause,bstop;
	private MediaPlayer mp = new MediaPlayer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_exam);
		
		bplay = (Button) findViewById(R.id.play);
		bpause = (Button) findViewById(R.id.pause);
		bstop = (Button) findViewById(R.id.stop);
		
		mediaInit();
		
		bplay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mp != null){
//					try {
//						mp.prepare();
//					} catch (IllegalStateException e) {
//						e.printStackTrace();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
					mp.start();
				}
			}
		});
		
		bpause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mp != null){
					mp.pause();
				}
			}
		});
		
		bstop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mp != null){
//					mp.stop();
					//暂停后，进度设置为0
					mp.pause();
					mp.seekTo(0);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_exam, menu);
		return true;
	}

	public void mediaInit(){
		mp = MediaPlayer.create(this, R.raw.father);
		mp.setLooping(true);
//		try {
//			AssetManager assetManager = getApplicationContext().getAssets();
//			AssetFileDescriptor fileDescriptor = assetManager.openFd("father1.mp3");
//			mp.setDataSource(fileDescriptor.getFileDescriptor(),
//                    fileDescriptor.getStartOffset(),
//                    fileDescriptor.getLength());
////			mp.prepare();
////			mp.start();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		mp.setOnCompletionListener(new OnCompletionListener() {//一首结束
//			@Override
//			public void onCompletion(MediaPlayer mp) {
//				mp.release();
//			}
//		});
	}
}
