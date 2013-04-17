package com.example.simpletest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartPageActivity extends SimpleTestActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_page);

		Button btnCompass = (Button) findViewById(R.id.btnCompass);
		btnCompass.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Toast.makeText(StartPageActivity.this,
				// "TODO: Launch Compass Activity", Toast.LENGTH_LONG).show();
				startActivity(new Intent(StartPageActivity.this,
						CompassActivity.class));
			}
		});

		Button btnAudio = (Button) findViewById(R.id.btnAudio);
		btnAudio.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Toast.makeText(StartPageActivity.this,
				// "TODO: Launch Audio Activity", Toast.LENGTH_LONG).show();
				startActivity(new Intent(StartPageActivity.this,
						MediaActivity.class));
			}
		});

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.start_page, menu);
	// return true;
	// }

}
