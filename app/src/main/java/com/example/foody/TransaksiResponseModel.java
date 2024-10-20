package com.example.foody;

public class TransaksiResponseModel {
    private String id;
    private String user_id;
    private String subscription_id;
    private String snap_token;
    private String gross_amount;

    public TransaksiResponseModel(String id, String user_id, String subscription_id,  String snap_token, String gross_amount) {
        this.id = id;
        this.user_id = user_id;
        this.subscription_id = subscription_id;
        this.snap_token = snap_token;
        this.gross_amount = gross_amount;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getSubscription_id() {
        return subscription_id;
    }

    public String getSnap_token() {
        return snap_token;
    }

    public String getGross_amount() {
        return gross_amount;
    }
}
