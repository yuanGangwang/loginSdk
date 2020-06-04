package com.guuidea.tower;

public class Token {

    /**
     * code : 200
     * msg : 成功
     * data : {"accessToken":"5267b005-133b-446e-962d-943171d8093d","tokenType":"bearer","refreshToken":"f779b441-e0b6-4851-9759-7b74bf5a1c15","expiresIn":0}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * accessToken : 5267b005-133b-446e-962d-943171d8093d
         * tokenType : bearer
         * refreshToken : f779b441-e0b6-4851-9759-7b74bf5a1c15
         * expiresIn : 0
         */

        private String accessToken;
        private String tokenType;
        private String refreshToken;
        private int expiresIn;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }
    }
}
