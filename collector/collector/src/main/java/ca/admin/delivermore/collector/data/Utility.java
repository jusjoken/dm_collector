package ca.admin.delivermore.collector.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utility {

    public static Utility instance = null;

    public static Utility getInstance() {
        if (Utility.instance == null) {
            Utility.instance = new Utility();
        }
        return Utility.instance;
    }

    public Double round(Double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
