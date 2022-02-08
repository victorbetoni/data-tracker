package net.victu.datatracker.util.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class SQLUtils {
    public static void executeScript(BufferedReader fileReader, Connection connection) {
        StringBuilder fileContent = new StringBuilder();
        try {
            String line = "";
            while((line = fileReader.readLine()) != null) {
                fileContent.append(line);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        final String[] queries = fileContent.toString().split(";");
        Arrays.stream(queries).forEach((query) -> {
            try{
                Statement st = connection.createStatement();
                st.execute(query);
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        });
    }
}
