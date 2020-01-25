package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

public class GetLatestVesionResponse
{


    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status+"";
    }


    @SerializedName("APK_URL")
    private String APK_URL;

    public String getAPK_URL()
    {
        return APK_URL+"";
    }


    public void setAPK_URL(String APK_URL)
    {
        this.APK_URL = APK_URL;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }





    /*@SerializedName("TOTAL")
    @Expose
    private Integer tOTAL;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("RECORDS")
    @Expose
    private List<RECORD> rECORDS = null;

    public Integer getTOTAL() {
        return tOTAL;
    }

    public void setTOTAL(Integer tOTAL) {
        this.tOTAL = tOTAL;
    }

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

    public List<RECORD> getRECORDS() {
        return rECORDS;
    }

    public void setRECORDS(List<RECORD> rECORDS) {
        this.rECORDS = rECORDS;
    }

    public class RECORD {

        @SerializedName("android_id")
        @Expose
        private String androidId;
        @SerializedName("version_name")
        @Expose
        private String versionName;
        @SerializedName("version_code")
        @Expose
        private Integer versionCode;
        @SerializedName("update_severity")
        @Expose
        private Integer updateSeverity;
        @SerializedName("Apk_url")
        @Expose
        private String apkUrl;

        public String getAndroidId() {
            return androidId;
        }

        public void setAndroidId(String androidId) {
            this.androidId = androidId;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public Integer getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(Integer versionCode) {
            this.versionCode = versionCode;
        }

        public Integer getUpdateSeverity() {
            return updateSeverity;
        }

        public void setUpdateSeverity(Integer updateSeverity) {
            this.updateSeverity = updateSeverity;
        }

        public String getApkUrl() {
            return apkUrl;
        }

        public void setApkUrl(String apkUrl) {
            this.apkUrl = apkUrl;
        }

    }*/
}
