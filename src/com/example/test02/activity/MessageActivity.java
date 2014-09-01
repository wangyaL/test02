package com.example.test02.activity;

import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import com.example.test02.R;

public class MessageActivity extends Activity {
	SoundPool soundPool;	//SoundPllo对象引用
	HashMap<Integer, Integer> soundPoolMap;	//声音的管理容器
	boolean SOUND_PLAY = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSounds();	//初始化声音资源
		setContentView(R.layout.activity_message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message, menu);
		return true;
	}

	@SuppressLint("UseSparseArrays")
	public void initSounds() {
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new HashMap<Integer, Integer>();
		
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				if(status != 0){
					Log.e("SOUND LOAD", "Sound ID:" + sampleId + " Failed to load.");
				}else{
					Log.i("SOUND LOAD", "Sound ID:" +sampleId + " loaded.");
					playSound(1, 5);	//播放5次
				}
				
			}
		});
		
		soundPoolMap.put(1, soundPool.load(this, R.raw.father, 1));	//加载声音
	}
	/**
	 * 播放声音的方法
	 * @param sound
	 * @param loop
	 */
	public void playSound(int sound, int loop){
		AudioManager mgr = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		//音乐音量
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;	//得到声音的大小

		soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1.0f);
	}
}
