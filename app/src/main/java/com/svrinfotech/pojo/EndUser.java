package com.svrinfotech.pojo;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class EndUser extends SugarRecord {

    String name,status,email;
    String hashSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashSet() {
        return hashSet;
    }

    public void setHashSet(ArrayList courseList) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("hashSet",new ArrayList<>(courseList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        hashSet=jsonObject.toString();
    }
}
