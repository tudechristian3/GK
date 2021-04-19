package com.goodkredit.myapplication.base;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.goodkredit.myapplication.BuildConfig;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.GlobalToast;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Ban_Lenovo on 5/5/2017.
 */

public class BaseFragment extends Fragment {

    protected Context mContext;

    public CountDownTimer mLoaderTimer;

    public String imei = "";
    public String userid = "";
    public String session = "";
    public String authcode = "";
    public String borrowerid = "";

    private DatabaseHandler db;

    //    public Uri fileUri = null;
    protected static final int SKY_REQUEST_CAMERA = 100;
    protected static final int REQUEST_CAMERA = 5;
    protected static final int SELECT_FILE = 4;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Displays progress dialog if instantiated
     */
    public void showProgressDialog(String titleID, String messageId) {
        ((BaseActivity) getActivity()).showProgressDialog(mContext, titleID, messageId);
    }

    /**
     * Hides progress dialog if instantiated
     */
    public void hideProgressDialog() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).hideProgressDialog();
        }
    }

    public void hideSoftKeyboard() {
        ((BaseActivity) getActivity()).hideSoftKeyboard();
    }


    public void startLoaderAnimation() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).startLoaderAnimation();
        }
    }

    protected Context getViewContext() {
        return mContext;
    }

    public void showError(String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getViewContext())).showError(content);
        } else {
            ((BaseActivity) getViewContext()).showError(content);
        }
    }

    public void showError() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getViewContext())).showError();
        } else {
            ((BaseActivity) getViewContext()).showError();
        }
    }

    //-----------------GLOBAL TOAST MESSAGES--------------------
    public void showToast(String message, GlobalToastEnum globalToastEnum) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ((BaseActivity) Objects.requireNonNull(getViewContext())).showToast(message, globalToastEnum);
            } else {
                ((BaseActivity) (getViewContext())).showToast(message, globalToastEnum);
            }
        } catch (Exception e) {
            e.printStackTrace();
            GlobalToast.displayToast(getViewContext(), message, globalToastEnum);
        }
    }

    public void showErrorToast(String content) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ((BaseActivity) Objects.requireNonNull(getActivity())).showErrorToast(content);
            } else {
                ((BaseActivity) getActivity()).showErrorToast(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
            GlobalToast.displayToast(getViewContext(), content, GlobalToastEnum.ERROR);
        }
    }

    public void showErrorToast() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ((BaseActivity) Objects.requireNonNull(getActivity())).showErrorToast();
            } else {
                ((BaseActivity) getActivity()).showErrorToast();
            }
        } catch (Exception e) {
            e.printStackTrace();
            GlobalToast.displayToast(getViewContext(), "An error has occurred. Please try again.", GlobalToastEnum.ERROR);
        }
    }

    public void showWarningToast(String content) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ((BaseActivity) Objects.requireNonNull(getActivity())).showWarningToast(content);
            } else {
                ((BaseActivity) getActivity()).showWarningToast(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
            GlobalToast.displayToast(getViewContext(),content , GlobalToastEnum.WARNING);
        }
    }

    public void showWarningToast() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ((BaseActivity) Objects.requireNonNull(getActivity())).showWarningToast();
            } else {
                ((BaseActivity) getActivity()).showWarningToast();
            }
        } catch (Exception e) {
            e.printStackTrace();
            GlobalToast.displayToast(getViewContext(), "Seems you are not connected to the internet. " +
                    "Please check your connection and try again. Thank you.", GlobalToastEnum.WARNING);
        }
    }

    public void showNoInternetToast() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ((BaseActivity) Objects.requireNonNull(getActivity())).showNoInternetToast();
            } else {
                ((BaseActivity) getActivity()).showNoInternetToast();
            }
        } catch (Exception e) {
            e.printStackTrace();
            GlobalToast.displayToast(getViewContext(), "Oops! We failed to connect to the service. " +
                    "Please check your internet connection and try again.", GlobalToastEnum.WARNING);
        }
    }

    //-----------------------------------------COMMON GLOBAL DIALOGS----------------------------------
    //GLOBAL ERROR DIALOGS
    public void showErrorGlobalDialogs(String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getViewContext())).showErrorGlobalDialogs(content);
        } else {
            ((BaseActivity) getViewContext()).showErrorGlobalDialogs(content);
        }
    }

    public void showErrorGlobalDialogs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getViewContext())).showErrorGlobalDialogs();
        } else {
            ((BaseActivity) getViewContext()).showErrorGlobalDialogs();
        }
    }

    //GLOBAL WARNING DIALOGS
    public void showWarningGlobalDialogs(String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getViewContext())).showWarningGlobalDialogs(content);
        } else {
            ((BaseActivity) getViewContext()).showWarningGlobalDialogs(content);
        }
    }

    public void showWarningGlobalDialogs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getViewContext())).showWarningGlobalDialogs();
        } else {
            ((BaseActivity) getViewContext()).showWarningGlobalDialogs();
        }
    }

    //----------------------------------END OF COMMON GLOBAL DIALOGS---------------------------

    public void setUpProgressLoader() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getActivity())).setUpProgressLoader();
        } else {
            ((BaseActivity) getActivity()).setUpProgressLoader();
        }
    }

    public void setUpProgressLoaderMessageDialog(String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getActivity())).setUpProgressLoaderMessageDialog(message);
        } else {
            ((BaseActivity) getActivity()).setUpProgressLoaderMessageDialog(message);
        }
    }

    public void setUpProgressLoaderDismissDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getActivity())).setUpProgressLoaderDismissDialog();
        } else {
            ((BaseActivity) getActivity()).setUpProgressLoaderDismissDialog();
        }
    }

    public void showGlobalDialogs(String messagecontent, String buttonmessage, ButtonTypeEnum buttonTypeEnum,
                                  boolean isReseller, boolean ishtmlcode, DialogTypeEnum dialogTypeEnum) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getActivity())).showGlobalDialogs(messagecontent, buttonmessage,
                    buttonTypeEnum, isReseller, ishtmlcode, dialogTypeEnum);
        } else {
            ((BaseActivity) getActivity()).showGlobalDialogs(messagecontent, buttonmessage, buttonTypeEnum,
                    isReseller, ishtmlcode, dialogTypeEnum);
        }
    }

    public void showGlobalDialogs(String messagecontent, String buttonmessage, ButtonTypeEnum buttonTypeEnum,
                                  boolean isReseller, boolean ishtmlcode, DialogTypeEnum dialogTypeEnum, boolean redirects) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getActivity())).showGlobalDialogs(messagecontent, buttonmessage,
                    buttonTypeEnum, isReseller, ishtmlcode, dialogTypeEnum, redirects);
        } else {
            ((BaseActivity) getActivity()).showGlobalDialogs(messagecontent, buttonmessage, buttonTypeEnum, isReseller,
                    ishtmlcode, dialogTypeEnum, redirects);
        }
    }

    public void showGlobalDialogs(String messagecontent, String buttonmessage, ButtonTypeEnum buttonTypeEnum,
                                  boolean isReseller, boolean ishtmlcode, DialogTypeEnum dialogTypeEnum, boolean redirects,
                                  Fragment fragment) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getActivity())).showGlobalDialogs(messagecontent, buttonmessage,
                    buttonTypeEnum, isReseller, ishtmlcode, dialogTypeEnum, redirects, fragment);
        } else {
            ((BaseActivity) getActivity()).showGlobalDialogs(messagecontent, buttonmessage,
                    buttonTypeEnum, isReseller, ishtmlcode, dialogTypeEnum, redirects, fragment);
        }
    }

    //=======================================================
    //CALCULATE EXPECTED LIMIT
    //length: size of array list
    //limitSize: limit value for each call
    //=======================================================
    public int getLimit(final double length, final double limitSize) {
        return ((BaseActivity) getActivity()).getLimit(length, limitSize);
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public String capitalizeWord(String word) {
        return ((BaseActivity) getActivity()).capitalizeWord(word);
    }

    public String getMobileNumber(String number) {
        return ((BaseActivity) getActivity()).getMobileNumber(number);
    }

    public Uri getOutputMediaFileUri(int type) {

        File photoFile = getOutputMediaFile(1);

        Uri photoURI = FileProvider.getUriForFile(getViewContext(),
                BuildConfig.APPLICATION_ID.concat(".provider"),
                photoFile);

        return photoURI;
    }

    public File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "GoodKredit");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Logger.debug("GoodKredit", "Oops! Failed create "
                        + "GoodKredit" + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    public Bitmap getImageBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getViewContext().getContentResolver().openInputStream(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void cropImage(Uri uri) {
        CropImage.activity(uri)
                .setActivityTitle("Crop Image")
                .setAutoZoomEnabled(true)
                .setBorderCornerThickness(0)
                .setOutputCompressQuality(75)
                .setActivityMenuIconColor(R.color.colorwhite)
                .setAllowRotation(true)
                .setAllowFlipping(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setFixAspectRatio(true)
                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                .start(getViewContext(), this);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Context inContext, Uri uri) {
        Cursor cursor = inContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    /**
     * Gets the state of Airplane Mode.
     *
     * @param context
     * @return true if enabled.
     */
    public boolean isAirplaneModeOn(Context context) {
        return ((BaseActivity) getActivity()).isAirplaneModeOn(context);
    }

    /**
     * Gets the state of GPS Mode.
     *
     * @return true if enabled.
     */
    public boolean isGPSModeOn() {
        return ((BaseActivity) getActivity()).isGPSModeOn();
    }

    public boolean isGPSEnabled(Context context) {
        return ((BaseActivity) getActivity()).isGPSEnabled(context);
    }

    public void showGPSDisabledAlertToUser() {
        ((BaseActivity) getActivity()).showGPSDisabledAlertToUser();
    }

    public boolean checkIfAddressIsWithinRadius(double firstlat, double firstlong, double secondlat, double secondlong, double radius) {
        return ((BaseActivity) getActivity()).checkIfAddressIsWithinRadius(firstlat, firstlong, secondlat, secondlong, radius);
    }

    public void log(String message) {
        ((BaseActivity) getActivity()).log(message);
    }

    //LOGOUT
    public void showAutoLogoutDialog(String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((BaseActivity) Objects.requireNonNull(getViewContext())).showAutoLogoutDialog(message);
        } else {
            ((BaseActivity) getViewContext()).showAutoLogoutDialog(message);
        }
    }

    //PROCEED TO COOP HOME
    public void proceedtoCoopHome() {
        ((BaseActivity) getViewContext()).proceedtoCoopHome();
    }

}
