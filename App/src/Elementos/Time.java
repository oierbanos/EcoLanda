package Elementos;

import errors.TimeException;

import java.util.Date;

public class Time {

    /**
     * Hora.
     */
    int hora;
    /**
     * Minutos.
     */
    int minutos;
    /**
     * Segundos.
     */
    int segundos;
    /**
     * Dia, mes y año.
     */
    Date fecha;

    /**
     * Registro de fecha y hora.
     * @param fecha Dia, mes y año.
     * @param hora Hora.
     * @param minutos Minutos.
     * @param segundos Segundos.
     * @throws TimeException Si el formato de fecha no es correcto lanza una excepcion.
     */
    public Time (Date fecha, int hora, int minutos, int segundos) throws TimeException {
        this.fecha = fecha;
        this.hora = hora;
        this.minutos = minutos;
        this.segundos = segundos;

        // Comprobar que el formato de hora es correcto.
        if (hora > 24 || hora < 0 || minutos > 60 || minutos < 0 || segundos > 60 || segundos < 0) {
            throw new TimeException("El valor de la fecha no es correcto");
        }
    }

    /**
     * Obtener la hora en string separado por ':'.
     * @return hh:mm:ss
     */
    public String getHora() {
        return hora + ":" + minutos + ":" + segundos;
    }

    /**
     * Obtener la fecha y la hora en string.
     * @return yyyy:mm:dd-hh:mm:ss
     */
    @Override
    public String toString() {
        return fecha.toString() + "-" + getHora();
    }
}
