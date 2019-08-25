package com.keybs.rc.views.adapters.general;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.keybs.rc.R;
import com.keybs.rc.app.AppController;
import com.keybs.rc.modules.network.retrofit.model.responses.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prashant on 8/9/2018.
 */
public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder> {

    private String selectedAnswer = "";
    private List<Question> questionList = new ArrayList<>();
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public SurveyAdapter(Question question, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
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
        Question question = (questionList.get(position));
        if (question != null) {

            /*if (AppController.isEnglish()) {
                holder.questions.setText(question.getQuestion());
                holder.option1.setText(question.getAnswer1());
                holder.option2.setText(question.getAnswer2());
                holder.option3.setText(question.getAnswer3());
                holder.option4.setText(question.getAnswer4());
            } else {
                holder.questions.setText(question.getQuestionAr());
                holder.option1.setText(question.getAnswer1Ar());
                holder.option2.setText(question.getAnswer2Ar());
                holder.option3.setText(question.getAnswer3Ar());
                holder.option4.setText(question.getAnswer4Ar());
            }*/

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


        /*if (position == ITEM_COUNT - 1) {
            holder.buttonLayout.setVisibility(View.VISIBLE);
            holder.cardView.setVisibility(View.GONE);
        } else {
            holder.cardView.setVisibility(View.VISIBLE);
            holder.buttonLayout.setVisibility(View.GONE);
        }*/
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
        private CardView cardView;
        private LinearLayout buttonLayout;
        private Button back, submit;
        //private RadioButton radioButton1, radioButton2, radioButton3;

        SurveyViewHolder(View itemView) {
            super(itemView);
            questions = itemView.findViewById(R.id.question);
            radioGroup = itemView.findViewById(R.id.rg_options);
            option1 = itemView.findViewById(R.id.option1);
            option2 = itemView.findViewById(R.id.option2);
            option3 = itemView.findViewById(R.id.option3);
            option4 = itemView.findViewById(R.id.option4);
            cardView = itemView.findViewById(R.id.card_view);
            buttonLayout = itemView.findViewById(R.id.button_layout);
            back = itemView.findViewById(R.id.back);
            submit = itemView.findViewById(R.id.submit);
        }
    }

    public interface RecyclerViewOnItemClickListener {

        void onSubmitClick(Question question);

        void onBackClick();
    }

}
