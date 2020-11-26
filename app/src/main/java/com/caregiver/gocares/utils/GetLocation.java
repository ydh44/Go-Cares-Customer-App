package com.caregiver.gocares.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class GetLocation {
	public MutableLiveData<String> kota;
	LocationManager locationManager;
	Activity activity;
	FusedLocationProviderClient fusedLocationProviderClient;


	public GetLocation(Activity activity) {
		this.activity = activity;
		kota = new MutableLiveData<>();
		locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

		fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);


		if (ContextCompat.checkSelfPermission(activity,
						Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
						&& ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);

			Handler hand = new Handler();
			Runnable run = new Runnable() {
				@Override
				public void run() {
					Log.d(TAG, "run: ");
					if (ContextCompat.checkSelfPermission(activity,
									Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
									&& ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
						location();
						hand.removeCallbacks(this);
					} else {
						hand.postDelayed(this, 100);
					}
				}
			};
			hand.postDelayed(run, 100);

		} else {
			location();
		}
	}


	private boolean checkLocationAvailable() {
		LocationManager m = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

		boolean gps = true;
		boolean network = true;
		boolean hasil = true;

		try {
			gps = m.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			network = m.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!gps && !network) {
			new AlertDialog.Builder(activity)
							.setTitle("Hidupkan Lokasi")
							.setCancelable(false)
							.setPositiveButton("Hidupkan", (dialogInterface, i) -> {
												activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
												Handler hands = new Handler();
												Runnable runs = new Runnable() {
													@Override
													public void run() {
														Log.d(TAG, "runs: ");
														if (m.isProviderEnabled(LocationManager.GPS_PROVIDER) && m.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
															location();
															hands.removeCallbacks(this);
														} else {
															hands.postDelayed(this, 100);
														}
													}
												};
												hands.postDelayed(runs, 100);
											}

							).setNegativeButton("Batal", null)
							.show();
			hasil = false;
		}

		return hasil;
	}

	@SuppressLint("MissingPermission")
	public void location() {
		if (checkLocationAvailable()) {
			Log.d(TAG, "location: ");
			LocationRequest locationRequest = new LocationRequest();
			locationRequest.setInterval(10000);
			locationRequest.setFastestInterval(3000);
			locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

			CancellationToken cancellationToken = new CancellationToken() {
				@Override
				public boolean isCancellationRequested() {
					return false;
				}

				@NonNull
				@Override
				public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
					return null;
				}
			};


			fusedLocationProviderClient.getCurrentLocation(100, cancellationToken).addOnCompleteListener(new OnCompleteListener<Location>() {
				@Override
				public void onComplete(@NonNull Task<Location> task) {
						Location location = task.getResult();
						if(location != null){
							Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
							List<Address> addresses = null;
							try {
								addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
								Log.d(TAG, "onComplete: " + addresses.get(0).getSubAdminArea());
								kota.postValue(addresses.get(0).getSubAdminArea());
							} catch (IOException e) {
								e.printStackTrace();
							}

						}
				}
			});
		}
	}
}
