package net.victu.datatracker.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EasyMySQLAcessor {

    private String host;
    private String schema;
    private String user;
    private String password;
    private int port;
    private Connection connection;
    private String url;

    public EasyMySQLAcessor(String host, String schema, String user, String password, int port) {
        this.host = host;
        this.schema = schema;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    public EasyMySQLAcessor(String url) {
        this.url = url;
    }

    public void connect() {
        try {
            if(url != null) {
                connection = DriverManager.getConnection(url);
            } else {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + schema, user, password);
            }
            System.out.println("Conectado com sucesso!");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean isConnected() {
        try{
            return connection != null && !connection.isClosed();
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection(){
        if(!isConnected()){
            connect();
        }
        return connection;
    }

    public void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Factory factory() {
        return new Factory();
    }

    public static class Factory {
        private String host;
        private String schema;
        private String user;
        private String password;
        private int port;

        public Factory host(String host) {
            this.host = host;
            return this;
        }

        public Factory schema(String schema) {
            this.schema = schema;
            return this;
        }

        public Factory user(String user) {
            this.user = user;
            return this;
        }

        public Factory password(String password) {
            this.password = password;
            return this;
        }

        public Factory port(int port) {
            this.port = port;
            return this;
        }

        public EasyMySQLAcessor build() {
            return new EasyMySQLAcessor(host, schema, user, password, port);
        }
    }
}
