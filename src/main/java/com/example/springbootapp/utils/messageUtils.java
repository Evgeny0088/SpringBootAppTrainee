package com.example.springbootapp.utils;

import com.example.springbootapp.DBdomain.User;

public abstract class messageUtils {

    public static String getAuthorName(User author){
        return author!=null ? author.getUsername() : "<none>";
    }

}
