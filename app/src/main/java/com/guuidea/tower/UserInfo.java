package com.guuidea.tower;

public class UserInfo {
    /**
     * code : 200
     */

    private int code;
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "code=" + code +
                ", data=" + data.toString() +
                '}';
    }

    public static class DataBean {


        private String email;
        private String partnerUserId;
        private String phone;
        private String authTime;
        private String partnerAppUserId;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPartnerUserId() {
            return partnerUserId;
        }

        public void setPartnerUserId(String partnerUserId) {
            this.partnerUserId = partnerUserId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAuthTime() {
            return authTime;
        }

        public void setAuthTime(String authTime) {
            this.authTime = authTime;
        }

        public String getPartnerAppUserId() {
            return partnerAppUserId;
        }

        public void setPartnerAppUserId(String partnerAppUserId) {
            this.partnerAppUserId = partnerAppUserId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "email='" + email + '\'' +
                    ", partnerUserId='" + partnerUserId + '\'' +
                    ", phone='" + phone + '\'' +
                    ", authTime='" + authTime + '\'' +
                    ", partnerAppUserId='" + partnerAppUserId + '\'' +
                    '}';
        }
    }
}
