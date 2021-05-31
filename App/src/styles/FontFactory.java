package styles;

import java.awt.*;

public class FontFactory {

    private static final String Arial = "Arial";
    private static final String Times = "Times new roman";

    public static final Font MENU_FONT = new Font(Arial, Font.PLAIN, 14);

    public static final Font BASE_FONT = new Font(Times, Font.PLAIN, 16);
    public static final Font TITLE_FONT = new Font(Times, Font.ITALIC, 20);
    public static final Font PLAIN_TITLE_FONT = new Font(Times, Font.PLAIN, 20);

    public static final Font LINK_FONT = new Font(Times, Font.PLAIN, 24);
    public static final Font BIG_BUTTON = new Font(Times, Font.PLAIN, 24);
    public static final Font NORMAL_BUTTON = new Font(Times, Font.PLAIN, 20);
    public static final Font SMALL_BUTTON = new Font(Times, Font.PLAIN, 14);
}
