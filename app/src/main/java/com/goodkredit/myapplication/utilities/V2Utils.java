package com.goodkredit.myapplication.utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.security.MessageDigest;
import java.text.DecimalFormat;

/**
 * Created by Ban_Lenovo on 7/29/2017.
 */

public class V2Utils {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int NO_ACTIVE_NETWORK = 0;

    public static final String HELVETICA_CONDENSED = "fonts/Helvetica LT 57 Condensed_0.ttf";
    public static final String HELVETICA_BLACK_CONDENSED = "fonts/Helvetica LT 97 Condensed.ttf";
    public static final String ROBOTO_BOLD = "fonts/Roboto-Bold.ttf";
    public static final String ROBOTO_MEDIUM = "fonts/Roboto-Medium.ttf";
    public static final String ROBOTO_REGULAR = "fonts/Roboto-Regular.ttf";
    public static final String ROBOTO_CONDENSED_BOLD = "fonts/RobotoCondensed-Bold.ttf";
    public static final String ROBOTO_CONDENSED_REGULAR = "fonts/RobotoCondensed-Regular.ttf";
    public static final String ROBOTO_ITALIC = "fonts/Roboto-Italic.ttf";

    public static int getConnectivityStatus(Context context) {

        int NETWORK = 0;

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Logger.debug("activeNetwork", "wifi");
                return TYPE_WIFI;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Logger.debug("activeNetwork", "mobile");
                return TYPE_MOBILE;
            } else {
                Logger.debug("activeNetwork", "no active");
                return NO_ACTIVE_NETWORK;
            }
        } else {
            Logger.debug("activeNetwork", "bull");
            return NO_ACTIVE_NETWORK;
        }
    }

    public static String currencyFormatter(String strAmount) {
        String pattern = "#,###,##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        return decimalFormat.format(Double.parseDouble(strAmount));
    }

    public static String replaceEscapeData(String string) {

        String result = string;

        if (string != null && !string.equals("")) {
            String tempstr = result;
            tempstr = tempstr.replaceAll("&AMP;", "&");
            tempstr = tempstr.replaceAll("&amp;", "&");
            tempstr = tempstr.replaceAll("&#39;", "'");
            tempstr = tempstr.replaceAll("#39;", "'");
            tempstr = tempstr.replaceAll("&#47;", "/");
            tempstr = tempstr.replaceAll("#47;", "/");
            tempstr = tempstr.replaceAll("&#GT;", ">");
            tempstr = tempstr.replaceAll("&#gt;", ">");
            tempstr = tempstr.replaceAll("&#LT;", "<");
            tempstr = tempstr.replaceAll("&#lt;", "<");
            tempstr = tempstr.replaceAll("&#40", "(");
            tempstr = tempstr.replaceAll("&#41;", ")");
            tempstr = tempstr.replaceAll("&#44;", ",");

            result = tempstr;
        }

        return result;
    }

    public static String getSha1Hex(String clearString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(clearString.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes) {
                buffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return buffer.toString();
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return null;
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    //SET TYPEFACE
    public static SpannableString setTypeFace(Context context, String fontName, String string) {


        String mString = string;
        SpannableString spannableString = null;
        String FONT_NAME = fontName;

        try {
            if (mString != null || !mString.isEmpty()) {
                Typeface font = Typeface.createFromAsset(context.getAssets(), FONT_NAME);
                spannableString = new SpannableString(string);
                spannableString.setSpan(new V2CustomTypeFaceSpan("", font), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannableString = SpannableString.valueOf(mString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spannableString;
    }

    public static Typeface setFontInput(Context context, String fontName) {
        Typeface typeface = null;
        try {
            typeface = Typeface.createFromAsset(context.getAssets(), fontName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeface;
    }

    public static void overrideFonts(final Context context, String fontName, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, fontName, child);
                }
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }
}
