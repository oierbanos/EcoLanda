package Elementos;

public class DatoSensor {

    /**
     * Tipo de sensor que ha hecho la medicion.
     */
    private String tipo;

    /**
     * Valor de la medicion.
     */
    private double valor;

    /**
     * Fecha y hora de cuando se hizo la medicion.
     */
    private Time tiempo;

    /**
     * Registra una medicion hecha por un sensor.
     * @param tipo Tipo de sensor que ha hecho la medicion.
     * @param valor Valor de la medicion.
     * @param tiempo Fecha y hora de cuando se hizo la medicion.
     */
    public DatoSensor(String tipo, double valor, Time tiempo) {
        this.tipo = tipo;
        this.valor = valor;
        this.tiempo = tiempo;
    }

    /**
     * Obtener el tipo del sensor que ha hecho la medicion.
     * @return Tipo de sensor.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Obtener el valor de medicion.
     * @return El valor de la medicion.
     */
    public double getValor() {
        return valor;
    }

    /**
     * Obtener la fecha y la hora de la medicion.
     * Formato: yyyy:mm:dd-hh:mm:ss
     * @return yyyy:mm:dd-hh:mm:ss
     */
    public String getTiempoString() {
        return tiempo.toString();
    }

    /**
     * Obtener la hora, minutos y segundos en el formato hh:mm:ss.
     * @return hh:mm:ss.
     */
    public String getHora() {
        return tiempo.getHora();
    }

    /**
     * Obtener el paremetro del objeto para mostrar en la columna de la tabla.
     * @param index Columna de la tabla.
     * @return Parametro que va en la columna de la tabla.
     */
    public Object getFieldAt(int index) {
        switch (index) {
            case 0: return tipo;
            case 1: return valor;
            case 2: return tiempo.getHora();
            default: return null;
        }
    }
}
