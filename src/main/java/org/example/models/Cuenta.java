package org.example.models;

import org.example.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;

public class Cuenta {

    private String persona;
    private BigDecimal saldo;
    private Banco banco;

    public Cuenta(){

    }

    public Cuenta (String persona, BigDecimal saldo){
        this.persona = persona;
        this.saldo = saldo;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public void debito(BigDecimal monto){
        System.out.println("Monto a Retirar $" + monto);
        if(saldo.intValue() >= monto.intValue()) {
            this.saldo = this.saldo.subtract(monto);
        }
        else {
            throw new DineroInsuficienteException("Dinero Insuficiente");
        }
        System.out.println("Saldo en metodo debito : $" + this.saldo);
    }

    public void credito (BigDecimal credito){
        if(credito.intValue() >= 0)
           this.saldo = this.saldo.add(credito);
        System.out.println("Saldo en metodo credito : $" + this.saldo);
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Cuenta))
            return false;

        Cuenta c = (Cuenta) obj;

        if(this.persona == null || this.saldo == null)
            return false;

        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }
}
