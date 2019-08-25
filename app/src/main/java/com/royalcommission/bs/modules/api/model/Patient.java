package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prashant on 12/10/2018.
 */
public class Patient {
    private String PatientId;
    private String ParentID;
    private String Gender;
    private String BirthDate;
    @SerializedName("ExpireDate")
    private String ExpiryDate;
    private String FullName;
    private String FirstName;
    private String SecondName;
    private String ThirdName;
    private String FourthName;

    private String FirstNameAr;
    private String SecondNameAr;
    private String ThirdNameAr;
    private String FourthNameAr;

    private String Email;
    private String ProfileImage;
    private String Mobile;
    private String Nationality;
    private String RegId;
    private String MaritalStatus;
    private String Address;
    private String RegType;
    private String Eligibility;
    private String HospitalID;
    private boolean GroupAdmin;
    @SerializedName("GrantAccessAdmin")
    private boolean grantAccessByAdmin;
    private boolean IsNameInArabic;
    private boolean IsLocalAccount;
    private String PME_CLS_CD;
    private String PSE_CLS_CD;
    private String PatientCategory;
    private String BloodGroup;

    public String getPatientId() {
        return PatientId;
    }

    public void setPatientId(String patientId) {
        PatientId = patientId;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getRegId() {
        return RegId;
    }

    public void setRegId(String regId) {
        RegId = regId;
    }

    public String getMaritalStatus() {
        return MaritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        MaritalStatus = maritalStatus;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getRegType() {
        return RegType;
    }

    public void setRegType(String regType) {
        RegType = regType;
    }

    public String getEligibility() {
        return Eligibility;
    }

    public void setEligibility(String eligibility) {
        Eligibility = eligibility;
    }

    public String getHospitalID() {
        return HospitalID;
    }

    public void setHospitalID(String hospitalID) {
        HospitalID = hospitalID;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public boolean isGroupAdmin() {
        return GroupAdmin;
    }

    public void setGroupAdmin(boolean groupAdmin) {
        GroupAdmin = groupAdmin;
    }

    public String getPME_CLS_CD() {
        return PME_CLS_CD;
    }

    public void setPME_CLS_CD(String PME_CLS_CD) {
        this.PME_CLS_CD = PME_CLS_CD;
    }

    public String getPSE_CLS_CD() {
        return PSE_CLS_CD;
    }

    public void setPSE_CLS_CD(String PSE_CLS_CD) {
        this.PSE_CLS_CD = PSE_CLS_CD;
    }

    public boolean isNameInArabic() {
        return IsNameInArabic;
    }

    public void setNameInArabic(boolean nameInArabic) {
        IsNameInArabic = nameInArabic;
    }

    public String getFourthNameAr() {
        return FourthNameAr;
    }

    public void setFourthNameAr(String fourthNameAr) {
        FourthNameAr = fourthNameAr;
    }

    public String getThirdNameAr() {
        return ThirdNameAr;
    }

    public void setThirdNameAr(String thirdNameAr) {
        ThirdNameAr = thirdNameAr;
    }

    public String getSecondNameAr() {
        return SecondNameAr;
    }

    public void setSecondNameAr(String secondNameAr) {
        SecondNameAr = secondNameAr;
    }

    public String getFirstNameAr() {
        return FirstNameAr;
    }

    public void setFirstNameAr(String firstNameAr) {
        FirstNameAr = firstNameAr;
    }

    public String getFourthName() {
        return FourthName;
    }

    public void setFourthName(String fourthName) {
        FourthName = fourthName;
    }

    public String getThirdName() {
        return ThirdName;
    }

    public void setThirdName(String thirdName) {
        ThirdName = thirdName;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public boolean isLocalAccount() {
        return IsLocalAccount;
    }

    public void setLocalAccount(boolean localAccount) {
        IsLocalAccount = localAccount;
    }


    public String getPatientCategory() {
        return PatientCategory;
    }

    public void setPatientCategory(String patientCategory) {
        PatientCategory = patientCategory;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public boolean isGrantAccessByAdmin() {
        return grantAccessByAdmin;
    }

    public void setGrantAccessByAdmin(boolean grantAccessByAdmin) {
        this.grantAccessByAdmin = grantAccessByAdmin;
    }

    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String parentID) {
        ParentID = parentID;
    }

}
