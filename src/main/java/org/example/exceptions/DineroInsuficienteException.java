package org.example.exceptions;

public class DineroInsuficienteException extends RuntimeException{
        public DineroInsuficienteException(String mensaje){
            super(mensaje);
        }
}
