package com.example.listviewtemplate;

import java.util.regex.Pattern;

import com.msx7.widget.RegularPolygonView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TestActivity extends Activity {
	RegularPolygonView mPolygonView;
	EditText mEditText;
	Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		mPolygonView = (RegularPolygonView) findViewById(R.id.regularPolygonView1);
		mButton = (Button) findViewById(R.id.button1);
		mEditText = (EditText) findViewById(R.id.editText1);
		mButton.setOnClickListener(mClickListener);
		ViewGroup mgroup;
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
