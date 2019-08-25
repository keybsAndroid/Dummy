package com.royalcommission.bs.views.fragments.login;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.royalcommission.bs.R;
import com.royalcommission.bs.views.activities.LoginActivity;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginSelectionFragment extends BaseFragment implements View.OnClickListener {

    private CardView passwordLogin, braceletLogin;
    public LoginSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_selection, container, false);
        passwordLogin = view.findViewById(R.id.password_login);
        braceletLogin = view.findViewById(R.id.bracelet_login);
        passwordLogin.setOnClickListener(this);
        braceletLogin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (getActivity() != null) {
            if (v.getId() == passwordLogin.getId()) {
                ((LoginActivity) getActivity()).loadFragment(R.id.container, new LoginUsingFormFragment(), true, LoginUsingFormFragment.TAG);
            } else if (v.getId() == braceletLogin.getId()) {
                ((LoginActivity) getActivity()).loadFragment(R.id.container, new LoginUsingBarcodeFragment(), true, LoginUsingBarcodeFragment.TAG);
            }
        }
    }
}
