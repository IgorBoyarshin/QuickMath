package quickmath;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Igor on 08-Aug-15.
 */
public class Mode {
    private int maxAdd;
    private int maxSub;
    private int maxMul;
    private int maxDiv;
    private int maxDivK;
    private int maxSqr;
    private int maxSingle;
    private int maxSize;
    private int maxAnswerSize;

    public Mode() {
        setDefaultValues();
    }

    public Mode(String fileName) {
        if (!loadFromFile(fileName)) {
            setDefaultValues();
        }
    }

    private void calculateSizesWithAdd() {
        int digits = getDigits(maxAdd);
        maxSize = digits > maxSize ? digits : maxSize;

        digits = getDigits(maxAdd + maxAdd);
        maxAnswerSize = digits > maxAnswerSize ? digits : maxAnswerSize;
    }

    private void calculateSizesWithSub() {
        int digits = getDigits(maxSub);
        maxSize = digits > maxSize ? digits : maxSize;

        digits = getDigits(maxSub);
        maxAnswerSize = digits > maxAnswerSize ? digits : maxAnswerSize;
    }

    private void calculateSizesWithMul() {
        int digits = getDigits(maxMul);
        maxSize = digits > maxSize ? digits : maxSize;

        digits = getDigits(maxMul * maxMul);
        maxAnswerSize = digits > maxAnswerSize ? digits : maxAnswerSize;
    }

    private void calculateSizesWithDiv() {
        int digits = getDigits(maxDiv * maxDivK);
        maxSize = digits > maxSize ? digits : maxSize;

        digits = getDigits(maxDivK);
        maxAnswerSize = digits > maxAnswerSize ? digits : maxAnswerSize;
    }

    private void calculateSizesWithSqr() {
        int digits = getDigits(maxSqr);
        maxSize = digits > maxSize ? digits : maxSize;

        digits = getDigits(maxSqr * maxSqr);
        maxAnswerSize = digits > maxAnswerSize ? digits : maxAnswerSize;
    }

    private void calculateSizesWithSingle() {
        int digits = getDigits(maxSingle);
        maxSize = digits > maxSize ? digits : maxSize;

        digits = getDigits(maxSingle * 9);
        maxAnswerSize = digits > maxAnswerSize ? digits : maxAnswerSize;
    }

    public static int getDigits(int number) {
        int counter = 1;
        while (number >= 10) {
            number /= 10;
            counter++;
        }

        return counter;
    }

    private void setDefaultValues() {
        maxAdd = 9999;
        maxSub = 9999;
        maxMul = 99;
        maxDiv = 50;
        maxDivK = 20;
        maxSqr = 99;
        maxSingle = 999;
        maxSize = 4;
        maxAnswerSize = 5;
    }

    private boolean loadFromFile(String fileName) {
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(fileName));

            maxAdd = Integer.parseInt(in.readLine());
            maxSub = Integer.parseInt(in.readLine());
            maxMul = Integer.parseInt(in.readLine());
            maxDiv = Integer.parseInt(in.readLine());
            maxDivK = Integer.parseInt(in.readLine());
            maxSqr = Integer.parseInt(in.readLine());
            maxSingle = Integer.parseInt(in.readLine());
            maxSize = Integer.parseInt(in.readLine());
            maxAnswerSize = Integer.parseInt(in.readLine());

            in.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        return true;
    }


    public int getMaxAdd() {
        return maxAdd;
    }

    public void setMaxAdd(int maxAdd) {
        this.maxAdd = maxAdd;
        calculateSizesWithAdd();
    }

    public int getMaxSub() {
        return maxSub;
    }

    public void setMaxSub(int maxSub) {
        this.maxSub = maxSub;
        calculateSizesWithSub();
    }

    public int getMaxMul() {
        return maxMul;
    }

    public void setMaxMul(int maxMul) {
        this.maxMul = maxMul;
        calculateSizesWithMul();
    }

    public int getMaxDiv() {
        return maxDiv;
    }

    public void setMaxDiv(int maxDiv) {
        this.maxDiv = maxDiv;
        calculateSizesWithDiv();
    }

    public int getMaxDivK() {
        return maxDivK;
    }

    public void setMaxDivK(int maxDivK) {
        this.maxDivK = maxDivK;
        calculateSizesWithDiv();
    }

    public int getMaxSqr() {
        return maxSqr;
    }

    public void setMaxSqr(int maxSqr) {
        this.maxSqr = maxSqr;
        calculateSizesWithSqr();
    }

    public int getMaxSingle() {
        return maxSingle;
    }

    public void setMaxSingle(int maxSingle) {
        this.maxSingle = maxSingle;
        calculateSizesWithSingle();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getMaxAnswerSize() {
        return maxAnswerSize;
    }
}
