package project.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:15432/mydb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Postgres2019!";

    // Método para obter uma conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método para criar a tabela 'clientes'
    public static void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS clientes (" +
                "id SERIAL PRIMARY KEY, " +
                "codigo VARCHAR(50), " +
                "nome VARCHAR(100))";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createTableSQL);
            System.out.println("Tabela criada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    // Método principal para executar o código
    public static void main(String[] args) {
        createTable(); // Chama o método para criar a tabela
    }
}