package com.royalcommission.bs.modules.api.model;

import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateUtils;

import java.util.Comparator;
import java.util.Objects;

/**
 * Created by Prashant on 12/23/2018.
 */
public class TestResult {

    private String Panic;

    private String Description;

    private String SPCM_NO;

    private String DeltaYN;

    private String PrevResult;

    private String RecentExamRemark;

    private String ResultBriefing;

    private String Critical;

    private String TestType;

    private String Name;

    private String TH1_SPCM_CD;

    private String Result;

    private String Remark;

    private String ProgressCode;

    private String SampleProgressStatus;

    private String HospitalName;

    private String Order_ID;

    private String IF_SPCM_NM;

    private String SampleName;

    private String ResultModificationDate;

    private String Images;

    private String Code;

    private String Flag;

    private String Date;

    private String RFVL_CNTE;

    private String EXRS_UNIT;

    private String ExamProgresCode;

    private String ResultDate;

    private String SampleNameEITM_ABBR;


    public TestResult() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getResultBriefing() {
        return ResultBriefing;
    }

    public void setResultBriefing(String resultBriefing) {
        ResultBriefing = resultBriefing;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getProgressCode() {
        return ProgressCode;
    }

    public void setProgressCode(String progressCode) {
        ProgressCode = progressCode;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public String getTestType() {
        return TestType;
    }

    public void setTestType(String testType) {
        TestType = testType;
    }

    public String getResultDate() {
        return ResultDate;
    }

    public void setResultDate(String resultDate) {
        ResultDate = resultDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getPanic() {
        return Panic;
    }

    public void setPanic(String panic) {
        Panic = panic;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getPrevResult() {
        return PrevResult;
    }

    public void setPrevResult(String prevResult) {
        PrevResult = prevResult;
    }

    public String getSPCM_NO() {
        return SPCM_NO;
    }

    public void setSPCM_NO(String SPCM_NO) {
        this.SPCM_NO = SPCM_NO;
    }

    public String getDeltaYN() {
        return DeltaYN;
    }

    public void setDeltaYN(String deltaYN) {
        DeltaYN = deltaYN;
    }

    public String getRecentExamRemark() {
        return RecentExamRemark;
    }

    public void setRecentExamRemark(String recentExamRemark) {
        RecentExamRemark = recentExamRemark;
    }

    public String getCritical() {
        return Critical;
    }

    public void setCritical(String critical) {
        Critical = critical;
    }

    public String getTH1_SPCM_CD() {
        return TH1_SPCM_CD;
    }

    public void setTH1_SPCM_CD(String TH1_SPCM_CD) {
        this.TH1_SPCM_CD = TH1_SPCM_CD;
    }

    public String getSampleProgressStatus() {
        return SampleProgressStatus;
    }

    public void setSampleProgressStatus(String sampleProgressStatus) {
        SampleProgressStatus = sampleProgressStatus;
    }

    public String getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(String order_ID) {
        Order_ID = order_ID;
    }

    public String getIF_SPCM_NM() {
        return IF_SPCM_NM;
    }

    public void setIF_SPCM_NM(String IF_SPCM_NM) {
        this.IF_SPCM_NM = IF_SPCM_NM;
    }

    public String getSampleName() {
        return SampleName;
    }

    public void setSampleName(String sampleName) {
        SampleName = sampleName;
    }

    public String getResultModificationDate() {
        return ResultModificationDate;
    }

    public void setResultModificationDate(String resultModificationDate) {
        ResultModificationDate = resultModificationDate;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getRFVL_CNTE() {
        return RFVL_CNTE;
    }

    public void setRFVL_CNTE(String RFVL_CNTE) {
        this.RFVL_CNTE = RFVL_CNTE;
    }

    public String getEXRS_UNIT() {
        return EXRS_UNIT;
    }

    public void setEXRS_UNIT(String EXRS_UNIT) {
        this.EXRS_UNIT = EXRS_UNIT;
    }

    public String getExamProgresCode() {
        return ExamProgresCode;
    }

    public void setExamProgresCode(String examProgresCode) {
        ExamProgresCode = examProgresCode;
    }

    public String getSampleNameEITM_ABBR() {
        return SampleNameEITM_ABBR;
    }

    public void setSampleNameEITM_ABBR(String sampleNameEITM_ABBR) {
        SampleNameEITM_ABBR = sampleNameEITM_ABBR;
    }


    public class CompareByExamDate implements Comparator<TestResult> {
        @Override
        public int compare(TestResult p1, TestResult p2) {

            if (CommonUtils.isValidString(p1.getDate()) && CommonUtils.isValidString(p2.getDate())) {
                if (DateUtils.getDate(p1.getDate()) != null && DateUtils.getDate(p2.getDate()) != null) {
                    if (Objects.requireNonNull(DateUtils.getDate(p1.getDate())).after(DateUtils.getDate(p2.getDate()))) {
                        return 1;
                    }
                }
            }

            if (CommonUtils.isValidString(p1.getDate()) && CommonUtils.isValidString(p2.getDate())) {
                if (DateUtils.getDate(p1.getDate()) != null && DateUtils.getDate(p2.getDate()) != null) {
                    if (Objects.requireNonNull(DateUtils.getDate(p1.getDate())).before(DateUtils.getDate(p2.getDate()))) {
                        return -1;
                    }
                }
            }
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

}
