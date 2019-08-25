package com.royalcommission.bs.views.fragments.dashboard.documents;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.Constants;
import com.royalcommission.bs.modules.utils.GridSpacingItemDecoration;
import com.royalcommission.bs.views.adapters.PhysicianAdapter;
import com.royalcommission.bs.views.dialogs.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhysicianRoundingDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private AlertDialog alertDialog;
    private long lastClickedTime;

    private static List<String> doctorList = new ArrayList<>();

    public PhysicianRoundingDialogFragment() {
        // Required empty public constructor
    }

    public static PhysicianRoundingDialogFragment getInstance(List<String> doctors) {
        // Required empty public constructor
        doctorList = doctors;
        return new PhysicianRoundingDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setCanceledOnTouchOutside(false);
            if (!alertDialog.isShowing())
                alertDialog.show();
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_physician_rounding, null, false);
            alertDialog.setContentView(view);
            RecyclerView recyclerView = view.findViewById(R.id.today_physician_rounding_recycler_view);
            setAdapter(recyclerView);
            view.findViewById(R.id.cancel).setOnClickListener(this);
        }
        return alertDialog;
    }

    private void setAdapter(RecyclerView recyclerView) {
        if (getActivity() != null) {
            PhysicianAdapter physicianAdapter = new PhysicianAdapter(getActivity(), doctorList);
            recyclerView.setHasFixedSize(true);
            int valueInPixels = getResources().getInteger(R.integer.grid_view_spacing);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_COLUMNS));
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(Constants.NUMBER_OF_COLUMNS, CommonUtils.dpToPx(recyclerView.getContext(), valueInPixels), true));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(physicianAdapter);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getDialog().getWindow() != null) {
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setAttributes(params);
        }
        blockBackButtonPressWhenDialogOpen();
    }

    private void blockBackButtonPressWhenDialogOpen() {
        if (getDialog() != null) {
            getDialog().setOnKeyListener((dialog, keyCode, event) -> (keyCode == android.view.KeyEvent.KEYCODE_BACK));
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            if (SystemClock.elapsedRealtime() - lastClickedTime < 1000)
                return;
            lastClickedTime = SystemClock.elapsedRealtime();
            if (view.getId() == R.id.cancel) {
                dismissAllowingStateLoss();
            }
        }
    }

}
