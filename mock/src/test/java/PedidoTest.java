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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class PedidoTest {

    @Mock
    private DescontoService descontoService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCalcularValorTotalComDesconto() {
        // Criando uma lista de itens de pedido
        ItemPedido item1 = new ItemPedido("Produto 1", 100.0, 2);
        ItemPedido item2 = new ItemPedido("Produto 2", 50.0, 3);
        List<ItemPedido> itens = Arrays.asList(item1, item2);

        when(descontoService.calcularDesconto(Mockito.anyDouble())).thenReturn(15.0);

        Pedido pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();

        // O valor total esperado após o desconto é (200 + 150) - 15 = 335.0
        assertEquals(335.0, valorTotalComDesconto, 0.01);
    }

    @Test
    public void testCalcularValorTotalSemDesconto() {
        // Criando uma lista de itens de pedido
        ItemPedido item1 = new ItemPedido("Produto 1", 100.0, 2);
        ItemPedido item2 = new ItemPedido("Produto 2", 50.0, 3);
        List<ItemPedido> itens = Arrays.asList(item1, item2);

        when(descontoService.calcularDesconto(Mockito.anyDouble())).thenReturn(0.0);

        Pedido pedido = new Pedido(itens, descontoService);

        double valorTotalSemDesconto = pedido.calcularValorTotal();

        // O valor total esperado sem desconto é (200 + 150) - 0 = 350.0
        assertEquals(350.0, valorTotalSemDesconto, 0.01);
    }

    @Test
    public void testCalcularValorTotalComDescontoMaiorQueTotal() {
        // Criando uma lista de itens de pedido
        ItemPedido item1 = new ItemPedido("Produto 1", 100.0, 2);
        ItemPedido item2 = new ItemPedido("Produto 2", 50.0, 3);
        List<ItemPedido> itens = Arrays.asList(item1, item2);

        when(descontoService.calcularDesconto(Mockito.anyDouble())).thenReturn(600.0);

        Pedido pedido = new Pedido(itens, descontoService);

        //double valorTotalComDesconto = pedido.calcularValorTotal();

        // O valor total não pode ser negativo, então deve ser 0.0
        assertThrows(IllegalArgumentException.class, pedido::calcularValorTotal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalcularValorTotalComDescontoNegativo() {
        // Criando uma lista de itens de pedido
        ItemPedido item1 = new ItemPedido("Produto 1", 100.0, 2);
        ItemPedido item2 = new ItemPedido("Produto 2", 50.0, 3);
        List<ItemPedido> itens = Arrays.asList(item1, item2);

        when(descontoService.calcularDesconto(Mockito.anyDouble())).thenReturn(400.0);

        Pedido pedido = new Pedido(itens, descontoService);

        pedido.calcularValorTotal();
    }

    @Test
    public void testCalcularValorTotalSemItens() {
        List<ItemPedido> itensVazios = new ArrayList<>();
        Pedido pedido = new Pedido(itensVazios, descontoService);

        double valorTotal = pedido.calcularValorTotal();

        assertEquals(0.0, valorTotal, 0.001);
    }

    @Test
    public void testCalcularValorTotalComDescontos() {
        // Criando itens de pedido com preços e quantidades
        ItemPedido item1 = new ItemPedido("Produto1", 10.0, 2);
        ItemPedido item2 = new ItemPedido("Produto2", 15.0, 3);

        // Criando uma lista de itens de pedido
        List<ItemPedido> itens = new ArrayList<>();
        itens.add(item1);
        itens.add(item2);

        DescontoService descontoService = Mockito.mock(DescontoService.class);

        when(descontoService.calcularDesconto(25.0)).thenReturn(5.0);

        Pedido pedido = new Pedido(itens, descontoService);

        // Verificando se o valor total é calculado corretamente com base no desconto simulado
        assertEquals(65.0, pedido.calcularValorTotal(), 0.001);
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

}
