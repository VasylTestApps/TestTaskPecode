package com.vasyl.testtaskpecode.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vasyl.testtaskpecode.R;
import com.vasyl.testtaskpecode.models.FragmentData;
import com.vasyl.testtaskpecode.utils.NotificationHelper;
import static java.lang.Math.toIntExact;


import java.util.Calendar;

public class MyFragment extends Fragment {

    private TextView mTextView;
    public static final String FRAGMENT_ID = "fragment_id";
    public static final String FRAGMENT_LIST_SIZE = "list_size";

    private int mId;
    private Button mCreateNotitficationButton;
    private RelativeLayout mMinusButton, mPlusButton;
    MyFragmentListener mListener;

    public static MyFragment newInstance(int fragmentId, int fragmentListSize) {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        args.putInt(FRAGMENT_ID, fragmentId);
        args.putInt(FRAGMENT_LIST_SIZE, fragmentListSize);
        fragment.setArguments(args);
        return fragment;
    }

    public MyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getContext().getTheme().applyStyle(R.style.AppTheme, true);
        View view = inflater.inflate(R.layout.my_fragment, null, false);
        mTextView = view.findViewById(R.id.tv_count);
        mCreateNotitficationButton = view.findViewById(R.id.btn_create_notification);
        mMinusButton = view.findViewById(R.id.btn_minus);
        mPlusButton = view.findViewById(R.id.btn_plus);
        mPlusButton.setOnClickListener(view1 -> createNewFragment());
        Bundle bundle = getArguments();
        Integer id = bundle.getInt(FRAGMENT_ID);
        Integer size = bundle.getInt(FRAGMENT_LIST_SIZE);
        mId = id;
        mTextView.setText(id.toString());
        mCreateNotitficationButton.setOnClickListener(v -> createNotification(getContext()));
        if (size <= 1)
            mMinusButton.setVisibility(View.GONE);
        else {
            mMinusButton.setVisibility(View.VISIBLE);
            mMinusButton.setOnClickListener(view1 -> mListener.deleteFragment(mId));
        }
        return view;
    }

    private void createNotification(Context context) {
        int notificationId = (int) Calendar.getInstance().getTime().getTime();
        NotificationHelper.createNotification(context, notificationId, mId);
        mListener.addNotificationId(mId, notificationId);
    }


    private void createNewFragment() {
        FragmentData newFragmentData = new FragmentData();
        newFragmentData.setId(mId + 1);
        mListener.createNewFragment(newFragmentData);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (MainActivity) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement" + MainActivity.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    interface MyFragmentListener {
        void createNewFragment(FragmentData fragmentData);

        void deleteFragment(int id);

        void addNotificationId(int fragmentId,int id);
    }
}
