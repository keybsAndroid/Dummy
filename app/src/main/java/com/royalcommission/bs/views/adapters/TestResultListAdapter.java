package com.keybs.rc.views.adapters.testresults;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keybs.rc.R;
import com.keybs.rc.modules.network.retrofit.model.responses.TestResult;
import com.keybs.rc.modules.utils.CommonUtils;
import com.keybs.rc.modules.utils.DateUtils;

import java.util.List;

/**
 * Created by Prashant on 7/8/2018.
 */

public class TestResultListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ViewGroup viewGroup;
    private List<TestResult> testResultList;

    public TestResultListAdapter(Context context, List<TestResult> testResultList) {
        this.context = context;
        this.testResultList = testResultList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        viewGroup = parent;
        View viewHolder = inflater.inflate(R.layout.item_lab_test_result_expandable_list, parent, false);
        return new TestResultsListAdapterHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TestResultsListAdapterHolder) {
            TestResultsListAdapterHolder testResultsListAdapterHolder = ((TestResultsListAdapterHolder) holder);
            TestResult testResult = testResultList.get(position);

            testResultsListAdapterHolder.childView.setVisibility(View.GONE);

            if (testResult != null) {
                String name = testResult.getName();

                if (CommonUtils.isValidString(name)) {
                    testResultsListAdapterHolder.requestDetailName.setText(name);
                } else {
                    setValues(null, testResultsListAdapterHolder.requestDetailName, null);
                }

                setValues(testResultsListAdapterHolder.specimenNameLL, testResultsListAdapterHolder.specimenName, testResult.getIF_SPCM_NM());

                setValues(testResultsListAdapterHolder.specimenCodeLL, testResultsListAdapterHolder.specimenCode, testResult.getTH1_SPCM_CD());

                setValues(testResultsListAdapterHolder.specimenNoLL, testResultsListAdapterHolder.specimenNo, testResult.getSPCM_NO());

                setValues(testResultsListAdapterHolder.sampleNameLL, testResultsListAdapterHolder.sampleName, testResult.getSampleName());

                setValues(testResultsListAdapterHolder.flagLL, testResultsListAdapterHolder.flag, testResult.getFlag());

                setValues(null, testResultsListAdapterHolder.requestDate, DateUtils.getDateTime(testResult.getDate(), false));

                setValues(testResultsListAdapterHolder.typeLL, testResultsListAdapterHolder.type, testResult.getTestType());

                setValues(testResultsListAdapterHolder.descriptionLL, testResultsListAdapterHolder.description, testResult.getDescription());

                setValues(testResultsListAdapterHolder.resultLL, testResultsListAdapterHolder.result, testResult.getResult());

                setValues(testResultsListAdapterHolder.prevResultLL, testResultsListAdapterHolder.prevResult, testResult.getPrevResult());

                setValues(testResultsListAdapterHolder.remarkLL, testResultsListAdapterHolder.remark, testResult.getRemark());

                setValues(testResultsListAdapterHolder.panicLL, testResultsListAdapterHolder.panic, testResult.getPanic());

                setValues(testResultsListAdapterHolder.resultDateLL, testResultsListAdapterHolder.resultDate, DateUtils.getDateTime(testResult.getResultDate(), false));

                setValues(testResultsListAdapterHolder.modDateLL, testResultsListAdapterHolder.modDate, DateUtils.getDateTime(testResult.getResultModificationDate(), false));

                setValues(testResultsListAdapterHolder.hospitalLL, testResultsListAdapterHolder.hospital, testResult.getHospitalName());

                setValues(testResultsListAdapterHolder.statusLL, testResultsListAdapterHolder.status, testResult.getSampleProgressStatus());

            }
        }
    }

    private void setValues(LinearLayout linearLayout, TextView textView, String values) {
        if (textView != null) {
            if (CommonUtils.isValidString(values)) {
                textView.setText(values);
                if (linearLayout != null)
                    linearLayout.setVisibility(View.VISIBLE);
            } else {
                textView.setText(context.getString(R.string.hypen));
                if (linearLayout != null)
                    linearLayout.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return testResultList == null ? 0 : testResultList.size();
    }


    public class TestResultsListAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView requestDetailName;
        private TextView requestDate;
        private ImageView rightArrow;
        private View parentView;
        private View childView;
        private boolean isExpanded = false;
        private View viewHolder;
        private CardView cardView;
        private TextView type, description, result, prevResult, remark, panic, resultDate, hospital, modDate, specimenName, specimenCode, specimenNo, sampleName, status, flag;
        private LinearLayout typeLL, descriptionLL, resultLL, prevResultLL, remarkLL, panicLL, resultDateLL, hospitalLL, modDateLL, specimenNameLL, specimenCodeLL, specimenNoLL, sampleNameLL, statusLL, flagLL;
        //private RecyclerView testImagesRV;

        TestResultsListAdapterHolder(View view) {
            super(view);
            viewHolder = view;
            requestDetailName = view.findViewById(R.id.request_detail_name);
            requestDate = view.findViewById(R.id.request_date);
            rightArrow = view.findViewById(R.id.right_arrow);
            parentView = view.findViewById(R.id.parent);
            cardView = view.findViewById(R.id.card_view);
            childView = view.findViewById(R.id.child);

            type = view.findViewById(R.id.type);
            description = view.findViewById(R.id.description);
            result = view.findViewById(R.id.result);
            prevResult = view.findViewById(R.id.prev_result);
            remark = view.findViewById(R.id.remark);
            panic = view.findViewById(R.id.panic);
            resultDate = view.findViewById(R.id.result_date);
            modDate = view.findViewById(R.id.result_mod_date);
            hospital = view.findViewById(R.id.hospital);
            specimenName = view.findViewById(R.id.specimen_name);
            specimenCode = view.findViewById(R.id.specimen_code);
            specimenNo = view.findViewById(R.id.specimen_no);
            sampleName = view.findViewById(R.id.sample_name);
            status = view.findViewById(R.id.status);
            flag = view.findViewById(R.id.flag);

            typeLL = view.findViewById(R.id.type_layout);
            descriptionLL = view.findViewById(R.id.description_layout);
            resultLL = view.findViewById(R.id.result_layout);
            prevResultLL = view.findViewById(R.id.prev_result_layout);
            remarkLL = view.findViewById(R.id.remark_layout);
            panicLL = view.findViewById(R.id.panic_layout);
            resultDateLL = view.findViewById(R.id.result_date_layout);
            modDateLL = view.findViewById(R.id.result_mod_date_layout);
            hospitalLL = view.findViewById(R.id.hospital_layout);
            specimenNameLL = view.findViewById(R.id.specimen_name_layout);
            specimenCodeLL = view.findViewById(R.id.specimen_code_layout);
            specimenNoLL = view.findViewById(R.id.specimen_no_layout);
            sampleNameLL = view.findViewById(R.id.sample_name_layout);
            statusLL = view.findViewById(R.id.status_layout);
            flagLL = view.findViewById(R.id.flag_layout);


            //testImagesRV = childView.findViewById(R.id.test_images);
            parentView.setOnClickListener(this);
            cardView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            if (view == null)
                return;

            if (view.getId() == parentView.getId() || view.getId() == cardView.getId()) {
                showExpand();
            }
        }

        private void showExpand() {
            if (!isExpanded) {
                isExpanded = true;
                rightArrow.setImageResource(R.drawable.outline_keyboard_arrow_down_black_24);

            } else {
                isExpanded = false;
                rightArrow.setImageResource(R.drawable.arrow_right_24_auto_mirror);

            }
            ChangeBounds transition = new ChangeBounds();
            transition.setDuration(125);
            TransitionManager.beginDelayedTransition(viewGroup, transition);
            viewHolder.setActivated(isExpanded);
            childView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}
