package org.example;

public class ItemPedido {

    private String nomeProduto;
    private double precoUnitario;
    private int quantidade;

    public ItemPedido(String nomeProduto, double precoUnitario, int quantidade) {
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    // Método para calcular o subtotal do item
    public double getSubtotal() {
        return precoUnitario * quantidade;
    }

    // Getters e setters (se necessário) para os atributos

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
