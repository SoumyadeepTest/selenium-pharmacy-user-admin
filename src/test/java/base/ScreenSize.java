package base;

public enum ScreenSize {

    DESKTOP(1920, 1080),
    LAPTOP(1366, 768),
    TABLET(768, 1024),
    MOBILE(390, 844);

    private final int width;
    private final int height;

    ScreenSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}