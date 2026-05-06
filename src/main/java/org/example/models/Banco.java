package org.example.models;

import java.math.BigDecimal;

public class Banco {

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void transferir(Cuenta origen, Cuenta destino, BigDecimal monto){
        if(monto.intValue() > 0 && monto.intValue() <= origen.getSaldo().intValue()){
            //destino.setSaldo( destino.getSaldo().add(monto) );
            //origen.setSaldo( origen.getSaldo().subtract(monto) );
            destino.credito(monto);
            origen.debito(monto);
        }
    }
}
