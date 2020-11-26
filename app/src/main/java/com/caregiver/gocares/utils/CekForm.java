package com.caregiver.gocares.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.caregiver.gocares.R;
import com.google.android.material.textfield.TextInputLayout;

public class CekForm{
	public CekForm(Context context, final EditText editText, final TextInputLayout textLayout, boolean icon){
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
				if(charSequence.length()<1)
				{
					textLayout.setErrorEnabled(true);
					textLayout.setError("Isi kolom di atas");
					if(icon){
						textLayout.setStartIconTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorRed)));
					}
				}

				else if (charSequence.length()>=1){
					textLayout.setErrorEnabled(false);
					if(icon){
						textLayout.setStartIconTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
					}
				}

			}

			@Override
			public void afterTextChanged(Editable editable) {
			}
		});
		editText.setOnFocusChangeListener((view, b) -> {
			if(icon){
				if(b){
					if(!textLayout.isErrorEnabled()){
						textLayout.setStartIconTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
					}else{
						textLayout.setStartIconTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorRed)));
					}
				}else if (!b) {
					if(!textLayout.isErrorEnabled()){
						textLayout.setStartIconTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.gray2)));
					}else{
						textLayout.setStartIconTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorRed)));
					}
				}
			}

		});
	}
}
