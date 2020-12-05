package com.caregiver.gocares.views.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.caregiver.gocares.BuildConfig;
import com.caregiver.gocares.views.activity.ImageprevActivity;
import com.caregiver.gocares.R;
import com.caregiver.gocares.databinding.PembayaranFragmentBinding;
import com.caregiver.gocares.viewmodels.PembayaranViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PembayaranFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PembayaranFragment extends Fragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	public PembayaranViewModel viewModel;
	public PembayaranFragmentBinding binding;
	public String idPesanan;
	public String jumlahBayar;
	public final int PERMISIION_CODE = 1000;
	public final int IMAGE_CAPTURE_CODE = 0;
	public final int IMAGE_PICK_CODE = 1;

	String currentPhotoPath, id, total, idcg;

	public Boolean pil;
	Uri photoURI;
	File photoFile = null;

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	public PembayaranFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment PembayaranFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static PembayaranFragment newInstance(String param1, String param2) {
		PembayaranFragment fragment = new PembayaranFragment();
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
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		viewModel = new PembayaranViewModel(this);
		binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.pembayaran_fragment, null, false);
		View view = this.binding.getRoot();
		binding.setPembayaranModel(viewModel);
		assert getArguments() != null;
		idPesanan = getArguments().getString("id_pesanan");
		jumlahBayar = getArguments().getString("jumlah_bayar");
		idcg = getArguments().getString("id_cg");

		return view;
	}


	public void pickImageFromGallery() {
		// intent to pick image
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, IMAGE_PICK_CODE);
	}

	public void openCamera() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
			// Create the File where the photo should go
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				// Error occurred while creating the File
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				photoURI = FileProvider.getUriForFile(requireActivity(),
								BuildConfig.APPLICATION_ID + ".provider",
								photoFile);

				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
				startActivityForResult(takePictureIntent, IMAGE_CAPTURE_CODE);
			}

		}
	}
	File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
						imageFileName,  /* prefix */
						".jpg",         /* suffix */
						storageDir/* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		currentPhotoPath = image.getAbsolutePath();
		return image;
	}


	//handling permission result
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		//this method called , when user presses Allow or Deny from permission request popup
		switch (requestCode) {
			case PERMISIION_CODE: {
				if (grantResults.length > 0 && grantResults[0] ==
								PackageManager.PERMISSION_GRANTED) {
					if(pil){
						openCamera();
					}else {
						pickImageFromGallery();
					}
					//permission form popup was granted
				} else {
					//permission from popup was denied
					Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);
		switch (reqCode) {
			case 0:
				if (resultCode == RESULT_OK) {
					String uri = photoURI.toString();
					Intent i = new Intent(getActivity(), ImageprevActivity.class);
					i.putExtra("image", uri);
					i.putExtra("idcg", idcg);
					i.putExtra("id", idPesanan);
					i.putExtra("path", photoFile.getPath());
					requireActivity().startActivity(i);
					requireActivity().finish();
				}
				break;
			case 1:
				if (resultCode == RESULT_OK) {
					final Uri imageUri = data.getData();
					assert imageUri != null;
					String uri = imageUri.toString();
					Intent i = new Intent(getActivity(), ImageprevActivity.class);
					i.putExtra("image", uri);
					i.putExtra("id", idPesanan);
					i.putExtra("idcg", idcg);
					requireActivity().startActivity(i);
					requireActivity().finish();
				}
				break;
			default:
				break;
		}
	}
}