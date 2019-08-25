package com.royalcommission.bs.views.fragments.guide;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.SurveyQuestion;
import com.royalcommission.bs.modules.api.model.SurveyQuestionResponse;
import com.royalcommission.bs.modules.api.model.SurveyResponse;
import com.royalcommission.bs.modules.api.parser.RetrofitResponseParser;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.adapters.SurveyAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends BaseFragment implements SurveyAdapter.RecyclerViewOnItemClickListener {


    private RecyclerView surveyQuestionsRecyclerView;

    public SurveyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        surveyQuestionsRecyclerView = view.findViewById(R.id.survey_list);
        getSurveyQuestions();
    }

    private void getSurveyQuestions() {
        processRequest(AppController.getWebService().getSurveyQuestions(SharedPreferenceUtils.getPatientID(AppController.getInstance())), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                SurveyQuestionResponse surveyQuestionResponse = RetrofitResponseParser.convertInstanceOfObject(object, SurveyQuestionResponse.class);
                if (surveyQuestionResponse != null) {
                    BaseResponse baseResponse = surveyQuestionResponse.getBaseResponse();
                    if (baseResponse != null) {
                        if (baseResponse.getResponseCode() == 1) {
                            SurveyQuestion question = surveyQuestionResponse.getSurveyQuestion();
                            if (question != null) {
                                setQuestion(question);
                            }
                        } else {
                            showMessageAlert(getString(R.string.survey), getString(R.string.internal_error));
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                showMessageAlert(getString(R.string.survey), getString(R.string.internal_error));
            }
        }, SurveyQuestionResponse.class);
    }

    private void setQuestion(SurveyQuestion question) {
        setAdapter(surveyQuestionsRecyclerView, question);
    }

    private void setAdapter(RecyclerView surveyRecyclerView, SurveyQuestion question) {
        if (surveyRecyclerView != null) {
            SurveyAdapter surveyAdapter = new SurveyAdapter(question, this);
            surveyRecyclerView.setHasFixedSize(true);
            surveyRecyclerView.setLayoutManager(new LinearLayoutManager(surveyRecyclerView.getContext()));
            surveyRecyclerView.setNestedScrollingEnabled(false);
            surveyRecyclerView.setAdapter(surveyAdapter);
        }
    }

    @Override
    public void onSubmitClick(SurveyQuestion question) {
        if (getActivity() != null && question != null) {
            submitAnswer(question);
        }
    }

    private void submitAnswer(SurveyQuestion question) {

        processRequest(AppController.getWebService().postSurveyAnswer(SharedPreferenceUtils.getPatientID(AppController.getInstance()), question.getQuestionNo(), question.getSelectedAnswer()), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    SurveyResponse surveyResponse = RetrofitResponseParser.convertInstanceOfObject(object, SurveyResponse.class);
                    if (surveyResponse != null) {
                        BaseResponse baseResponse = surveyResponse.getBaseResponse();
                        if (baseResponse != null) {
                            if (baseResponse.getResponseCode() == 1) {
                                showMessageAlert(getString(R.string.survey), getString(R.string.survey_success));
                            } else {
                                showMessageAlert(getString(R.string.survey), getString(R.string.survey_error));
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

    @Override
    public void onBackClick() {

    }
}
