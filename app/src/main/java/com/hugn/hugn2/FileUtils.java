package com.hugn.hugn2;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class FileUtils {

    private static File getHugnDirectory() {
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File hugnDir = new File(downloadDir, "Hugn04");
        if (!hugnDir.exists()) {
            hugnDir.mkdirs();
        }
        return hugnDir;
    }

    public static String getTextFromFile(String fileName, Context context) {
        try {
            StringBuilder text = new StringBuilder(); //String
            File f = new File(getHugnDirectory(), fileName);
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
            }
            return new String(text);
        } catch (Exception e) {
            Log.e("toi dang doc file", e.getStackTrace().toString());
            return "";
        }
    }

    public static String getTextFromFileVerification(String fileName,
                                                     Context context) {
        try {
            FileInputStream fIn = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fIn);

            char[] inputBuffer = new char[32];
            isr.read(inputBuffer);
            return new String(inputBuffer);
        } catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
            return "";
        }
    }

    public static void taoFileTXT(String noidung, String fileName,
                                  Context context) {
        try {
            File file = new File(getHugnDirectory(), fileName);
            final String TEXTSTRING = new String(noidung);
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write(TEXTSTRING);
            osw.flush();
            osw.close();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

    public static void addLineToFile(String line, String fileName,
                                     Context context) {

        try {
            File f = new File(getHugnDirectory(), fileName);
            final String TEXTSTRING = new String(line);
            FileWriter fOut = new FileWriter(f, true);
            fOut.append(TEXTSTRING);
            fOut.append("\n");
            fOut.flush();
            fOut.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    public static void deleteFile(String fileName, Context context) {
        File f = new File(getHugnDirectory(), fileName);
        if (f.exists()) {
            f.delete();
        }
    }

}
