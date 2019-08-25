package com.royalcommission.bs.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.model.SurveyQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prashant on 8/9/2018.
 */
public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder> {

    private String selectedAnswer = "";
    private List<SurveyQuestion> questionList = new ArrayList<>();
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public SurveyAdapter(SurveyQuestion question, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        this.questionList.clear();
        this.questionList.add(question);

    }

    @NonNull
    @Override
    public SurveyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey, parent, false);
        return new SurveyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final SurveyViewHolder holder, int position) {
        SurveyQuestion question = (questionList.get(position));
        if (question != null) {
            holder.questions.setText(AppController.isEnglish() ? question.getQuestion() : question.getQuestionAr());
            holder.option1.setText(AppController.isEnglish() ? question.getAnswer1() : question.getAnswer1Ar());
            holder.option2.setText(AppController.isEnglish() ? question.getAnswer2() : question.getAnswer2Ar());
            holder.option3.setText(AppController.isEnglish() ? question.getAnswer3() : question.getAnswer3Ar());
            holder.option4.setText(AppController.isEnglish() ? question.getAnswer4() : question.getAnswer4Ar());

            holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                switch (checkedId) {
                    case R.id.option1:
                        selectedAnswer = getSelectedOption(group);
                        break;
                    case R.id.option2:
                        selectedAnswer = getSelectedOption(group);
                        break;

                    case R.id.option3:
                        selectedAnswer = getSelectedOption(group);
                        break;

                    case R.id.option4:
                        selectedAnswer = getSelectedOption(group);
                        break;
                }
                holder.buttonLayout.setVisibility(View.VISIBLE);
                question.setSelectedAnswer(selectedAnswer);

                holder.submit.setOnClickListener(v -> recyclerViewOnItemClickListener.onSubmitClick(question));

                holder.back.setOnClickListener(v -> recyclerViewOnItemClickListener.onBackClick());

            });
        }
    }

    private String getSelectedOption(RadioGroup group) {
        RadioButton radioButtonAns = group.findViewById(group.getCheckedRadioButtonId());
        if (radioButtonAns != null) {
            return radioButtonAns.getText().toString();
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return questionList == null ? 0 : questionList.size();
    }

    class SurveyViewHolder extends RecyclerView.ViewHolder {

        private TextView questions;
        private RadioGroup radioGroup;
        private RadioButton option1, option2, option3, option4;
        private LinearLayout buttonLayout;
        private Button back, submit;

        SurveyViewHolder(View itemView) {
            super(itemView);
            questions = itemView.findViewById(R.id.question);
            radioGroup = itemView.findViewById(R.id.rg_options);
            option1 = itemView.findViewById(R.id.option1);
            option2 = itemView.findViewById(R.id.option2);
            option3 = itemView.findViewById(R.id.option3);
            option4 = itemView.findViewById(R.id.option4);
            buttonLayout = itemView.findViewById(R.id.button_layout);
            back = itemView.findViewById(R.id.back);
            back.setVisibility(View.GONE);
            submit = itemView.findViewById(R.id.submit);
        }
    }

    public interface RecyclerViewOnItemClickListener {

        void onSubmitClick(SurveyQuestion question);

        void onBackClick();
    }

}
