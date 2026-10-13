package base;

public class BrowserConfig {

    private final String browser;
    private final boolean headless;
    private final ScreenSize screenSize;

    private BrowserConfig(Builder builder) {
        this.browser = builder.browser;
        this.headless = builder.headless;
        this.screenSize = builder.screenSize;
    }

    public String getBrowser() {
        return browser;
    }

    public boolean isHeadless() {
        return headless;
    }

    public ScreenSize getScreenSize() {
        return screenSize;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String browser = "chrome";
        private boolean headless = false;
        private ScreenSize screenSize = ScreenSize.DESKTOP;

        public Builder browser(String browser) {
            this.browser = browser;
            return this;
        }

        public Builder headless(boolean headless) {
            this.headless = headless;
            return this;
        }

        public Builder screen(ScreenSize screenSize) {
            this.screenSize = screenSize;
            return this;
        }

        public BrowserConfig build() {
            return new BrowserConfig(this);
        }
    }
}