package disconnectionist.www.borrower;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jameshudson on 24/2/17.
 */

    public class BorrowItem implements Serializable {
        String user;
        String item;
        Date date;
    }
