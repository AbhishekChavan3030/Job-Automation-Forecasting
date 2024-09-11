package com.fileprocess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author Jitu Patil
 */
public class NLP_Process {

    public static String resultdata = "";
    public static TreeSet<String> list = new TreeSet<String>();
    public static Map<String, Integer> map = new HashMap<>();

   
    public static String Occupation_Title_process(String data) {
        String result_data;
        result_data=data.replace("'","").replace("\"","").replace("-","");
        System.out.println(result_data);
        return result_data;
    }
    public static String Typical_Entry_Level_Education_process(String data) {
        String result_data;
        result_data=data.replace("'","").replace("\"","").replace("-","");
        System.out.println(result_data);

        return result_data;
    }
    public static String Typical_on_the_job_Training_process(String data) {
        String result_data;
        result_data=data.replace("'"," ").replace("\""," ").replace("-"," ");
        System.out.println(result_data);
        return result_data;
    }
    



}
