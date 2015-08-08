package quickmath;

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
//    private long maxAnswerSize;

    public Mode() {
        setDefaultValues();
    }

    public Mode(String fileName) {
        if (!loadFromFile(fileName)) {
            setDefaultValues();
        }
    }

    private void calculateMaxSize() {

    }

    private void setDefaultValues() {
        maxAdd = 10;
        maxSub = 10;
        maxMul = 10;
        maxDiv = 10;
        maxDivK = 10;
        maxSqr = 10;
        maxSingle = 10;
        maxSize = 3;
    }

    private boolean loadFromFile(String fileName) {
        return false;
    }


    public int getMaxAdd() {
        return maxAdd;
    }

    public void setMaxAdd(int maxAdd) {
        this.maxAdd = maxAdd;
    }

    public int getMaxSub() {
        return maxSub;
    }

    public void setMaxSub(int maxSub) {
        this.maxSub = maxSub;
    }

    public int getMaxMul() {
        return maxMul;
    }

    public void setMaxMul(int maxMul) {
        this.maxMul = maxMul;
    }

    public int getMaxDiv() {
        return maxDiv;
    }

    public void setMaxDiv(int maxDiv) {
        this.maxDiv = maxDiv;
    }

    public int getMaxDivK() {
        return maxDivK;
    }

    public void setMaxDivK(int maxDivK) {
        this.maxDivK = maxDivK;
    }

    public int getMaxSqr() {
        return maxSqr;
    }

    public void setMaxSqr(int maxSqr) {
        this.maxSqr = maxSqr;
    }

    public int getMaxSingle() {
        return maxSingle;
    }

    public void setMaxSingle(int maxSingle) {
        this.maxSingle = maxSingle;
    }
}
