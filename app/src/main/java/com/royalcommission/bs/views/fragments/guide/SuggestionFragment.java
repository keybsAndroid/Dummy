package com.royalcommission.bs.views.fragments.guide;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.SurveyResponse;
import com.royalcommission.bs.modules.api.parser.RetrofitResponseParser;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.activities.HomeActivity;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestionFragment extends BaseFragment {

    private EditText suggestionsEditText;

    public SuggestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suggestion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        suggestionsEditText = view.findViewById(R.id.edittext);
        Button submit = view.findViewById(R.id.submit);
        Button back = view.findViewById(R.id.back);
        submit.setOnClickListener(v -> {
            if (suggestionsEditText != null && isValidString(suggestionsEditText.getText().toString())) {
                sendSuggestion(suggestionsEditText.getText().toString());
            } else {
                showToastMessage(getString(R.string.fields_empty));
            }
        });
        back.setOnClickListener(v -> {
            if (getActivity() != null) {
                ((HomeActivity) getActivity()).goBack();
            }
        });
    }

    private void sendSuggestion(String complaints) {
        processRequest(AppController.getWebService().postComplaint(SharedPreferenceUtils.getPatientID(AppController.getInstance()), complaints), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    SurveyResponse surveyResponse = RetrofitResponseParser.convertInstanceOfObject(object, SurveyResponse.class);
                    if (surveyResponse != null) {
                        BaseResponse baseResponse = surveyResponse.getBaseResponse();
                        if (baseResponse != null) {
                            if (baseResponse.getResponseCode() == 1) {
                                showMessageAlert(getString(R.string.complaint), getString(R.string.complaint_success));
                                if (suggestionsEditText != null) {
                                    suggestionsEditText.setText("");
                                }
                            } else {
                                showMessageAlert(getString(R.string.complaint), getString(R.string.complaint_error));
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        }, SurveyResponse.class);
    }

}
