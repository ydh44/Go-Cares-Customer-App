package com.caregiver.gocares.viewmodels;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.caregiver.gocares.views.fragment.PembayaranFragment;

public class PembayaranViewModel extends ViewModel {
	public ObservableField<String> jumlahBayar = new ObservableField<>();
	PembayaranFragment view;

	public PembayaranViewModel(PembayaranFragment pembayaranFragment) {
		this.view = pembayaranFragment;
		Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				jumlahBayar.set(view.jumlahBayar);
			}
		};handler.postDelayed(runnable, 100);
	}

	public void ambilFoto(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (view.requireActivity().checkSelfPermission(Manifest.permission.CAMERA) ==
							PackageManager.PERMISSION_DENIED ) {
				//permission not enabled , request it
				String[] permission = new String[]{Manifest.permission.CAMERA};
				//show popup to request permission
				view.requireActivity().requestPermissions(permission, view.PERMISIION_CODE);
				view.pil = true;

			} else {
				//permission already granted
				view.openCamera();

			}
		} else {
			//system os < marshmallow
			view.openCamera();
		}
	}

	public void  ambilGaleri(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (view.requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
							PackageManager.PERMISSION_DENIED) {
				//permission not granted, request it
				String[] permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
				//show popup for runtime permission
				view.requireActivity().requestPermissions(permission, view.PERMISIION_CODE);
				view.pil = false;
			}
			else {
				//permission already granted
				view.pickImageFromGallery();
			}
		}
		else {
			//system os is less then marshmallow
			view.pickImageFromGallery();

		}

	}

	public void salin(){
		ClipboardManager clipboardManager = (ClipboardManager) view.requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clipData = ClipData.newPlainText("rekening", view.binding.text4.getText());
		clipboardManager.setPrimaryClip(clipData);
		Toast.makeText(view.getActivity(), "Rekening Berhasil Disalin", Toast.LENGTH_SHORT ).show();
		Vibrator vibrator = (Vibrator) view.requireActivity().getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(80);
	}

	public void home(){
		view.requireActivity().finish();
	}

}
