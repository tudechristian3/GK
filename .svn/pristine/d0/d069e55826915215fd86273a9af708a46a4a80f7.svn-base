package com.goodkredit.myapplication.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Voucher;

import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;

import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalSnackEnum;
import com.goodkredit.myapplication.utilities.CustomTypefaceSpan;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.scottyab.aescrypt.AESCrypt;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;

/**
 * Created by user on 8/2/2016.
 */
public class CommonFunctions {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int NO_ACTIVE_NETWORK = 0;
    public static ProgressDialog mDialog;

    CommonVariables cv;

    public static int getConnectivityStatus(Context context) {

        int NETWORK = 0;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (activeNetwork != null) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    Logger.debug("activeNetwork", "wifi");
                    NETWORK = TYPE_WIFI;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Logger.debug("activeNetwork", "mobile");
                    NETWORK = TYPE_MOBILE;
                } else {
                    Logger.debug("activeNetwork", "no active");
                    NETWORK = NO_ACTIVE_NETWORK;
                }
            } else {
                Logger.debug("activeNetwork", "bull");
                NETWORK = NO_ACTIVE_NETWORK;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NETWORK;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static String convertDate(String dateval) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat formatOutput = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa");

            Date date = formatInput.parse(dateval);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public final static String convertDate1(String dateval) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat formatOutput = new SimpleDateFormat("EEE, MMM d, yyyy");

            Date date = formatInput.parse(dateval);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public final static String convertDateDDMMYYFormat(String dateval) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat formatOutput = new SimpleDateFormat("dd/MM/yy hh:mm aaa");

            Date date = formatInput.parse(dateval);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public final static String convertDateToDayoftheWeek(String dateval) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat formatOutput = new SimpleDateFormat("EEEE");

            Date date = formatInput.parse(dateval);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public final static String convertDateWithoutTime(String dateval) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat formatOutput = new SimpleDateFormat("yyyy-MM-dd");

            Date date = formatInput.parse(dateval);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public final static String convertDateOnlyWithoutTime(String dateval) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat formatOutput = new SimpleDateFormat("MMMM dd, yyyy");

            Date date = formatInput.parse(dateval);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public final static String getMonthYear(String dateval) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat formatOutput = new SimpleDateFormat("MMM yyyy");

            Date date = formatInput.parse(dateval);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString.toUpperCase();
    }

    public final static String getYearFromDate(String dateval) {
        String year = "";
        try {
            Calendar calender = Calendar.getInstance();
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            // SimpleDateFormat formatOutput = new SimpleDateFormat("MMM. dd, yyyy hh:mm aaa");

            Date date = formatInput.parse(dateval);
            calender.setTimeInMillis(date.getTime());
            year = String.valueOf(calender.get(Calendar.YEAR));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return year;
    }

    public final static String getMonthFromDate(String dateval) {
        String mon = "";
        try {
            Calendar calender = Calendar.getInstance();
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            // SimpleDateFormat formatOutput = new SimpleDateFormat("MMM. dd, yyyy hh:mm aaa");

            Date date = formatInput.parse(dateval);
            calender.setTimeInMillis(date.getTime());
            if ((calender.get(Calendar.MONTH) + 1) < 10)
                mon = "0" + String.valueOf(calender.get(Calendar.MONTH) + 1);
            else
                mon = String.valueOf(calender.get(Calendar.MONTH) + 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mon;
    }

    public static String dateFormatForSQLITE(String dateVal) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat formatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = formatInput.parse(dateVal);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static long getLongFromDate(String dateval) {
        long dateLong = 0;
        String dateString;
        try {
            SimpleDateFormat formatinput = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa");
            SimpleDateFormat formatOutput = new SimpleDateFormat("MMMM dd, yyyy");
            Date date = formatinput.parse(dateval);
            dateString = formatOutput.format(date);
            Date date2 = formatOutput.parse(dateString);
            dateLong = date2.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateLong;
    }

    public static Calendar getCalendarFromDate(String dateVal) {
        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(getLongFromDate(convertDate(dateVal)));
        Log.v("cal", String.valueOf(cal.getTimeInMillis()));

        return cal;
    }

    public static String getDateFromDateTime(String dateVal) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa");
            SimpleDateFormat formatOutput = new SimpleDateFormat("MMMM dd, yyyy");

            Date date = formatInput.parse(dateVal);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString.toUpperCase();
    }

    public static String getDateTimeFromDateTime(String dateVal) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa");
            SimpleDateFormat formatOutput = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");

            Date date = formatInput.parse(dateVal);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString.toUpperCase();
    }

    public static String getDateFromDateTimeDDMMYYFormat(String dateVal) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("dd/MM/yy hh:mm");
            SimpleDateFormat formatOutput = new SimpleDateFormat("MM/dd/yyyy");

            Date date = formatInput.parse(dateVal);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString.toUpperCase();
    }

    public static String getDateFromSkycableDateTime(String dateVal) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat formatOutput = new SimpleDateFormat("MMMM dd, yyyy");

            Date date = formatInput.parse(dateVal);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public final static String getYearFromSkycableDateTime(String dateval) {
        String year = "";
        try {
            Calendar calender = Calendar.getInstance();
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            // SimpleDateFormat formatOutput = new SimpleDateFormat("MMM. dd, yyyy hh:mm aaa");

            Date date = formatInput.parse(dateval);
            calender.setTimeInMillis(date.getTime());
            year = String.valueOf(calender.get(Calendar.YEAR));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return year;
    }

    public final static String getMonthFromSkycableDateTime(String dateval) {
        String mon = "";
        try {
            Calendar calender = Calendar.getInstance();
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            // SimpleDateFormat formatOutput = new SimpleDateFormat("MMM. dd, yyyy hh:mm aaa");

            Date date = formatInput.parse(dateval);
            calender.setTimeInMillis(date.getTime());
            if ((calender.get(Calendar.MONTH) + 1) < 10)
                mon = "0" + String.valueOf(calender.get(Calendar.MONTH) + 1);
            else
                mon = String.valueOf(calender.get(Calendar.MONTH) + 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mon;
    }

    public static String getDateShortenFromDateTime(String dateVal) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa");
            SimpleDateFormat formatOutput = new SimpleDateFormat("MMM dd, yyyy");

            Date date = formatInput.parse(dateVal);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString.toUpperCase();
    }

    public static String getTimeFromDateTime(String dateVal) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa");
            SimpleDateFormat formatOutput = new SimpleDateFormat("hh:mm aaa");

            Date date = formatInput.parse(dateVal);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString.toUpperCase();
    }

    public static String getTimeFromSkycableDateTime(String dateVal) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat formatOutput = new SimpleDateFormat("hh:mm aaa");

            Date date = formatInput.parse(dateVal);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static String discountFormatter(String number) {
        String pattern = "#,###,##0.0000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        return decimalFormat.format(Double.parseDouble(number));
    }

    public static String currencyFormatter(String number) {
        String pattern = "#,###,##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(Double.parseDouble(number));
    }

    public static String pointsFormatter(String number) {
        String pattern = "#,###,##0";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        return decimalFormat.format(Double.parseDouble(number));
    }

    public static String singleDigitFormatter(String number) {
        String pattern = "#,###,##0.0";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        return decimalFormat.format(Double.parseDouble(number));
    }

    public static String wholenumberFormatter(String number) {
        String pattern = "######0";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        return decimalFormat.format(Double.parseDouble(number));
    }

    public static String decimalFormatter(String number) {
        String pattern = "######0.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(Double.parseDouble(number));
    }

    public static long convertStringDateToLong(String dateTime) {
        try {
            DateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            Date d = f.parse(dateTime);
            long milliseconds = d.getTime();
            return milliseconds;
        } catch (Exception e) {
            return 0;
        }

    }

    public static String convertLongToStringDate(String dateTime) {
        String dateString = null;
        Long l = Long.parseLong(dateTime);
        try {
            Date date = new Date(l);
            DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            dateString = sdf.format(date);
        } catch (Exception e) {
            return dateString;
        }
        return dateString;
    }

    public static void showDialog(Context context, String Title, String message, Boolean bool) {
        try {
            mDialog = new ProgressDialog(context);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setMessage(message);
            mDialog.show();

        } catch (Exception e) {
            hideDialog();
            Logger.debug("okhttp", "SHOWDIALOG : " + e.getMessage());
        }
    }

    public static void hideDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public static void log(String message) {
        for (int i = 0, length = message.length(); i < length; i++) {
            int newline = message.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + 4000);
                Logger.debug("OkHttp", message.substring(i, end));
                i = end;
            } while (i < newline);
        }
    }

    public static String POST(String url1, String json) {

        if (CommonVariables.isHttpLogs) {
            log(url1 + " === " + json);
        }

        String result = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url1);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoOutput(true);
            con.setConnectTimeout(120000);
            con.setReadTimeout(120000);


            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(json);
            writer.flush();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            line = sb.toString();
            result = line.trim();

            if (CommonVariables.isHttpLogs) {
                log(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Logger.debug("httppost", "Exception" + e.toString());
            result = null;
        }

        return result;
    }

    public static String POST1(String url, String json) {
        Logger.debug("URL && JSON", url + " === " + json);
        InputStream inputStream = null;
        String result = null;
        try {
            HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection = 10000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            int timeoutSocket = 10000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);


            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient(httpParameters);

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
            Logger.debug("REQ RESPONSE", "POST: " + String.valueOf(httpResponse.getStatusLine().getReasonPhrase()));
            Logger.debug("REQ RESPONSE", "POST: " + String.valueOf(httpResponse.getStatusLine().getStatusCode()));

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
                result = null;
            }

        } catch (SocketTimeoutException e) {
            Logger.debug("httppost", "SocketTimeoutException" + e.toString());
            e.printStackTrace();
            result = null;
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            Logger.debug("httppost", "ConnectTimeoutException" + e.toString());
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.debug("httppost", "Exception" + e.toString());
            result = null;
        }


        // 11. return result
        return result;


    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
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

    public final static String replaceEscapeData(String xstring) {
        String result = xstring;

        if (xstring != null && !xstring.equals("")) {
            String tempstr = result;
            //HTML ENTITIES
            tempstr = tempstr.replaceAll("&AMP;", "&");
            tempstr = tempstr.replaceAll("&amp;", "&");
            tempstr = tempstr.replaceAll("AMP;", "&");
            tempstr = tempstr.replaceAll("amp;", "&");
            tempstr = tempstr.replaceAll("&#39;", "'");
            tempstr = tempstr.replaceAll("#39;", "'");
            tempstr = tempstr.replaceAll("&#47;", "/");
            tempstr = tempstr.replaceAll("#47;", "/");
            tempstr = tempstr.replaceAll("&#GT;", ">");
            tempstr = tempstr.replaceAll("&#gt;", ">");
            tempstr = tempstr.replaceAll("&#LT;", "<");
            tempstr = tempstr.replaceAll("&#lt;", "<");
            tempstr = tempstr.replaceAll("&#40;", "(");
            tempstr = tempstr.replaceAll("&#41;", ")");
            tempstr = tempstr.replaceAll("&#44;", ",");
            tempstr = tempstr.replaceAll("#44;", ",");

            //UTF-8 ENCODING ISSUES
            tempstr = tempstr.replaceAll("â€š", "‚");
            tempstr = tempstr.replaceAll("Æ’", "ƒ");
            tempstr = tempstr.replaceAll("â€ž", "„");
            tempstr = tempstr.replaceAll("â€¦", "…");
            tempstr = tempstr.replaceAll("â€", "†");
            tempstr = tempstr.replaceAll("â€¡", "‡");
            tempstr = tempstr.replaceAll("Ë†", "ˆ");
            tempstr = tempstr.replaceAll("â€˜", "‘");
            tempstr = tempstr.replaceAll("â€™", "’");
            tempstr = tempstr.replaceAll("â€œ", "“");
            tempstr = tempstr.replaceAll("â€", "”");
            tempstr = tempstr.replaceAll("â€¢", "•");
            tempstr = tempstr.replaceAll("â€“", "–");
            tempstr = tempstr.replaceAll("â€”", "—");
            tempstr = tempstr.replaceAll("â„¢", "™");

            //CUSTOM
            tempstr = tempstr.replaceAll("Ã", "Ñ");
            tempstr = tempstr.replaceAll("Ã±", "ñ");
            tempstr = tempstr.replaceAll("&\u200CNtilde;", "Ñ");
            tempstr = tempstr.replaceAll("Ntilde;", "Ñ");
            tempstr = tempstr.replaceAll("&\u200Cntilde;", "ñ");
            tempstr = tempstr.replaceAll("ntilde;", "ñ");
            tempstr = tempstr.replaceAll("\u009D", "");

            result = tempstr;
        }

        return result;
    }

    public static String getMonthName(int month) {
        switch (month + 1) {
            case 1:
                return "January";

            case 2:
                return "February";

            case 3:
                return "March";

            case 4:
                return "April";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "August";

            case 9:
                return "September";

            case 10:
                return "October";

            case 11:
                return "November";

            case 12:
                return "December";
        }

        return "";
    }


    public static SpannableString setTypeFace(Context context, String fontName, String title) {
        if (title != null) {
            Typeface font = Typeface.createFromAsset(context.getAssets(), fontName);
            SpannableString mNewTitle = new SpannableString(title);
            mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return mNewTitle;
        } else {
            return null;
        }
    }

    public static String getQRDataElement(String data) {
        String str = "";
        try {
            if (!data.isEmpty() && !data.equals("")) {
                Random r = new Random();
                int i1 = r.nextInt(data.trim().length() - 1);
                if (i1 == 0) {
                    i1 += 1;
                }
                str = data.substring(0, i1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            str = data;
        }

        return str;
    }

    public static int getScreenWidthPixel(Context context) {
        int widthPixel = 0;
        try {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            widthPixel = size.x;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return widthPixel;
    }

    public static String commaFormatter(String number) {
        if (number != null) {
            String pattern = "#,###,##0";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            return decimalFormat.format(Double.parseDouble(number));
        } else {
            return "";
        }
    }

    public static String commaFormatterWithDecimals(String number) {
        if (number != null) {
            String pattern = "#,###,##0.00";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            return decimalFormat.format(Double.parseDouble(number));
        } else {
            return "";
        }
    }

    public static String parseXML(String data, String nametag) {
        String result = "";
        int startpoint;
        int endpoint;

        //getting the starting point
        startpoint = data.indexOf("<" + nametag + ">");
        //getting the endpoint
        endpoint = data.indexOf("</" + nametag + ">");
        if (startpoint == -1 || endpoint == -1) {
            //return empty
            result = "NONE";
        } else {
            int starttaglen = nametag.length() + 2;
            //returning the extracted data
            result = data.substring(startpoint + starttaglen, endpoint);
        }

        return result;
    }

    public static String parseJSON(String json, String key) {
        String result = "";
        try {
            JSONObject obj = new JSONObject(json);
            result = obj.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String buildVoucherURL(String prodID) {
        String ext = prodID + "-voucher-design.jpg";
        String builtURL = CommonVariables.s3link + ext;
        return builtURL;
    }

    public static String capitalizeWord(String word) {

        if (word != null) {

            if (word.length() > 0) {

                StringBuilder builder = new StringBuilder();

                try {

                    String[] cap_word_arr = word.toLowerCase().split(" ");

                    for (String s : cap_word_arr) {
                        String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                        builder.append(cap + " ");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return builder.toString();

            } else {

                return null;

            }

        } else {

            return null;

        }

    }

    public static String userMobileNumber(String strEntry, boolean returnWithCC) {
        String mobileNumber = "";
        try {

            strEntry = strEntry.replace("(", "");
            strEntry = strEntry.replace(")", "");
            strEntry = strEntry.replace("+", "");
            strEntry = strEntry.replace(" ", "");

            if (returnWithCC) {
                if (strEntry.length() >= 10) {
                    switch (String.valueOf(strEntry.charAt(0))) {
                        case "0": {
                            mobileNumber = "63" + strEntry.substring(1);
                            break;
                        }
                        case "9": {
                            mobileNumber = "63" + strEntry;
                            break;
                        }
                        case "6": {
                            mobileNumber = strEntry;
                            break;
                        }
                        case "+": {
                            mobileNumber = strEntry.substring(1);
                            break;
                        }
                    }
                }
            } else {
                if (strEntry.length() >= 10) {
                    switch (String.valueOf(strEntry.charAt(0))) {
                        case "0": {
                            mobileNumber = strEntry.substring(1);
                            break;
                        }
                        case "9": {
                            mobileNumber = strEntry;
                            break;
                        }
                        case "6": {
                            mobileNumber = strEntry.substring(2);
                            break;
                        }
                        case "+": {
                            mobileNumber = strEntry.substring(3);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileNumber;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static String getMonthNumber(String strDate) {
        String mon = "";
        try {
            Calendar calender = Calendar.getInstance();
            SimpleDateFormat formatInput = new SimpleDateFormat("MMMM");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = formatInput.parse(strDate);
            calender.setTimeInMillis(date.getTime());
            if ((calender.get(Calendar.MONTH) + 1) < 10)
                mon = "0" + String.valueOf(calender.get(Calendar.MONTH) + 1);
            else
                mon = String.valueOf(calender.get(Calendar.MONTH) + 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mon;
    }

    public static String getCPMPCDate(String dateVal) {
        String dateString = "";
        try {
            SimpleDateFormat formatInput = new SimpleDateFormat("yyyy:MM:dd");
            formatInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat formatOutput = new SimpleDateFormat("MMMM dd, yyyy");

            Date date = formatInput.parse(dateVal);
            dateString = formatOutput.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static String replaceSingleQuoteforSQL(String value) {
        if (value.contains("'")) {
            value = value.replace("'", "''");
        }
        return value;
    }

    //LIMITS THE PASSED AMOUNT TO A SPECIFIC DECIMAL POINTS
    public static String totalamountlimiter(String number) {
        String pattern = "0.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(Double.parseDouble(number));
    }

    public static int calculateNoOfColumns(Context context, String from) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        int noOfColumns;

        if (from.equals(Voucher.KEY_MAIN_VOUCHER)) {
            noOfColumns = (int) (dpWidth / 85);
        } else if (from.equals("SERVICES")) {
            noOfColumns = (int) (dpWidth / 95);
        }else if(from.equals("PAYMENT CHANNELS")){
            noOfColumns = (int) (dpWidth / 108);
        }else {
            noOfColumns = (int) (dpWidth / 170);
        }
        Logger.debug("okhttp", "COLUMN WIDTH: " + dpWidth);
        return noOfColumns;
    }

    public static String buildAddress(String[] addressParams) {
        String address = "";
        for (int i = 0; i < addressParams.length; i++) {
            log(addressParams[i]);
            if (addressParams[i] != null) {
                address = address.concat(replaceEscapeData(addressParams[i])).concat(", ");
            }
        }
        return address.trim().substring(0, address.length() - 1);
    }

    public static boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null) {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    //CONVERTING VALUES INTO DP
    public static int converttoDP(Context context, int converttodp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (converttodp * scale + 0.5f);
    }

    //GET AGE FROM CALENDAR
    public static int getAgeFromCalendar(int passedyear, int passedmonth, int passedday) {

        GregorianCalendar cal = new GregorianCalendar();

        int year = 0;
        int month = 0;
        int day = 0;
        int age = 0;

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        cal.set(passedyear, passedmonth, passedday);
        age = year - cal.get(Calendar.YEAR);

        if ((month < cal.get(Calendar.MONTH)) || ((month == cal.get(Calendar.MONTH))
                && (day < cal.get(Calendar.DAY_OF_MONTH)))) {
            --age;
        }

//        if(age < 0)
//            throw new IllegalArgumentException("Age < 0");

        return age;
    }

    public static boolean stringContainsNumber(String string) {
        return Pattern.compile("[0-9]").matcher(string).find();
    }

    public static boolean stringContainsDuplicate(String string) {
        for (int i = 0; i < string.length(); i++) {
            for (int j = i + 1; j < string.length(); j++) {
                if (string.charAt(i) == string.charAt(j)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean stringContainsTwoDots(String string) {
        return string.indexOf(".") != string.lastIndexOf(".");
    }

    //TURNS THE 0/9 TO 63
    public static String convertoPHCountryCodeNumber(String number) {
        String result = "";
        if (number.length() > 0) {
            if (number.substring(0, 2).equals("63")) {
                if (number.length() == 12) {
                    result = number;
                }
            } else if (number.substring(0, 2).equals("09")) {
                if (number.length() == 11) {
                    result = "63" + number.substring(1, number.length());
                }
            } else if (number.substring(0, 1).equals("9")) {
                if (number.length() == 10) {
                    result = "63" + number;
                }
            }
        }
        return result;
    }

    public static File compressImage(Context context, File file) {
        try {
            return new Compressor(context)
                    .setMaxWidth(840)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public static boolean stringContainsNumberDecimal(String string) {
        return Pattern.compile("[.0-9]").matcher(string).find();
    }

    // This is base64 encoding, which is not an encryption
    public static String encodeToBase64(String input) {
        return Base64.encodeToString(input.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT).trim();
    }

    public static String decodeToBase64(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }

    //GET IMEI (COMMON)
    @SuppressLint("HardwareIds")
    public static String getImei(Context context) {
        String deviceId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return "";
                }
            }
            assert mTelephony != null;
            if (mTelephony.getDeviceId() != null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    deviceId = mTelephony.getImei();
                }else {
                    deviceId = mTelephony.getDeviceId();
                }
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        Log.d("deviceId", deviceId);
        return deviceId;
    }

    public static String checkImeiIfNull(String imei, String userid) {
        if (imei == null) {
            imei = "GK" + userid;
        }

        return imei;
    }


    /*|*****************SECURITY UPDATE AS OF OCT.14,2019************************|*/
    public static LinkedHashMap getIndexAndAuthenticationID(LinkedHashMap<String, String> parameters) {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        //RANDOM VALUE
        int orderCheck = CommonFunctions.getRandomNumberByLength(2);

        StringBuilder index = new StringBuilder();
        StringBuilder authentication = new StringBuilder();

        //Get all the keys from the Hashmap
        ArrayList<String> data = new ArrayList<>(parameters.keySet());
        try {
            String parameter;
            String paramValue;
            int parameterLength;

            for (int i = 0; i < data.size(); i++) {
                String key = String.valueOf(data.get(i));
                parameter = parameters.get(key);
                //GET RANDOM LENGTH
                assert parameter != null;
                parameterLength = getRandomNumberByLength(parameter.length());

                //CHART AT BASED ON RANDOM LENGTH
                paramValue = getRandomCharacter(parameter, parameterLength, orderCheck);

                index.append(parameterLength);
                authentication.append(paramValue);
            }

            //CREATE FINAL INDEX
            index = orderCheck == 0 ? index.append(0) :
                    index.append(1);

            //CREATE INDEX WITHOUT 0 OR 1 AND CREATE AUTHENTICATION
            map.put("index", index.toString());
            map.put("authenticationid", authentication.toString());

        } catch (Exception e) {
        }
        return map;
    }


    private static int getRandomNumberByLength(int length) {
        Random random = new Random();
        int num = 0;

        try {

            if (length != 0) {
                if (length > 10) {
                    num = random.nextInt(10);
                } else {
                    num = random.nextInt(length);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }

    private static String getRandomCharacter(String value, int randomindex, int ordercheck) {
        String strchar = "";

        int valueLength = value.length() - 1;
        if (ordercheck == 0) {
            strchar = String.valueOf(value.charAt(randomindex));
        } else {
            strchar = String.valueOf(value.charAt(valueLength - randomindex));
        }

        return strchar;
    }


    //AES256 CBC
    public static String encryptAES256CBC(String key, String message) {
        if (CommonVariables.isEncryptOrDecrypt) {
            AESCrypt.DEBUG_LOG_ENABLED = true;
        }

        String encryptedMsg = "";
        try {
            encryptedMsg = AESCrypt.encrypt(key, message);
        } catch (GeneralSecurityException e) {
            encryptedMsg = "error";
            e.printStackTrace();
        }

        return encryptedMsg;
    }

    //AES256 CBC
    public static String decryptAES256CBC(String key, String encryptedMsg) {
        if (CommonVariables.isEncryptOrDecrypt) {
            AESCrypt.DEBUG_LOG_ENABLED = true;
        }

        String messageAfterDecrypt = "";
        try {
            messageAfterDecrypt = AESCrypt.decrypt(key, encryptedMsg);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            messageAfterDecrypt = "error";
        }

        return messageAfterDecrypt;
    }

    public static List<String> splitOtpList(String shaotpvalue) {

        List<String> otpList = new ArrayList<>();

        int maxListLength = 5;
        int strOtpLength = shaotpvalue.length();

        int charlength = strOtpLength / maxListLength;

        int start = 0;
        int end = charlength;

        for (int i = 0; i <= maxListLength - 1; i++) {
            String otp = shaotpvalue.substring(start, end);

            start = start + charlength;
            end = end + charlength;
            otpList.add(otp);
        }

        return otpList;
    }

    public static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(5);

    }

    public static String getStringfromListStringByIndex(List<String> listvalue, int index) {
        List<String> stringList = new ArrayList<>();

        String stringvalue = "";

        for (int i = 0; i <= listvalue.size() - 1; i++) {
            if (i == index) {
                stringvalue = listvalue.get(i);
            }
        }

        return stringvalue;
    }

    public static LinkedHashMap<String, String> getIndexAndAuthID(LinkedHashMap<String, String> parameters, String privatekeylen, String privatekeyvalue) {

        //DECLARE VARIABLES
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        String index = "";
        String authenticationid = "";
        String param = "";
        String paramvalue = "";
        int paramlen = 0;

        //GENERATE RANDOM NUMBER
        int ordercheck = getRandomNumberByLength(2);

        //GET ALL THE KEYS FROM HASHMAP
        ArrayList<String> data = new ArrayList<>(parameters.keySet());

        try {
            for (int i = 0; i < data.size(); i++) {
                String key = String.valueOf(data.get(i));
                param = parameters.get(key);

                //GET RANDOM LENGTH
                paramlen = getRandomNumberByLength(param.length());
                //CHART AT BASED ON RANDOM LENGTH
                paramvalue = getRandomCharacter(param, paramlen, ordercheck);

                //GET INDEX AND AUTHENTICATIONID
                index = index.concat(String.valueOf(paramlen));
                authenticationid = authenticationid.concat(String.valueOf(paramvalue));
            }

            if (privatekeylen.equals("")) {
                index = ordercheck == 0 ? index + "0" : index + "1";
            } else {
                index = ordercheck == 0 ? index + privatekeylen + "0" : index + privatekeylen + "1";
            }

            authenticationid = privatekeyvalue.equals("") ? getSha1Hex(authenticationid)
                    : getSha1Hex(authenticationid + privatekeyvalue);

            //RETURNS FINAL INDEX AND FINAL AUTHENTICATIONID
            map.put("index", index);
            map.put("authenticationid", authenticationid);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static LinkedHashMap<String, String> getIndexAndAuthIDWithSession
            (Context context, LinkedHashMap<String, String> parameters, String sessionid) {


        //GET RANDOM NUMBER
        int ordercheck = getRandomNumberByLength(2);

        //DECLARE VARIABLES
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        String param;
        String paramvalue;
        int paramlen;

        String tempindex = "";
        String tempauth = "";
        String index;
        String authenticationid;

        //GET ALL THE KEYS FROM HASHMAP
        ArrayList<String> data = new ArrayList<>(parameters.keySet());



            for (int i = 0; i < data.size(); i++) {
                String key = String.valueOf(data.get(i));
                param = parameters.get(key);

                //GET RANDOM LENGTH
                paramlen = getRandomNumberByLength(param.length());
                //CHART AT BASED ON RANDOM LENGTH
                paramvalue = getRandomCharacter(param, paramlen, ordercheck);

                tempindex = tempindex.concat(String.valueOf(paramlen));
                tempauth = tempauth.concat(String.valueOf(paramvalue));

            }

            //PRIVATE KEY
            List<String> strSplitPrivateKey;
            strSplitPrivateKey = PreferenceUtils.getListPreference(context, "sha1otp");

            Logger.debug("okhttp", "==============SPLIT: " + strSplitPrivateKey.toString());

            int privatekeylen = 0;
            String privatekeyvalue = "";

            //RANDOM THE SHA1 LIST OF THE OTP
            privatekeylen = generateRandomNumber();
            privatekeyvalue = getStringfromListStringByIndex(strSplitPrivateKey, privatekeylen);

            //CREATE FINAL INDEX
            index = ordercheck == 0 ? tempindex + privatekeylen + "0" :
                    tempindex + privatekeylen + "1";


            Logger.debug("okhttp", "authenticationid : " + tempauth);
        Logger.debug("okhttp", "privatekeyvalue : " + privatekeyvalue);
            //CREATE FINAL AUTHENTICATIONID
            authenticationid = CommonFunctions.getSha1Hex(tempauth + privatekeyvalue + sessionid);

            //RETURNS FINAL INDEX AND FINAL AUTHENTICATIONID
            map.put("index", index);
            map.put("authenticationid", authenticationid);

            Logger.debug("okhttp", "INDEX : " + index);
            Logger.debug("okhttp", "authenticationid : " + authenticationid);


        return map;
    }

    public static LinkedHashMap getIndexAndAuthIDWithSessioNoKEY(LinkedHashMap<String, String> parameters, String sessionid) {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        //RANDOM VALUE
        int orderCheck = CommonFunctions.getRandomNumberByLength(2);

        StringBuilder index = new StringBuilder();
        StringBuilder authentication = new StringBuilder();
        //Get all the keys from the Hashmap
        ArrayList<String> data = new ArrayList<>(parameters.keySet());

        try {
            String parameter;
            String paramValue;
            int parameterLength;

            for (int i = 0; i < data.size(); i++) {
                String key = String.valueOf(data.get(i));
                parameter = parameters.get(key);

                //GET RANDOM LENGTH
                assert parameter != null;
                parameterLength = getRandomNumberByLength(parameter.length());

                //CHART AT BASED ON RANDOM LENGTH
                paramValue = getRandomCharacter(parameter, parameterLength, orderCheck);

                index.append(parameterLength);
                authentication.append(paramValue);
            }
            //CREATE FINAL INDEX
            index = orderCheck == 0 ? index.append(0) : index.append(1);
            //Create authentication ID
            String finalAuthID = CommonFunctions.getSha1Hex(authentication + sessionid);

            //Returns Final Index and Final Auth ID
            map.put("index", index.toString());
            map.put("authenticationid", finalAuthID);

            Logger.debug("OKHTTTTTTPPPPPPPPP", "TEMP AUTH :" + authentication);
            Logger.debug("OKHTTTTTTPPPPPPPPP", "INDEX :" + index);
            Logger.debug("OKHTTTTTTPPPPPPPPP", "FINAL AUTH: " + finalAuthID);


        } catch (Exception e) {
            Logger.debug("okhttp", e.getMessage());
        }
        return map;
    }

    public static void showSnackbar(Context context, View view, String message, String type) {


        /**if have time , please create ENUM for this, thanks
         *
         *                              Spread the love,
         *                                        Dev
         ***/

        final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setBackgroundColor(context.getResources().getColor(R.color.colorwhite));
        // Inflate your custom view with an Edit Text
        LayoutInflater objLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View snackView = objLayoutInflater.inflate(R.layout.snackbar_layout, null);

        RelativeLayout snackbarparent = snackView.findViewById(R.id.snackbar_parent);
        TextView snackbar_text = snackView.findViewById(R.id.snackbar_text);

        switch (type) {
            case "WARNING":
                snackbarparent.setBackgroundColor(context.getResources().getColor(R.color.color_gkearn_blue));
                break;
            case "ERROR":
                snackbarparent.setBackgroundColor(context.getResources().getColor(R.color.color_error_red));
                break;

            default:
                snackbarparent.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                break;
        }

        snackbar_text.setText(message);

        layout.addView(snackView, 0);
        layout.setPadding(0, 0, 0, 0);
        ViewCompat.setFitsSystemWindows(layout, false);
        ViewCompat.setOnApplyWindowInsetsListener(layout, null);
        snackbar.show();


    }


    public static String removeSpecialCharacters(String characters) {
        String value = "";
        value = Normalizer.normalize(characters, Normalizer.Form.NFD);
        value = value.replaceAll("[^a-zA-Z0-9]+","");
        return value;
    }


    public static String addDashIntervals(String original, int dashInterval) {
        if(original != null) {
            if(original.trim().equals("")) {
                return original;
            }
            StringBuilder withDashes = new StringBuilder(original.substring(0, dashInterval));
            for (int i = dashInterval; i < original.length(); i += dashInterval) {
                withDashes.append("-").append(original.substring(i, i + dashInterval));
            }
            return withDashes.toString();
        } else {
            return original;
        }
    }

}
