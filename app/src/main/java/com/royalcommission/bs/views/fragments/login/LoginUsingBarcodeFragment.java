package com.royalcommission.bs.views.fragments.login;


import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.barcode.Barcode;
import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.Login;
import com.royalcommission.bs.modules.api.model.LoginResponse;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.Constants;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;
import timber.log.Timber;

import static com.royalcommission.bs.views.dialogs.BaseDialogFragment.VERIFICATION_FRAGMENT_BUNDLE_KEY_OTP;
import static com.royalcommission.bs.views.dialogs.BaseDialogFragment.VERIFICATION_FRAGMENT_BUNDLE_KEY_PATIENT_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginUsingBarcodeFragment extends BaseFragment implements BarcodeReader.BarcodeReaderListener {

    public static final String TAG = LoginUsingBarcodeFragment.class.getSimpleName();

    private BarcodeReader barcodeReader;
    private boolean isBarcodeProcessing = false;

    public LoginUsingBarcodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_using_bracelet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barcodeReader = (BarcodeReader) getChildFragmentManager().findFragmentById(R.id.barcode_fragment);
        if (barcodeReader != null) {
            barcodeReader.setListener(this);
        }
    }

    @Override
    public void onScanned(Barcode barcode) {
        Timber.tag(TAG).e("onScanned: %s", barcode.displayValue);
        barcodeReader.playBeep();
        if (!isBarcodeProcessing) {
            isBarcodeProcessing = true;
            String barcodeValues = barcode.displayValue;
            String[] barcodeData = barcodeValues.split("\n");
            if (isValidString(barcodeData[0])) {
                //runOnUIThread(() -> Toast.makeText(getActivity(), "Medical Record Number Received..", Toast.LENGTH_SHORT).show());
                doLoginByPatientID(barcodeData[0]);
            } else {
                showMessageAlert(getString(R.string.login_title), getString(R.string.barcode_id_error));
                isBarcodeProcessing = false;
            }
        }
    }

    private void doLoginByPatientID(String patientId) {
        processRequest(AppController.getWebService().getLoginByPatientID(patientId), false, false, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    LoginResponse loginResponse = (LoginResponse) object;
                    BaseResponse baseResponse = loginResponse.getBaseResponse();
                    isBarcodeProcessing = false;
                    if (baseResponse != null && isValidString(baseResponse.toString())) {
                        if (baseResponse.getResponseCode() == 1) {
                            Login login = loginResponse.getLogin();
                            if (login != null) {
                                String code = login.getMsg();
                                if (isValidString(code)) {
                                    screenMoveToOTPScreen(patientId, CommonUtils.getNumberFromString(code));
                                } else {
                                    showMessageAlert(getString(R.string.login_title), getString(R.string.barcode_id_error));
                                }
                            } else {
                                showMessageAlert(getString(R.string.login_title), getString(R.string.barcode_id_error));
                            }
                        } else {
                            showMessageAlert(getString(R.string.login_title), getString(R.string.barcode_id_error));
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                isBarcodeProcessing = false;
                showMessageAlert(getString(R.string.login_title), getString(R.string.internal_error));
            }
        }, LoginResponse.class);
    }


    private void screenMoveToOTPScreen(String patientId, String otp) {
        Bundle bundle = new Bundle();
        bundle.putString(VERIFICATION_FRAGMENT_BUNDLE_KEY_OTP, otp);
        bundle.putString(VERIFICATION_FRAGMENT_BUNDLE_KEY_PATIENT_ID, patientId);
        VerificationFragment verificationFragment = VerificationFragment.newInstance(bundle);
        verificationFragment.setPageFrom(Constants.LOGIN);
        if (getActivity() != null) {
            verificationFragment.show(getActivity().getSupportFragmentManager(), "");
            getActivity().getSupportFragmentManager().executePendingTransactions();
            if (verificationFragment.getDialog() != null) {
                verificationFragment.getDialog().setOnDismissListener(dialog -> onResume());
            }
        }
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        /*Log.e(TAG, "onScannedMultiple: " + barcodes.size());

        StringBuilder codes = new StringBuilder();
        for (Barcode barcode : barcodes) {
            codes.append(barcode.displayValue).append(", ");
        }

        final String finalCodes = codes.toString();
        runOnUIThread(() -> Toast.makeText(getActivity(), "Barcodes: " + finalCodes, Toast.LENGTH_SHORT).show());*/
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {
        Timber.tag(TAG).e("onScanError: %s", errorMessage);
        showMessageAlert(getString(R.string.login_with_patient_band), errorMessage);
    }

    @Override
    public void onCameraPermissionDenied() {
        runOnUIThread(() -> Toast.makeText(getActivity(), "Camera permission denied!", Toast.LENGTH_LONG).show());
    }


}
