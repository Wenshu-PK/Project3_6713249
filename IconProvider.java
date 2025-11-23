package Project3_6713249;


public class IconProvider {

    private static final String[] ICON_PATHS = {
            constants.ICON_1,
            constants.ICON_2,
            constants.ICON_3
    };

    public static MyImageIcon loadIcon(int index, int size) {
        return new MyImageIcon(ICON_PATHS[index]).resize(size, size);
    }

    public static int count() {
        return ICON_PATHS.length;
    }
}
