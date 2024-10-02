package projectProduto.Dao;

import projectProduto.ConnecDataProduto.DataBaseConnectionP;
import projectProduto.domain.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao implements IProdutoDao {

    @Override
    public Integer cadastrar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = DataBaseConnectionP.getConnection(); // Obtém a conexão com o banco de dados
            String sql = getSqlInsert(); //  consulta SQL para inserção
            stm = connection.prepareStatement(sql); //  instrução SQL
            adicionarParametrosInsert(stm, produto); // Adiciona parâmetros à consulta
            return stm.executeUpdate(); // Executa a atualização e retorna o número de linhas afetadas
        } catch (SQLException e) {
            throw e; // Relança a exceção para tratamento posterior
        } finally {
            closeConnection(connection, stm, null); // Fecha recursos
        }
    }

    @Override
    public Integer atualizar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = DataBaseConnectionP.getConnection();
            String sql = getSqlUpdate();
            stm = connection.prepareStatement(sql);
            adicionarParametrosUpdate(stm, produto);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Produto buscar(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Produto produto = null;
        try {
            connection = DataBaseConnectionP.getConnection();
            String sql = getSqlSelect();
            stm = connection.prepareStatement(sql);
            adicionarParametrosSelect(stm, codigo);
            rs = stm.executeQuery();

            if (rs.next()) { // Se houver um resultado
                produto = new Produto(); // Cria um novo objeto Produto
                Long id = rs.getLong("ID"); // Obtém o ID do resultado
                String nome = rs.getString("NOME"); // Obtém o nome do resultado
                String cd = rs.getString("CODIGO"); // Obtém o código do resultado
                produto.setId(id); // Define o ID
                produto.setNome(nome);
                produto.setCodigo(cd);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        }
        return produto; // Retorna o produto encontrado
    }

    @Override
    public Integer excluir(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = DataBaseConnectionP.getConnection();
            String sql = getSqlDelete();
            stm = connection.prepareStatement(sql);
            adicionarParametrosDelete(stm, produto);
            return stm.executeUpdate(); // Executa a atualização
        } catch (SQLException e) {
            throw e; // Relança a exceção
        } finally {
            closeConnection(connection, stm, null); // Fecha recursos
        }
    }

    @Override
    public List<Produto> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Produto> list = new ArrayList<>(); // Lista para armazenar produtos
        try {
            connection = DataBaseConnectionP.getConnection();
            String sql = getSqlSelectAll();
            stm = connection.prepareStatement(sql); // Prepara a instrução
            rs = stm.executeQuery(); // Executa a consulta

            while (rs.next()) { // Itera sobre os resultados
                Produto produto = new Produto(); // Cria um novo objeto Produto
                Long id = rs.getLong("ID"); // Obtém o ID do resultado
                String nome = rs.getString("NOME"); // Obtém o nome do resultado
                String cd = rs.getString("CODIGO");
                produto.setId(id); // Define o ID
                produto.setNome(nome);
                produto.setCodigo(cd);
                list.add(produto); // Adiciona o produto à lista
            }
        } catch (SQLException e) {
            throw e; // Relança a exceção
        } finally {
            closeConnection(connection, stm, rs); // Fecha recursos
        }
        return list; // Retorna a lista de produtos
    }

    private String getSqlInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO produtos (ID, CODIGO, NOME) ");
        sb.append("VALUES (nextval('produtos_seq'), ?, ?)"); // Utiliza sequência para ID
        return sb.toString();
    }

    private void adicionarParametrosInsert(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1, produto.getCodigo()); // Define o código do produto
        stm.setString(2, produto.getNome());
    }

    private String getSqlUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE produtos ");
        sb.append("SET NOME = ?, CODIGO = ? ");
        sb.append("WHERE ID = ?"); // Atualiza com base no ID
        return sb.toString();
    }

    private void adicionarParametrosUpdate(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1, produto.getNome()); // Define o nome do produto
        stm.setString(2, produto.getCodigo());
        stm.setLong(3, produto.getId()); 
    }

    private String getSqlDelete() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM produtos ");
        sb.append("WHERE CODIGO = ?"); // Exclui com base no código
        return sb.toString();
    }

    private void adicionarParametrosDelete(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1, produto.getCodigo()); // Define o código do produto
    }

    private String getSqlSelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM produtos ");
        sb.append("WHERE CODIGO = ?"); // Busca produto pelo código
        return sb.toString();
    }

    private void adicionarParametrosSelect(PreparedStatement stm, String codigo) throws SQLException {
        stm.setString(1, codigo); // Define o código como parâmetro
    }

    private String getSqlSelectAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM produtos"); // Seleciona todos os produtos
        return sb.toString();
    }

    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close(); // Fecha ResultSet
            }
            if (stm != null && !stm.isClosed()) {
                stm.close(); // Fecha PreparedStatement
            }
            if (connection != null && !connection.isClosed()) {
                connection.close(); // Fecha conexão
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime pilha de exceções em caso de falha
        }
    }
}
