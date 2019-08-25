package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prashant on 12/30/2018.
 */
public class Question {
    @SerializedName("Question")
    private String question;

    @SerializedName("QuestionArabic")
    private String questionAr;

    @SerializedName("Answer1")
    private String answer1;

    @SerializedName("Answer1Arabic")
    private String answer1Ar;

    @SerializedName("Answer2")
    private String answer2;

    @SerializedName("Answer2Arabic")
    private String answer2Ar;

    @SerializedName("Answer3")
    private String answer3;

    @SerializedName("Answer3Arabic")
    private String answer3Ar;

    @SerializedName("Answer4")
    private String answer4;

    @SerializedName("Answer4Arabic")
    private String answer4Ar;

    @SerializedName("Qno")
    private String questionNo;

    private String selectedAnswer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public String getQuestionAr() {
        return questionAr;
    }

    public void setQuestionAr(String questionAr) {
        this.questionAr = questionAr;
    }

    public String getAnswer1Ar() {
        return answer1Ar;
    }

    public void setAnswer1Ar(String answer1Ar) {
        this.answer1Ar = answer1Ar;
    }

    public String getAnswer2Ar() {
        return answer2Ar;
    }

    public void setAnswer2Ar(String answer2Ar) {
        this.answer2Ar = answer2Ar;
    }

    public String getAnswer3Ar() {
        return answer3Ar;
    }

    public void setAnswer3Ar(String answer3Ar) {
        this.answer3Ar = answer3Ar;
    }

    public String getAnswer4Ar() {
        return answer4Ar;
    }

    public void setAnswer4Ar(String answer4Ar) {
        this.answer4Ar = answer4Ar;
    }
}
