package com.TicketIT.Model;

import java.util.Date;
import java.util.UUID;

public class Card {

    private String id;

    private String type;

    private String holder;

    private String number;

    private String expiry;

    private String securityCode;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getHolder() { return holder; }

    public void setHolder(String holder) { this.holder = holder; }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public String getExpiry() { return expiry; }

    public void setExpiry(String expiry) { this.expiry = expiry; }

    public String getSecurityCode() { return securityCode; }

    public void setSecurityCode(String securityCode) { this.securityCode = securityCode; }

}
