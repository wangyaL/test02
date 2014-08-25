package com.example.test02.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.test02.R;

public class HomeMenuActivity extends TabActivity {
	private TabHost tabHost;  
	private TextView main_tab_new_message, main_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除标题 
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_home_menu);
		
		main_title = (TextView) findViewById(R.id.main_title);
		
//		main_tab_new_message=(TextView) findViewById(R.id.main_tab_new_message);  
//        main_tab_new_message.setVisibility(View.VISIBLE);  
//        main_tab_new_message.setText("10");  
          
        tabHost=this.getTabHost(); 
        TabHost.TabSpec spec;  
        Intent intent;  
  
        intent=new Intent().setClass(this, AddExamActivity.class);  
        spec=tabHost.newTabSpec("添加考试").setIndicator("添加考试").setContent(intent);  
        tabHost.addTab(spec);  
          
        intent=new Intent().setClass(this,MyExamActivity.class);  
        spec=tabHost.newTabSpec("我的考试").setIndicator("我的考试").setContent(intent);  
        tabHost.addTab(spec);  
          
        intent=new Intent().setClass(this, MessageActivity.class);  
        spec=tabHost.newTabSpec("我的通知").setIndicator("我的通知").setContent(intent);  
        tabHost.addTab(spec);  
          
       
        intent=new Intent().setClass(this, SettingsActivity.class);  
        spec=tabHost.newTabSpec("设置").setIndicator("设置").setContent(intent);  
        tabHost.addTab(spec);  
        
        //设置默认选择项
        tabHost.setCurrentTab(2);
        main_title.setText("我的通知");
          
        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);  
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
              
            @Override  
            public void onCheckedChanged(RadioGroup group, int checkedId) {  
                switch (checkedId) {  
                case R.id.main_tab_addExam://添加考试  
                    tabHost.setCurrentTabByTag("添加考试");
                    main_title.setText("添加考试");
                    break;  
                case R.id.main_tab_myExam://我的考试  
                    tabHost.setCurrentTabByTag("我的考试");
                    main_title.setText("我的考试");
                    break;  
                case R.id.main_tab_message://我的通知  
                    tabHost.setCurrentTabByTag("我的通知");
                    main_title.setText("我的通知");
                    break;  
                case R.id.main_tab_settings://设置  
                    tabHost.setCurrentTabByTag("设置");
                    main_title.setText("设置");
                    break;  
                default:  
//                    tabHost.setCurrentTabByTag("我的考试");  
                    break;  
                }  
            }  
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_menu, menu);
		return true;
	}

}
