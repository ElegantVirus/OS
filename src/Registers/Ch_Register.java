package Registers;

/**
 * Created by El_Diablo on 3/20/2017.
 */
public class Ch_Register {
    private boolean ch;

    /**
     * Shows if the channel is busy
     * @return 1 if the channel is busy
     */
    public boolean getCh() {
        return ch;
    }

    public void setCh(boolean ch) {
        this.ch = ch;
    }
}
