package com.TicketIT.Model;

public class Invoice {

    private String id;

    private Double amount;

    private String cardId;

    private Boolean paid;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public Double getAmount() { return amount; }

    public void setAmount(Double amount) { this.amount = amount; }

    public String getCardId() { return cardId; }

    public void setCardId(String card) { this.cardId = card; }

    public Boolean getPaid() { return paid; }

    public void setPaid(Boolean paid) { this.paid = paid; }

}
