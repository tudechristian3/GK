package com.goodkredit.myapplication.fragments.paramount;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.paramount.ParamountInsuranceActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.ParamountQueue;
import com.goodkredit.myapplication.database.DatabaseHandler;

/**
 * Created by User-PC on 11/29/2017.
 */

public class VehicleDetailsFragment extends BaseFragment implements View.OnClickListener {
    private DatabaseHandler mdb;

    private String currentyear;
    private String requestID;

    private EditText edtMVFileNumber;
    private EditText edtPlateNumber;
    private EditText edtEngineMotorNumber;
    private EditText edtSerialChassisNumber;

    private Button btnNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_details, container, false);

        setHasOptionsMenu(true);

        try {

            init(view);

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mdb != null) {

            ParamountQueue paramountQueue = mdb.getParamountQueue(mdb);
            if (paramountQueue != null) {

                if (paramountQueue.getMVFileNumber() != null) {
                    edtMVFileNumber.setText(paramountQueue.getMVFileNumber());
                }

                if (paramountQueue.getPlateNumber() != null) {
                    edtPlateNumber.setText(paramountQueue.getPlateNumber());
                }

                if (paramountQueue.getEngineMotorNumber() != null) {
                    edtEngineMotorNumber.setText(paramountQueue.getEngineMotorNumber());
                }

                if (paramountQueue.getSerial() != null) {
                    edtSerialChassisNumber.setText(paramountQueue.getSerial());
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

                String strsql = "UPDATE " + DatabaseHandler.PARAMOUNT_QUEUE + " SET MVFileNumber=?, PlateNumber=?, EngineMotorNumber=?, Serial=? WHERE RequestID=?";

                String[] whereArgs = new String[]{edtMVFileNumber.getText().toString().replaceAll("-", "").trim(),
                        edtPlateNumber.getText().toString().trim(),
                        edtEngineMotorNumber.getText().toString().trim(),
                        edtSerialChassisNumber.getText().toString().trim(),
                        requestID};

                mdb.updateParamountQueue(mdb, strsql, whereArgs);

                Bundle args = new Bundle();
                args.putString("currentyear", currentyear);
                args.putString("generatedid", requestID);

                ((ParamountInsuranceActivity) getViewContext()).displayView(2, args);

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init(View view) {
        btnNext = (Button) view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        edtMVFileNumber = (EditText) view.findViewById(R.id.edtMVFileNumber);
        edtPlateNumber = (EditText) view.findViewById(R.id.edtPlateNumber);
        edtEngineMotorNumber = (EditText) view.findViewById(R.id.edtEngineMotorNumber);
        edtSerialChassisNumber = (EditText) view.findViewById(R.id.edtSerialChassisNumber);

    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        currentyear = getArguments().getString("currentyear");
        requestID = getArguments().getString("generatedid");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext: {

                if (edtMVFileNumber.getText().toString().trim().length() > 0 &&
                        edtPlateNumber.getText().toString().trim().length() > 0 &&
                        edtEngineMotorNumber.getText().toString().trim().length() > 0 &&
                        edtSerialChassisNumber.getText().toString().trim().length() > 0) {

                    String strsql = "UPDATE " + DatabaseHandler.PARAMOUNT_QUEUE + " SET MVFileNumber=?, PlateNumber=?, EngineMotorNumber=?, Serial=? WHERE RequestID=?";

                    String[] whereArgs = new String[]{edtMVFileNumber.getText().toString().replaceAll("-", "").trim(),
                            edtPlateNumber.getText().toString().trim(),
                            edtEngineMotorNumber.getText().toString().trim(),
                            edtSerialChassisNumber.getText().toString().trim(),
                            requestID};

                    mdb.updateParamountQueue(mdb, strsql, whereArgs);

                    Bundle args = new Bundle();
                    args.putString("currentyear", currentyear);
                    args.putString("generatedid", requestID);

                    ((ParamountInsuranceActivity) getViewContext()).displayView(4, args);

                } else {

                    showError("Please input all required fields.");

                }

                break;
            }
        }
    }
}
