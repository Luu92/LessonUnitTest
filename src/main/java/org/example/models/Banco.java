package org.example.models;

import java.math.BigDecimal;
import java.util.List;

public class Banco {

    private List<Cuenta> cuentas;
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public void addCuenta(Cuenta cuenta){
        cuentas.add(cuenta);
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
