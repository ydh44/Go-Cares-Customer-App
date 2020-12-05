package com.caregiver.gocares.views.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.caregiver.gocares.R;
import com.caregiver.gocares.utils.ParseData;
import com.caregiver.gocares.utils.SessionLog;
import com.caregiver.gocares.views.activity.MainActivity;
import com.caregiver.gocares.views.activity.SplashActivity;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment {

	@Override
	public View onCreateView(
					LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState
	) {
		View view = inflater.inflate(R.layout.profile_fragment, container, false);

		ParseData parseData = new ParseData();

		TextView nama= view.findViewById(R.id.nama);
		TextView email= view.findViewById(R.id.email);
		TextView umur = view.findViewById(R.id.umur);
		TextView gender = view.findViewById(R.id.gender);
		TextView telepon = view.findViewById(R.id.telepon);
		TextView alamat = view.findViewById(R.id.alamat);

		Log.d(TAG, "onCreateView: "+ SessionLog.GetUserAge(getActivity()));
		nama.setText(SessionLog.GetUserName(getActivity()));
		email.setText(SessionLog.GetUserEmail(getActivity()));
		umur.setText(parseData.parseUmur(SessionLog.GetUserAge(getActivity())));
		gender.setText(parseData.parseGender(SessionLog.GetUserGender(getActivity())));
		telepon.setText(SessionLog.GetUserPhone(getActivity()));
		alamat.setText(SessionLog.GetUserAddress(getActivity()));

		view.findViewById(R.id.keluar).setOnClickListener(view1 -> {
			new AlertDialog.Builder(getActivity())
							.setTitle("Anda yakin ingin keluar dari akun Anda ?")
							.setCancelable(false)
							.setPositiveButton("Iya", (dialogInterface, i) -> {
								SessionLog.Delete(getActivity());
								Intent intent = new Intent(getActivity(), SplashActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
								getActivity().startActivity(intent);
								getActivity().finish();
											}
							).setNegativeButton("Batal", null)
							.show();
		});

		return view;
	}

	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}
}