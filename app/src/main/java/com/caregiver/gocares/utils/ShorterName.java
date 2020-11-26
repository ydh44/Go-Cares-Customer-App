package com.caregiver.gocares.utils;

import android.content.Intent;
import android.util.Log;

public class ShorterName {
	String name;
	public ShorterName(String name){
		this.name = name;
	}

	public String getShort(){
		String[] parsename = name.split(" ");
		Integer length = 10;
		String shortest = null;

		for(int i = 0; i < parsename.length; i++){
			Log.d("TAG", "getShort: " + parsename[i] + length);
			if(parsename[i].length() < length){
				length = parsename[i].length();
				shortest = parsename[i];
			}
		}

		return shortest;
	}

}
