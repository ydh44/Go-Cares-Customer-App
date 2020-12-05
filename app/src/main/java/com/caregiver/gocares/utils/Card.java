package com.caregiver.gocares.utils;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.caregiver.gocares.R;

public class Card {

    //inialisasi variable before calling id element layout

    ConstraintLayout ExpandableView_detail_pesanan, ExpandableView_detail_lansia, ExpandableView_detail_ulasan;
    ConstraintLayout detail_pesananHead, detail_lansiaHead, detail_ulasanHead;
    LinearLayout expandableView_Cardview;
    ConstraintLayout detailBtn;
    TextView detailTv;
    ImageView detail_pesananBtn,  detail_lansiaBtn, detail_ulasanBtn;
    ViewGroup parent;

    final AutoTransition transition;

    public Card(@Nullable ConstraintLayout expandableView_detail_pesanans,
                ConstraintLayout expandableView_detail_lansias,
                ConstraintLayout expandableView_detail_ulasans,
                LinearLayout expandableView_Cardviews,
                ConstraintLayout detail_pesananHeads,
                ConstraintLayout detail_lansiaHeads,
                ConstraintLayout detail_ulasanHeads,
                ConstraintLayout detailBtns,
                TextView detailTvs,
                ImageView detail_pesananBtns,
                ImageView detail_lansiaBtns,
                ImageView detail_ulasanBtns,
                ViewGroup parents) {
        ExpandableView_detail_pesanan = expandableView_detail_pesanans;
        ExpandableView_detail_lansia = expandableView_detail_lansias;
        ExpandableView_detail_ulasan = expandableView_detail_ulasans;
        expandableView_Cardview = expandableView_Cardviews;
        detail_pesananHead = detail_pesananHeads;
        detail_lansiaHead = detail_lansiaHeads;
        detail_ulasanHead = detail_ulasanHeads;
        detailBtn = detailBtns;
        detailTv = detailTvs;
        detail_pesananBtn = detail_pesananBtns;
        detail_lansiaBtn = detail_lansiaBtns;
        detail_ulasanBtn = detail_ulasanBtns;
        parent = parents;

        this.transition = new AutoTransition();
        transition.setDuration(150);

        // method on click element detail  nested expandable cardview
        if(detail_pesananHead == null && detail_lansiaHead == null && detail_ulasanHead == null){

            detailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableView_Cardview.getVisibility()==View.GONE){
                        TransitionManager.beginDelayedTransition(parent, transition);
                        expandableView_Cardview.setVisibility(View.VISIBLE);
                        detailTv.setText("Tutup");
                    } else {
                        TransitionManager.beginDelayedTransition(parent, transition);
                        expandableView_Cardview.setVisibility(View.GONE);
                        detailTv.setText("Detail");
                    }
                }
            });
        }else {
            detailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableView_Cardview.getVisibility()==View.GONE){
                        TransitionManager.beginDelayedTransition(parent, transition);
                        expandableView_Cardview.setVisibility(View.VISIBLE);
                        detailTv.setText("Tutup");
                    } else {
                        TransitionManager.beginDelayedTransition(parent, transition);
                        expandableView_Cardview.setVisibility(View.GONE);
                        ExpandableView_detail_lansia.setVisibility(View.GONE);
                        ExpandableView_detail_pesanan.setVisibility(View.GONE);
                        ExpandableView_detail_ulasan.setVisibility(View.GONE);
                        detail_lansiaBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                        detail_pesananBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                        detail_ulasanBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                        detailTv.setText("Detail");
                    }
                }
            });

            //method element detail lansia nested expandable cardview

            detail_lansiaHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ExpandableView_detail_lansia.getVisibility()==View.GONE){
                        TransitionManager.beginDelayedTransition(parent, transition);
                        ExpandableView_detail_lansia.setVisibility(View.VISIBLE);
                        ExpandableView_detail_pesanan.setVisibility(View.GONE);
                        ExpandableView_detail_ulasan.setVisibility(View.GONE);
                        detail_lansiaBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                        detail_pesananBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                        detail_ulasanBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    } else {
                        TransitionManager.beginDelayedTransition(parent, transition);
                        ExpandableView_detail_lansia.setVisibility(View.GONE);
                        detail_lansiaBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    }
                }
            });

            //method element detail lansia nested expandable cardview

            detail_pesananHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ExpandableView_detail_pesanan.getVisibility()==View.GONE){

                        TransitionManager.beginDelayedTransition(parent,transition);
                        ExpandableView_detail_pesanan.setVisibility(View.VISIBLE);
                        ExpandableView_detail_lansia.setVisibility(View.GONE);
                        ExpandableView_detail_ulasan.setVisibility(View.GONE);
                        detail_pesananBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                        detail_lansiaBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                        detail_ulasanBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    } else {
                        TransitionManager.beginDelayedTransition(parent, transition);
                        ExpandableView_detail_pesanan.setVisibility(View.GONE);
                        detail_pesananBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    }
                }
            });
            //method element detail ulasan nested expandable cardview

            detail_ulasanHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ExpandableView_detail_ulasan.getVisibility()==View.GONE){
                        TransitionManager.beginDelayedTransition(parent, transition);
                        ExpandableView_detail_ulasan.setVisibility(View.VISIBLE);
                        ExpandableView_detail_lansia.setVisibility(View.GONE);
                        ExpandableView_detail_pesanan.setVisibility(View.GONE);
                        detail_ulasanBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                        detail_pesananBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                        detail_lansiaBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    } else {
                        TransitionManager.beginDelayedTransition(parent, transition);
                        ExpandableView_detail_ulasan.setVisibility(View.GONE);
                        detail_ulasanBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    }
                }
            });
        }
    }
}