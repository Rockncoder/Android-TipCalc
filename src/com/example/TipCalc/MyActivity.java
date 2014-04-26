package com.example.TipCalc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class MyActivity extends Activity implements LinearLayout.OnTouchListener, RatingBar.OnRatingBarChangeListener {
	private int currentRatings = 1;
	private double tipPercents[] = {0.0, 0.04, 0.08, 0.12, 0.16, 0.20};
	private RatingBar rating;
	private TextView billAmount;
	private TextView tipAmount;
	private TextView totalAmount;
	private LinearLayout layout;



	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// find all of our widgets
		rating = (RatingBar)findViewById(R.id.ratingBar);
		billAmount = (TextView)findViewById(R.id.billAmount);
		tipAmount = (TextView)findViewById(R.id.tipAmount);
		totalAmount = (TextView)findViewById(R.id.totalAmount);
		layout =(LinearLayout)findViewById(R.id.layout);

		// set all of the listeners
		layout.setOnTouchListener(this);
		rating.setOnRatingBarChangeListener(this);
		billAmount.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
				updateCalculation();
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});


	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
		currentRatings = (int)v;
		updateCalculation();
		hideKeyboard();

	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		hideKeyboard();
		return false;
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(billAmount.getWindowToken(), 0);
	}


	private void updateCalculation() {
		String billText = billAmount.getText().toString();
		double bill;

		try {
			bill = Double.parseDouble(billText);
		} catch (NumberFormatException ex) {
			bill = 0.0;
		}

		double tip = bill * tipPercents[currentRatings];
		double total = bill + tip;

		tipAmount.setText(String.format("%5.2f", tip));
		totalAmount.setText(String.format("%5.2f", total));
	}
}
