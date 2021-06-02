package Elementos;

import errors.TimeException;

import java.util.Date;

public class Time {

    int hora;
    int minutos;
    int segundos;
    Date fecha;

    public Time (Date fecha, int hora, int minutos, int segundos) throws TimeException {
        this.fecha = fecha;
        this.hora = hora;
        this.minutos = minutos;
        this.segundos = segundos;

        if (hora > 24 || hora < 0 || minutos > 60 || minutos < 0 || segundos > 60 || segundos < 0) {
            throw new TimeException("El valor de la fecha no es correcto");
        }
    }

    public String getHora() {
        return hora + ":" + minutos + ":" + segundos;
    }

    @Override
    public String toString() {
        return fecha.toString() + "-" + getHora();
    }
}
