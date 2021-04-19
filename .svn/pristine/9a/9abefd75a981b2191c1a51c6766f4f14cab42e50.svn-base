package com.goodkredit.myapplication.fragments.barcodes;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.GroupedVouchersActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Ban_Lenovo on 1/6/2017.
 */

public class FragmentBarcodeFormatCODABAR extends BaseFragment {

    String barcode_data;

    //Singleton
    public static FragmentBarcodeFormatCODABAR newInstance(String barcode_data) {
        FragmentBarcodeFormatCODABAR fragment = new FragmentBarcodeFormatCODABAR();
        Bundle b = new Bundle();
        b.putString("DATA", barcode_data);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barcode_item, container, false);

        ImageView barcodeImage = (ImageView) view.findViewById(R.id.barcode);
        TextView barcodeFormat = (TextView) view.findViewById(R.id.barcodeFormatLbl);
        Bitmap bitmap = null;
        try {
            bitmap = encodeAsBitmap(getArguments().getString("DATA"), BarcodeFormat.CODABAR, 750, 300);
            barcodeImage.setImageBitmap(bitmap);
            barcodeFormat.setText("Format: CODABAR");
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
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
