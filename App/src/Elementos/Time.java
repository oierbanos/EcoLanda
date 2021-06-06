package Elementos;

import errors.TimeException;

import java.util.Date;

/**
 * Fecha y hora de un suceso.
 */
public class Time {

    /**
     * Hora.
     */
    int hora;
    /**
     * Minutos.
     */
    int minuto;
    /**
     * Segundos.
     */
    int segundo;
    /**
     * Dia, mes y año.
     */
    Date fecha;

    /**
     * Registro de fecha y hora.
     * @param fecha Dia, mes y año.
     * @param hora Hora.
     * @param minuto Minutos.
     * @param segundo Segundos.
     * @throws TimeException Si el formato de fecha no es correcto lanza una excepcion.
     */
    public Time (Date fecha, int hora, int minuto, int segundo) throws TimeException {
        this.fecha = fecha;
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;

        // Comprobar que el formato de hora es correcto.
        if (hora > 24 || hora < 0 || minuto > 60 || minuto < 0 || segundo > 60 || segundo < 0) {
            throw new TimeException("El valor de la fecha no es correcto");
        }
    }

    /**
     * Obtener la hora en string separado por ':'.
     * @return hh:mm:ss
     */
    public String getHora() {
        return hora + ":" + minuto + ":" + segundo;
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
