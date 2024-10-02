package testProduto;

import org.junit.jupiter.api.Test;
import projectProduto.Dao.ProdutoDao;
import projectProduto.domain.Produto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoDaoTest {

    private ProdutoDao produtoDao = new  ProdutoDao();

    @Test
   public void cadastrarTestProduto() throws Exception {

        // Instanciação do DAO
        produtoDao = new ProdutoDao();

        // Criação do objeto Cliente
        Produto produto = new Produto();
        produto.setCodigo("222");
        produto.setNome("Laptop");

        //cadastra o produto
        Integer countCad = produtoDao.cadastrar(produto);
        System.out.println("Linhas afetadas no cadastro:" + countCad);
        assertEquals(1, countCad);

        ///Buscar o cliente cadastrado
        Produto produtoBD = produtoDao.buscar(produto.getCodigo());
        assertNotNull(produtoBD); //verifica se foi encontrado
        assertEquals(produto.getCodigo(), produtoBD.getCodigo()); //compara os codigos
        assertEquals(produto.getNome(), produtoBD.getNome());


        // (Opcional) Verificação adicional após exclusão, se aplicável

        Integer CoundDelet = produtoDao.excluir(produtoBD);
        System.out.println("Linha afetada apos exclução" + CoundDelet);
        assertEquals(1, CoundDelet);

    }

    @Test
    void atualizarTestProduto() throws  Exception {
        produtoDao = new ProdutoDao();

        // cadastro prduto
        Produto produto = new Produto();
        produto.setCodigo("101");
        produto.setNome("Tv");
        Integer coundCad = produtoDao.cadastrar(produto);
        assertTrue(coundCad == 1);


        // buscar o produto nome e codigo
        Produto produtoBD = produtoDao.buscar("101");
        assertNotNull(produtoBD);//verifica se foi encontrado
        assertEquals(produto.getCodigo(), produto.getCodigo()); //verifica(compara) os codigos
        assertEquals(produtoBD.getNome(), produtoBD.getNome()); //verifica(compara) os nomes

        //outro produto
        produtoBD.setCodigo("20");
        produtoBD.setNome("Caregador");
        Integer countupdate = produtoDao.atualizar(produtoBD);
        assertTrue(countupdate == 1);

        //buscar o cliente "1o"
        Produto produtoBD1 = produtoDao.buscar("101");
        assertNull(produtoBD1);

        //buscar e atualiar
        Produto produtoBD2 = produtoDao.buscar("20");
        assertNotNull(produtoBD2);//verifica se foi encontrado
        assertEquals(produtoBD.getId(), produtoBD2.getId());//verifica(compara) os ID
        assertEquals(produtoBD.getCodigo(), produtoBD2.getCodigo()); //verifica(compara) os Codigo
        assertEquals(produtoBD.getNome(), produtoBD2.getNome()); //verifica(compara) os Nomes

        List<Produto> list = produtoDao.buscarTodos();
        for (Produto prodLis : list){
            produtoDao.excluir(prodLis);
        }
    }

    @Test
    void buscarTestProduto() throws Exception {

        // Instanciação do DAO
        produtoDao = new ProdutoDao();

        Produto produto = new Produto();
        produto.setCodigo("244");
        produto.setNome("Teclado Mecanico");

        // Cadastrar o produto
        Integer countCad = produtoDao.cadastrar(produto);
        System.out.println("Valor de countCad após cadastro: " + countCad);
        assertTrue(countCad == 1, "Cadastro falhou, countCad não é 1");

        // Buscar o cliente no banco de dados
        Produto produtoBD = produtoDao.buscar("244");
        assertNotNull(produtoBD, "Produto não encontrado no banco de dados"); //caso nao encontrado
        assertEquals(produto.getCodigo(), produtoBD.getCodigo(), "Códigos não correspondem"); //caso nao encontrado
        assertEquals(produto.getNome(), produtoBD.getNome(), "Nomes não correspondem"); //caso nao encontrado


        // Exibir as informações do cliente encontrado
        System.out.println("Produto encontrado: Código = " + produtoBD.getCodigo() + ", Nome = " + produtoBD.getNome());



        // Excluir o produto test
        Integer countCad1 = produtoDao.excluir(produtoBD);
        System.out.println("Valor de countCad1 após exclusão: " + countCad1);
        assertTrue(countCad1 == 1);
    }


    @Test
    void excluirTestProduto() throws Exception{

        //instancia o DAO
        produtoDao = new ProdutoDao();

        Produto produto = new Produto();
        produto.setCodigo("67");
        produto.setNome("Fone");

        // Cadastrar
        Integer countCad = produtoDao.cadastrar(produto);
        System.out.println("Valor de countCad após cadastro: " + countCad);
        assertTrue(countCad == 1, "Cadastro falhou, countCad não é 1");

        // Buscar o produto no banco de dados
        Produto produtoBD = produtoDao.buscar("67");
        assertNotNull(produtoBD, "Produto não encontrado no banco de dados"); // se não for encontrado
        assertEquals(produto.getCodigo(), produtoBD.getCodigo());
        assertEquals(produto.getNome(), produtoBD.getNome());


        // Exibir as informações do Produto encontrado
        System.out.println("Produto encontrado: Código = " + produtoBD.getCodigo() + ", Nome = " + produto.getNome());


        // Excluir test do sql
        Integer countCad1 = produtoDao.excluir(produtoBD);
        System.out.println("Valor de countCad1 após exclusão: " + countCad1);
        assertTrue(countCad1 == 1);
    }

    @Test
    void buscarTodosTestProduto() throws Exception {

        //instancia o DAO
        produtoDao = new ProdutoDao();

        //cadastrar
        Produto produto= new Produto();
        produto.setCodigo("4452");
        produto.setNome("Monitor 43");
        Integer coundCad =produtoDao.cadastrar(produto);
        assertTrue(coundCad == 1);

        //cadastrar
        produto.setCodigo("4468");
        produto.setNome("Monitor 32");
        Integer coundCad1 = produtoDao.cadastrar(produto);
        assertTrue(coundCad1 == 1);

        //lista para os produtos buscados
        List<Produto> list = produtoDao.buscarTodos();
        assertNotNull(list);
        assertEquals(2,list.size());

        // Exclui todos os produtos da lista
        int counDel = 0;
        for (Produto prod : list){
            produtoDao.excluir(prod);
            counDel++;

        }
        // Verifica se todos os produtos foram excluídos
        assertEquals(list.size(), counDel);

        // Verifica se o banco está vazio após as exclusões
        list = produtoDao.buscarTodos();
        assertEquals(list.size(), 0);



    }
}