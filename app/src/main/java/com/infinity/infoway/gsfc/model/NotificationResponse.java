package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user02 on 8/22/2017.
 */

public class NotificationResponse
{

    @SerializedName("notif_no")
    private String notifno;
    public String getNotifno()
    {
        return notifno;
    }
    @SerializedName("notif_head")
    private String notifhead;
    public String getNotifhead()
    {
        return notifhead;
    }

    @SerializedName("notif_msg")
    private String notifmsg;

    public String getNotifmsg()
    {
        return notifmsg;
    }


    @SerializedName("notif_date")
    private String notifdate;

    public String getNotifdate()
    {
        return notifdate;
    }

}
