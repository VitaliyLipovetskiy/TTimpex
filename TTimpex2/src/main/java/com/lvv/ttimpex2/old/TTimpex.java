package com.lvv.ttimpex2.old;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

public class TTimpex {

    public static final String CONNECTION_STRING = "jdbc:paradox:";//C:\\IdeaProjects\\_paradox\\";

    //public static final String OUTPUT = "C:\\IdeaProjects\\_paradox\\Events.csv";

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Нет пути к базам");
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (month.length() == 1)
            month = "0" + month;
        if (day.length() == 1)
            day = "0" + day;
        String fileName = "D" + day + month + "_" + year;
//        System.out.println(fileName);

        Connection con = null;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]))){

            Class.forName("com.googlecode.paradox.Driver");
            con = DriverManager.getConnection(CONNECTION_STRING + args[0]);
            Statement statement = con.createStatement();

            String sql;
            sql = "SELECT * FROM " + fileName;

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String postId = resultSet.getString("POST");
                String event;
                if (resultSet.getString("Event").equals("0"))
                    event = "1";
                else
                    event = "0";
                String card = resultSet.getString("CARD");
                String time = resultSet.getString("TIME");
                String res = card + ";0000" + card +";" + postId + ";" +
                        event + ";" + day + "." + month + ".20" + year + ";" + time.substring(0,5);
                System.out.println(res);
                writer.write(res);
                writer.newLine();
            }

            resultSet.close();
            statement.close();
            con.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
