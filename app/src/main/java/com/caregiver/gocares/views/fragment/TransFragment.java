package com.caregiver.gocares.views.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caregiver.gocares.R;
import com.caregiver.gocares.repositories.GetUser;
import com.caregiver.gocares.utils.CekConnection;
import com.caregiver.gocares.views.activity.MainActivity;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransFragment extends Fragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	public TransFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment TransFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static TransFragment newInstance(String param1, String param2) {
		TransFragment fragment = new TransFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.trans_fragment, container, false);
		new CekConnection(getContext(), true).status.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				if (aBoolean) {
					new GetUser(getContext(), "Bearer " + getArguments().getString("bearer")).user.observe(
									getViewLifecycleOwner(), new Observer<Boolean>() {
										@Override
										public void onChanged(Boolean Boolean) {
											if(Boolean){
												Intent intent = new Intent(getActivity(), MainActivity.class);
												intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
												startActivity(intent);
											}else if(aBoolean == null){
												getActivity().finish();
											}
										}
									}
					);
				}else {
					getActivity().finish();
				}
			}
		});
		return view;

	}
}