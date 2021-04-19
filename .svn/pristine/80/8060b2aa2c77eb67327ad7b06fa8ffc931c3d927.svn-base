package com.goodkredit.myapplication.fragments.prepaidrequest;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by User-PC on 10/27/2017.
 */

public class BarcodeFormatFragment extends BaseFragment {
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    private String billingid = "";
    private String barcode = "";
    private String barcodeTitle = "";
    private ImageView barcodeImage;
    private TextView barcodeformat;

    public static BarcodeFormatFragment newInstance(String billingid, String barcode) {
        BarcodeFormatFragment fragment = new BarcodeFormatFragment();
        Bundle b = new Bundle();
        b.putString("billingid", billingid);
        b.putString("barcode", barcode);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.barcode_format_fragment, container, false);

        billingid = getArguments().getString("billingid");
        barcode = getArguments().getString("barcode");

        barcodeImage = (ImageView) view.findViewById(R.id.imgBarcode);
        barcodeformat = (TextView) view.findViewById(R.id.barcodeFormatLbl);

        try {
            switch (barcode) {
                case "CODE_128": {
                    barcodeTitle = "Format: CODE 128";
                    Bitmap bitmap = encodeAsBitmap(billingid, BarcodeFormat.CODE_128, 750, 300);
                    barcodeImage.setImageBitmap(bitmap);
                    break;
                }
                case "CODABAR": {
                    barcodeTitle = "Format: CODABAR";
                    Bitmap bitmap = encodeAsBitmap(billingid, BarcodeFormat.CODABAR, 750, 300);
                    barcodeImage.setImageBitmap(bitmap);
                    break;
                }
                case "CODE_39": {
                    barcodeTitle = "Format: CODE 39";
                    Bitmap bitmap = encodeAsBitmap(billingid, BarcodeFormat.CODE_39, 750, 300);
                    barcodeImage.setImageBitmap(bitmap);
                    break;
                }
                case "QR_CODE": {
                    barcodeTitle = "Format: QR CODE";
                    Bitmap bitmap = encodeAsBitmap(billingid, BarcodeFormat.QR_CODE, 750, 300);
                    barcodeImage.setImageBitmap(bitmap);
                    break;
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

        barcodeformat.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", barcodeTitle));


        return view;
    }

    /*********************
     * ALL FOR BARCODE
     ******************/
    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {

        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }
}
