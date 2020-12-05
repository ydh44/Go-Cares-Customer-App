package com.caregiver.gocares.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caregiver.gocares.R;
import com.caregiver.gocares.databinding.RiwayatFragmentBinding;
import com.caregiver.gocares.models.Pesanan;
import com.caregiver.gocares.views.adapter.RiwayatAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RiwayatFragment extends Fragment {

	MutableLiveData<List<Pesanan>> pesanan = new MutableLiveData<>();

	@Override
	public View onCreateView(
					LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState
	) {
		View view = inflater.inflate(R.layout.riwayat_fragment, container, false);

		ViewGroup viewGroup = view.findViewById(R.id.top);
		RecyclerView recyclerView = view.findViewById(R.id.recycler);

		assert getArguments() != null;
		pesanan.postValue(getArguments().getParcelableArrayList("riwayat"));


		pesanan.observe(requireActivity(), v ->{
			pesanan.removeObservers(requireActivity());
			if(v.size()<1){
				recyclerView.setVisibility(View.GONE);
				view.findViewById(R.id.nopesan).setVisibility(View.VISIBLE);
			}else {
				Collections.reverse(v);

				RiwayatAdapter riwayatAdapter = new RiwayatAdapter(getActivity(), v, requireActivity().findViewById(R.id.frame));
				recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
				recyclerView.setAdapter(riwayatAdapter);

			}
		});


		return view;
	}


	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);



	}

	@Override
	public void onAttachFragment(@NonNull Fragment childFragment) {
		super.onAttachFragment(childFragment);

	}

}