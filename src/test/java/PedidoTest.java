import org.example.DescontoService;
import org.example.ItemPedido;
import org.example.Pedido;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PedidoTest {

    @Mock
    private DescontoService descontoService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //Teste de Desconto de 10%:
    @Test
    public void testCalcularValorTotalComDescontoDezPorCento() {
        // Criando uma mock de itens de pedido
        ItemPedido item1 = new ItemPedido("Produto 1", 100.0, 2);
        List<ItemPedido> itens = Collections.singletonList(item1);

        // Configurando o mock para simular um desconto de 10%
        when(descontoService.calcularDesconto(Mockito.anyDouble())).thenReturn(20.0);

        // Criando o pedido
        Pedido pedido = new Pedido(itens, descontoService);

        // Calculando o valor total com desconto
        double valorTotalComDesconto = pedido.calcularValorTotal();

        // O valor total esperado após um desconto de 10% é 200 - 20 = 180.0
        assertEquals(180.0, valorTotalComDesconto, 0.01);
    }

     
    //Teste de Desconto Zero:
    @Test
    public void testCalcularValorTotalSemDesconto() {
        // Criando uma mock de itens de pedido
        ItemPedido item1 = new ItemPedido("Produto 1", 100.0, 2);
        List<ItemPedido> itens = Collections.singletonList(item1);

        // Configurando o mock para simular um desconto de zero
        when(descontoService.calcularDesconto(Mockito.anyDouble())).thenReturn(0.0);

        // Criando o pedido
        Pedido pedido = new Pedido(itens, descontoService);

        // Calculando o valor total sem desconto
        double valorTotalSemDesconto = pedido.calcularValorTotal();

        // O valor total esperado sem desconto é 200 - 0 = 200.0
        assertEquals(200.0, valorTotalSemDesconto, 0.01);
    }

   //Teste de Desconto Maior que Total: ZOADO
    @Test
    public void testCalcularValorTotalComDescontoMaiorQueTotal() {
        // Criando uma mock de itens de pedido
        ItemPedido item1 = new ItemPedido("Produto 1", 100.0, 2);
        List<ItemPedido> itens = Collections.singletonList(item1);

        // Configurando o mock para simular um desconto maior que o total
        when(descontoService.calcularDesconto(Mockito.anyDouble())).thenReturn(300.0);

        // Criando o pedido
        Pedido pedido = new Pedido(itens, descontoService);

        // Verificando se uma exceção IllegalArgumentException é lançada
        assertThrows(IllegalArgumentException.class, pedido::calcularValorTotal);
    }

    
    //Teste de Exceção para Valor Negativo após Desconto:
    @Test
    public void testExcecaoParaValorNegativo() {
        // Criando uma mock de itens de pedido
        ItemPedido item1 = new ItemPedido("Produto 1", 100.0, 2);
        List<ItemPedido> itens = Collections.singletonList(item1);

        // Configurando o mock para simular um desconto maior que o total
        when(descontoService.calcularDesconto(Mockito.anyDouble())).thenReturn(400.0);

        // Criando o pedido
        Pedido pedido = new Pedido(itens, descontoService);

        // Verificando se uma exceção IllegalArgumentException é lançada
        assertThrows(IllegalArgumentException.class, pedido::calcularValorTotal);
    }

    
    //Teste para Lista Vazia de Itens:
    @Test
    public void testCalcularValorTotalComListaVaziaDeItens() {
        // Criando uma lista vazia de itens de pedido
        List<ItemPedido> itensVazios = new ArrayList<>();
        Pedido pedido = new Pedido(itensVazios, descontoService);

        // Verificando se o método retorna 0.0 quando não há itens no pedido
        assertEquals(0.0, pedido.calcularValorTotal(), 0.01);
    }

    @Test
    public void testCalcularValorTotalChamaCalcularDescontoUmaVez() {
        // Criando um mock de DescontoService
        DescontoService descontoService = Mockito.mock(DescontoService.class);

        Pedido pedido = new Pedido(new ArrayList<>(), descontoService);

        pedido.calcularValorTotal();

        // Verificando se o método calcularDesconto foi chamado exatamente uma vez
        assertEquals(1, pedido.getCalcularDescontoChamado());
    }

    @Test
    public void testCalcularValorTotalComDoisDescontosDiferentes() {
        // Criando mocks de itens de pedido
        ItemPedido item1 = new ItemPedido("Produto 1", 100.0, 2);
        ItemPedido item2 = new ItemPedido("Produto 2", 50.0, 3);
        List<ItemPedido> itens = Arrays.asList(item1, item2);

        // Configurando o primeiro mock para simular um desconto de 10%
        when(descontoService.calcularDesconto(200.0)).thenReturn(20.0);

        // Configurando o segundo mock para simular um desconto de 5%
        when(descontoService.calcularDesconto(150.0)).thenReturn(7.5);

        // Criando o pedido
        Pedido pedido = new Pedido(itens, descontoService);

        // Calculando o valor total com descontos diferentes
        // O valor total esperado é (200 - 20) + (150 - 7.5) = 180 + 142.5 = 322.5
        double valorTotalComDescontosDiferentes = pedido.calcularValorTotal();

        // Verificando se o método calcularDesconto foi chamado duas vezes
        assertEquals(2, pedido.getCalcularDescontoChamado());

        assertEquals(322.5, valorTotalComDescontosDiferentes, 0.01);
    }

}
