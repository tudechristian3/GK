package com.goodkredit.myapplication.fragments.paramount;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.paramount.ParamountInsuranceActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.ParamountQueue;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 11/29/2017.
 */

public class PersonalInformationFragment extends BaseFragment implements View.OnClickListener {
    private DatabaseHandler mdb;
    private String currentyear;
    private String requestID;

    private EditText edtEmailAdress;
    private EditText edtType;
    private EditText edtCorporate;
    private EditText edtFirstName;
    private EditText edtMiddleName;
    private EditText edtLastName;

    private LinearLayout corporateNameLayout;
    private LinearLayout firstNameLayout;
    private LinearLayout middleNameLayout;
    private LinearLayout lastNameLayout;

    private List<String> arrType;

    private TextView emailErrorMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_information, container, false);

        setHasOptionsMenu(true);

        try {

            init(view);

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    private void init(View view) {
        view.findViewById(R.id.btnNext).setOnClickListener(this);

        edtEmailAdress = (EditText) view.findViewById(R.id.edtEmailAdress);
        edtEmailAdress.addTextChangedListener(new QuantityTextWatcher(edtEmailAdress));
        edtType = (EditText) view.findViewById(R.id.edtType);
        edtType.setText("Individual");
        edtType.setOnClickListener(this);
        edtCorporate = (EditText) view.findViewById(R.id.edtCorporate);
        edtFirstName = (EditText) view.findViewById(R.id.edtFirstName);
        edtMiddleName = (EditText) view.findViewById(R.id.edtMiddleName);
        edtLastName = (EditText) view.findViewById(R.id.edtLastName);
        emailErrorMessage = (TextView) view.findViewById(R.id.emailErrorMessage);

        corporateNameLayout = (LinearLayout) view.findViewById(R.id.corporateNameLayout);
        firstNameLayout = (LinearLayout) view.findViewById(R.id.firstNameLayout);
        middleNameLayout = (LinearLayout) view.findViewById(R.id.middleNameLayout);
        lastNameLayout = (LinearLayout) view.findViewById(R.id.lastNameLayout);

    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        arrType = new ArrayList<>();
        arrType.add("Individual");
        arrType.add("Corporate without assignee");
        arrType.add("Corporate with assignee");
        currentyear = getArguments().getString("currentyear");
        requestID = getArguments().getString("generatedid");
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mdb != null) {

            ParamountQueue paramountQueue = mdb.getParamountQueue(mdb);
            if (paramountQueue != null) {

                if (paramountQueue.getEmailAddress() != null) {
                    edtEmailAdress.setText(paramountQueue.getEmailAddress());
                }

                if (paramountQueue.getType() != null) {
                    edtType.setText(paramountQueue.getType());

                    switch (paramountQueue.getType()) {
                        case "Individual": {
                            edtCorporate.setText("");
                            corporateNameLayout.setVisibility(View.GONE);
                            edtFirstName.setText("");
                            firstNameLayout.setVisibility(View.VISIBLE);
                            edtMiddleName.setText("");
                            middleNameLayout.setVisibility(View.VISIBLE);
                            edtLastName.setText("");
                            lastNameLayout.setVisibility(View.VISIBLE);
                            break;
                        }
                        case "Corporate without assignee": {
                            edtCorporate.setText("");
                            corporateNameLayout.setVisibility(View.VISIBLE);
                            edtFirstName.setText("");
                            firstNameLayout.setVisibility(View.GONE);
                            edtMiddleName.setText("");
                            middleNameLayout.setVisibility(View.GONE);
                            edtLastName.setText("");
                            lastNameLayout.setVisibility(View.GONE);
                            break;
                        }
                        case "Corporate with assignee": {
                            edtCorporate.setText("");
                            corporateNameLayout.setVisibility(View.VISIBLE);
                            edtFirstName.setText("");
                            firstNameLayout.setVisibility(View.VISIBLE);
                            edtMiddleName.setText("");
                            middleNameLayout.setVisibility(View.VISIBLE);
                            edtLastName.setText("");
                            lastNameLayout.setVisibility(View.VISIBLE);
                            break;
                        }
                    }

                    if (paramountQueue.getCorporateName() != null) {
                        edtCorporate.setText(paramountQueue.getCorporateName());
                    }

                    if (paramountQueue.getFirstName() != null) {
                        edtFirstName.setText(paramountQueue.getFirstName());
                    }

                    if (paramountQueue.getMiddleName() != null) {
                        edtMiddleName.setText(paramountQueue.getMiddleName());
                    }

                    if (paramountQueue.getLastName() != null) {
                        edtLastName.setText(paramountQueue.getLastName());
                    }

                } else {

                    edtType.setText("Individual");

                }

            }

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                hideKeyboard(getViewContext());

                String strsql = "UPDATE " + DatabaseHandler.PARAMOUNT_QUEUE + " SET EmailAddress=?, Type=?, FirstName=?, MiddleName=?, LastName=?, CorporateName=? WHERE RequestID=?";

                String[] whereArgs = new String[]{edtEmailAdress.getText().toString().trim(),
                        edtType.getText().toString().trim(),
                        edtFirstName.getText().toString().trim(),
                        edtMiddleName.getText().toString().trim(),
                        edtLastName.getText().toString().trim(),
                        ".",
                        requestID};

                mdb.updateParamountQueue(mdb, strsql, whereArgs);

                Bundle args = new Bundle();
                args.putString("currentyear", currentyear);
                args.putString("generatedid", requestID);

                ((ParamountInsuranceActivity) getViewContext()).displayView(3, args);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext: {

                switch (edtType.getText().toString()) {
                    case "Individual": {

                        if (CommonFunctions.isValidEmail(edtEmailAdress.getText().toString().trim()) &&
                                edtEmailAdress.getText().toString().trim().length() > 0 &&
                                edtType.getText().toString().trim().length() > 0 &&
                                edtFirstName.getText().toString().trim().length() > 0 &&
                                edtLastName.getText().toString().trim().length() > 0) {

                            String strsql = "UPDATE " + DatabaseHandler.PARAMOUNT_QUEUE + " SET EmailAddress=?, Type=?, FirstName=?, MiddleName=?, LastName=?, CorporateName=? WHERE RequestID=?";

                            String[] whereArgs = new String[]{edtEmailAdress.getText().toString().trim(),
                                    edtType.getText().toString().trim(),
                                    edtFirstName.getText().toString().trim(),
                                    edtMiddleName.getText().toString().trim(),
                                    edtLastName.getText().toString().trim(),
                                    ".",
                                    requestID};

                            mdb.updateParamountQueue(mdb, strsql, whereArgs);

                            Bundle args = new Bundle();
                            args.putString("currentyear", currentyear);
                            args.putString("generatedid", requestID);

                            ((ParamountInsuranceActivity) getViewContext()).displayView(5, args);

                        } else {

                            if (!CommonFunctions.isValidEmail(edtEmailAdress.getText().toString())) {
                                showError("Please input a valid email address.");
                            } else {
                                showError("Please input all required fields.");
                            }

                        }

                        break;
                    }
                    case "Corporate without assignee": {

                        if (CommonFunctions.isValidEmail(edtEmailAdress.getText().toString().trim()) &&
                                edtCorporate.getText().toString().trim().length() > 0) {

                            String strsql = "UPDATE " + DatabaseHandler.PARAMOUNT_QUEUE + " SET EmailAddress=?, Type=?, FirstName=?, MiddleName=?, LastName=?, CorporateName=? WHERE RequestID=?";

                            String[] whereArgs = new String[]{edtEmailAdress.getText().toString().trim(),
                                    edtType.getText().toString().trim(),
                                    ".",
                                    ".",
                                    ".",
                                    edtCorporate.getText().toString().trim(),
                                    requestID};

                            mdb.updateParamountQueue(mdb, strsql, whereArgs);

                            Bundle args = new Bundle();
                            args.putString("currentyear", currentyear);
                            args.putString("generatedid", requestID);

                            ((ParamountInsuranceActivity) getViewContext()).displayView(5, args);

                        } else {

                            if (!CommonFunctions.isValidEmail(edtEmailAdress.getText().toString())) {
                                showError("Please input a valid email address.");
                            } else {
                                showError("Please input all required fields.");
                            }

                        }

                        break;
                    }
                    case "Corporate with assignee": {

                        if (CommonFunctions.isValidEmail(edtEmailAdress.getText().toString().trim()) &&
                                edtEmailAdress.getText().toString().trim().length() > 0 &&
                                edtType.getText().toString().trim().length() > 0 &&
                                edtCorporate.getText().toString().trim().length() > 0 &&
                                edtFirstName.getText().toString().trim().length() > 0 &&
                                edtLastName.getText().toString().trim().length() > 0) {

                            String strsql = "UPDATE " + DatabaseHandler.PARAMOUNT_QUEUE + " SET EmailAddress=?, Type=?, CorporateName=?, FirstName=?, MiddleName=?, LastName=? WHERE RequestID=?";

                            String[] whereArgs = new String[]{edtEmailAdress.getText().toString().trim(),
                                    edtType.getText().toString().trim(),
                                    edtCorporate.getText().toString().trim(),
                                    edtFirstName.getText().toString().trim(),
                                    edtMiddleName.getText().toString().trim(),
                                    edtLastName.getText().toString().trim(),
                                    requestID};

                            mdb.updateParamountQueue(mdb, strsql, whereArgs);

                            Bundle args = new Bundle();
                            args.putString("currentyear", currentyear);
                            args.putString("generatedid", requestID);

                            ((ParamountInsuranceActivity) getViewContext()).displayView(5, args);

                        } else {

                            if (!CommonFunctions.isValidEmail(edtEmailAdress.getText().toString())) {
                                showError("Please input a valid email address.");
                            } else {
                                showError("Please input all required fields.");
                            }

                        }

                        break;
                    }
                }

                break;
            }
            case R.id.edtType: {

                openTypeDialog();

                break;
            }
        }
    }

    private void openTypeDialog() {
        if (mdb != null) {
            MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                    .title("Type")
                    .items(arrType)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int position, CharSequence text) {

                            edtType.setText(text.toString());

                            switch (text.toString()) {
                                case "Individual": {
                                    edtCorporate.setText("");
                                    corporateNameLayout.setVisibility(View.GONE);
                                    edtFirstName.setText("");
                                    firstNameLayout.setVisibility(View.VISIBLE);
                                    edtMiddleName.setText("");
                                    middleNameLayout.setVisibility(View.VISIBLE);
                                    edtLastName.setText("");
                                    lastNameLayout.setVisibility(View.VISIBLE);
                                    break;
                                }
                                case "Corporate without assignee": {
                                    edtCorporate.setText("");
                                    corporateNameLayout.setVisibility(View.VISIBLE);
                                    edtFirstName.setText("");
                                    firstNameLayout.setVisibility(View.GONE);
                                    edtMiddleName.setText("");
                                    middleNameLayout.setVisibility(View.GONE);
                                    edtLastName.setText("");
                                    lastNameLayout.setVisibility(View.GONE);
                                    break;
                                }
                                case "Corporate with assignee": {
                                    edtCorporate.setText("");
                                    corporateNameLayout.setVisibility(View.VISIBLE);
                                    edtFirstName.setText("");
                                    firstNameLayout.setVisibility(View.VISIBLE);
                                    edtMiddleName.setText("");
                                    middleNameLayout.setVisibility(View.VISIBLE);
                                    edtLastName.setText("");
                                    lastNameLayout.setVisibility(View.VISIBLE);
                                    break;
                                }
                            }

                        }
                    })
                    .show();

            V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());
        }
    }

    private class QuantityTextWatcher implements TextWatcher {
        private EditText editText;

        public QuantityTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s.length() > 0) {

                if (editText != null) {

//                    if (!CommonFunctions.isValidEmail(edtEmailAdress.getText().toString())) {
//                        edtEmailAdress.removeTextChangedListener(this);
//                        emailErrorMessage.setText("Invalid Email Address");
//                        emailErrorMessage.setVisibility(View.VISIBLE);
//                        edtEmailAdress.addTextChangedListener(this);
//                    } else {
//                        emailErrorMessage.setVisibility(View.GONE);
//                    }

                }

            } else {

                emailErrorMessage.setVisibility(View.GONE);

            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {

                if (editText != null) {

                    if (!CommonFunctions.isValidEmail(edtEmailAdress.getText().toString())) {
                        edtEmailAdress.removeTextChangedListener(this);
                        emailErrorMessage.setText("Invalid Email Address");
                        emailErrorMessage.setVisibility(View.VISIBLE);
                        edtEmailAdress.addTextChangedListener(this);
                    } else {
                        emailErrorMessage.setVisibility(View.GONE);
                    }

                }

            } else {
                emailErrorMessage.setVisibility(View.GONE);
            }
        }


    }
}
