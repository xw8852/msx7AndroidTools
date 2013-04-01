package com.example.listviewtemplate;

import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.msx7.widget.RegularPolygonView;

public class TestActivity extends Activity {
	RegularPolygonView mPolygonView;
	EditText mEditText;
	Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.test);
//		getWindow().getDecorView().invalidate();
//		mPolygonView = (RegularPolygonView) findViewById(R.id.regularPolygonView1);
//		mButton = (Button) findViewById(R.id.button1);
//		mEditText = (EditText) findViewById(R.id.editText1);
//		mButton.setOnClickListener(mClickListener);
//		System.out.println("0-getVertexs:"+mPolygonView.getVertexs());
//		RelativeLayout mLayout;
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("1-getVertexs:"+mPolygonView.getVertexs());

	}


	View.OnClickListener mClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			String text = mEditText.getText().toString().trim();
			if (Pattern.matches("[0-9]+", text)) {
				int border = Integer.parseInt(text);
				mPolygonView.setBorders(border);
			}
		}
	};
}
