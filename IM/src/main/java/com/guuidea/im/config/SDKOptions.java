package com.guuidea.im.config;

public class SDKOptions {

    private String appKey;
    private String appSecret;
    private String authToken;

    public SDKOptions() {
    }


    public SDKOptions(String appKey, String appSecret, String authToken) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.authToken = authToken;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getAuthToken() {
        return authToken;
    }

    public static final class SDKOptionsBuilder {
        private String appKey;
        private String appSecret;
        private String authToken;

        private SDKOptionsBuilder() {
        }

        public static SDKOptionsBuilder aSDKOptions() {
            return new SDKOptionsBuilder();
        }

        public SDKOptionsBuilder setAppKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public SDKOptionsBuilder setAppSecret(String appSecret) {
            this.appSecret = appSecret;
            return this;
        }

        public SDKOptionsBuilder setAuthToken(String authToken) {
            this.authToken = authToken;
            return this;
        }

        public SDKOptions build() {
            return new SDKOptions(appKey, appSecret, authToken);
        }
    }
}
