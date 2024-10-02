package testCliente;

import org.junit.jupiter.api.Test;
import project.dao.ClienteDAO;
import project.domain.Cliente;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteDAOTest {

    private ClienteDAO clienteDAO = new ClienteDAO();
    @Test
    public void cadastrarTest() throws Exception {
        // Instanciação do DAO
        clienteDAO = new ClienteDAO();

        // Criação do objeto Cliente
        Cliente cliente = new Cliente();
        cliente.setCodigo("11");  // Código alterado para "67"
        cliente.setNome("Hugo Lima");

        // Cadastrar o cliente
        Integer countCad = clienteDAO.cadastrar(cliente);
        System.out.println("Linhas afetadas no cadastro: " + countCad);
        assertEquals(1, countCad);  // Verifica se o cadastro foi bem-sucedido

        ///Buscar o cliente cadastrado
        Cliente clienteBD = clienteDAO.buscar(cliente.getCodigo());
       assertNotNull(clienteBD);  // Verifica se o cliente foi encontrado
       assertEquals(cliente.getCodigo(), clienteBD.getCodigo());  // Compara os códigos
        assertEquals(cliente.getNome(), clienteBD.getNome());  // Compara os nomes

        // (Opcional) Verificação adicional após exclusão, se aplicável
         Integer countDel = clienteDAO.excluir(clienteBD);
         System.out.println("Linhas afetadas na exclusão: " + countDel);
         assertEquals(1, countDel);  // Verifica se a exclusão foi bem-sucedida


        //Modelo professor codigo da erro só se precisar mas a frente
        // Integer countCad = clienteDAO.excluir(clienteBD);
        //assertEquals(countCad == 1);
    }

    @Test
    void atualizarTest() throws Exception {
        clienteDAO = new ClienteDAO();

        // cadastro cliente
        Cliente cliente = new Cliente();
        cliente.setCodigo("10");
        cliente.setNome("Marcos Barbosa");
        Integer coundCad = clienteDAO.cadastrar(cliente);;
        assertTrue(coundCad == 1);


        // buscar o cliente nome e codigo
        Cliente clienteBD = clienteDAO.buscar("10");
        assertNotNull(clienteBD);
        assertEquals(cliente.getCodigo(), clienteBD.getCodigo());
        assertEquals(cliente.getNome(), clienteBD.getNome());

        //outro cliente
        clienteBD.setCodigo("20");
        clienteBD.setNome("Cliente test");
        Integer countupdate = clienteDAO.atualizar(clienteBD);
        assertTrue(countupdate == 1);

        //buscar o cliente "1o"
        Cliente clienteBD1 = clienteDAO.buscar("10");
        assertNull(clienteBD1);

        //buscar e atualiar
        Cliente clienteBD2 = clienteDAO.buscar("20");
        assertNotNull(clienteBD2);
        assertEquals(clienteBD.getId(), clienteBD2.getId());
        assertEquals(clienteBD.getCodigo(), clienteBD2.getCodigo());
        assertEquals(clienteBD.getNome(), clienteBD2.getNome());

        List<Cliente> list = clienteDAO.buscarTodos();
        for (Cliente cli : list){
            clienteDAO.excluir(cli);
        }


    }

    @Test
    void buscarTest() throws Exception {
        clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("01");
        cliente.setNome("Chapo Gusman");

        // Cadastrar o cliente
        Integer countCad = clienteDAO.cadastrar(cliente);
        System.out.println("Valor de countCad após cadastro: " + countCad);
        assertTrue(countCad == 1, "Cadastro falhou, countCad não é 1");

        // Buscar o cliente no banco de dados
        Cliente clienteBD = clienteDAO.buscar("01");
        assertNotNull(clienteBD, "Cliente não encontrado no banco de dados");

        // Exibir as informações do cliente encontrado
        System.out.println("Cliente encontrado: Código = " + clienteBD.getCodigo() + ", Nome = " + clienteBD.getNome());

        assertEquals(cliente.getCodigo(), clienteBD.getCodigo(), "Códigos não correspondem");
        assertEquals(cliente.getNome(), clienteBD.getNome(), "Nomes não correspondem");

        // Excluir o cliente (comentado para este teste)
        Integer countCad1 = clienteDAO.excluir(clienteBD);
         System.out.println("Valor de countCad1 após exclusão: " + countCad1);
         assertTrue(countCad1 == 1);
    }

    @Test
    void excluirTest() throws Exception {
        clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("01");
        cliente.setNome("Chapo Gusman");

        // Cadastrar o cliente
        Integer countCad = clienteDAO.cadastrar(cliente);
        System.out.println("Valor de countCad após cadastro: " + countCad);
        assertTrue(countCad == 1, "Cadastro falhou, countCad não é 1");

        // Buscar o cliente no banco de dados
        Cliente clienteBD = clienteDAO.buscar("01");
        assertNotNull(clienteBD, "Cliente não encontrado no banco de dados");

        // Exibir as informações do cliente encontrado
        System.out.println("Cliente encontrado: Código = " + clienteBD.getCodigo() + ", Nome = " + clienteBD.getNome());

        assertEquals(cliente.getCodigo(), clienteBD.getCodigo(), "Códigos não correspondem");
        assertEquals(cliente.getNome(), clienteBD.getNome(), "Nomes não correspondem");

        // Excluir o cliente (comentado para este teste)
        Integer countCad1 = clienteDAO.excluir(clienteBD);
        System.out.println("Valor de countCad1 após exclusão: " + countCad1);
        assertTrue(countCad1 == 1);
    }

    @Test
    void buscarTodos() throws Exception {
        clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("88");
        cliente.setNome("Ruam Lira");
        Integer coundCad =clienteDAO.cadastrar(cliente);
        assertTrue(coundCad == 1);

        cliente.setCodigo("89");
        cliente.setNome("Joao Lucas");
        Integer coundCad1 =clienteDAO.cadastrar(cliente);
        assertTrue(coundCad1 == 1);

        List<Cliente> list = clienteDAO.buscarTodos();
        assertNotNull(list);
        assertEquals(2,list.size());

        int counDel = 0;
        for (Cliente cli : list){
            clienteDAO.excluir(cli);
            counDel++;

        }
       assertEquals(list.size(), counDel);

        list = clienteDAO.buscarTodos();
        assertEquals(list.size(), 0);

    }
}
