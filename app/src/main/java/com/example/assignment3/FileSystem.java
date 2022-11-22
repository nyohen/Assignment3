package com.example.assignment3;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import android.app.Activity;
import android.content.Context;

public class FileSystem
{
    static String outputToFile = "result.txt";
    FileOutputStream os;
    FileInputStream is;

    private ArrayList<Integer> resultArr(String fileContent)
    {
        int defaultVal = 0, storeNum_= 1;
        ArrayList<Integer> resultArray = new ArrayList<>(2);

        for (int a = 0; a < fileContent.toCharArray().length; a++) {

            if (fileContent.toCharArray()[a] == '-')
            {
                int isCorrect_ = Integer.parseInt
                        (fileContent.substring(defaultVal, a));
                int qAmount_ = Integer.parseInt(fileContent.substring
                        (a + storeNum_, fileContent.toCharArray().length));

                resultArray.add(isCorrect_);
                resultArray.add(qAmount_);
                break;
            }
        } return resultArray;
    }

    public void saveRecords(Activity temp, int isCorrect, int qAmount)
    {
        try {
            os = temp.openFileOutput(outputToFile, Context.MODE_PRIVATE);
            os.write((isCorrect + "-" + qAmount).getBytes());
        }
        catch (IOException e) { e.printStackTrace(); }
        finally {
            try { os.close(); }
            catch (IOException e) { e.printStackTrace(); }
        }
    }

    public ArrayList<Integer> readRecords(Activity context)
    {
        int defaultVal = -1, defaultVal_ = 2, storeInput;
        ArrayList<Integer> recordOutput = new ArrayList<>(defaultVal_);
        StringBuilder sb = new StringBuilder();

        try {
            is = context.openFileInput(outputToFile);
            InputStreamReader inputStreamReader = new InputStreamReader
                    (is, StandardCharsets.UTF_8);
            while ((storeInput = inputStreamReader.read()) != defaultVal) {
                sb.append((char) storeInput);
            }
            recordOutput = resultArr(sb.toString());
        }
        catch (IOException e) { e.printStackTrace(); }
        finally {
            try { is.close(); }
            catch (IOException e) { e.printStackTrace(); }
        }
        return recordOutput;
    }

    public void deleteRecords(Activity temp)
    {
        try {
            os = temp.openFileOutput(outputToFile, Context.MODE_PRIVATE);
            os.write(("").getBytes());
        }
        catch (IOException e) { e.printStackTrace(); }
        finally {
            try { os.close(); }
            catch (IOException e) { e.printStackTrace(); }
        }
    }
}
