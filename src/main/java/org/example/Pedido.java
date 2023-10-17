package org.example;

import java.util.List;

public class Pedido {

    private final List<ItemPedido> itens;
    private final DescontoService descontoService;
    private int calcularDescontoChamado = 0;

    public Pedido(List<ItemPedido> itens, DescontoService descontoService) {
        this.itens = itens;
        this.descontoService = descontoService;
    }

    public double calcularValorTotal() {
        double valorTotal = 0.0;
        for (ItemPedido item : itens) {
            valorTotal += item.getSubtotal();
        }

        double desconto = descontoService.calcularDesconto(valorTotal);
        calcularDescontoChamado++;

        double valorFinal = valorTotal - desconto;

        if (valorFinal < 0) {
            throw new IllegalArgumentException("O valor total não pode ser negativo.");
        }

        return valorFinal;
    }

    public int getCalcularDescontoChamado() {
        return calcularDescontoChamado;
    }

}
