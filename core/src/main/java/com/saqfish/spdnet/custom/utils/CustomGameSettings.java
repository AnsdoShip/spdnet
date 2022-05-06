package com.saqfish.spdnet.custom.utils;

import com.watabou.utils.GameSettings;

public class CustomGameSettings extends GameSettings {

    public static void putSeedString(String str){
        put("seed_string_input_val", str);
    }

    public static String getSeedString(){
        return getString("seed_string_input_val", "");
    }


}

