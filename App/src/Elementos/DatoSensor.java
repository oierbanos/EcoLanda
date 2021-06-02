package Elementos;

public class DatoSensor {

    private String tipo;
    private double valor;
    private Time tiempo;

    public DatoSensor(String tipo, double valor, Time tiempo) {
        this.tipo = tipo;
        this.valor = valor;
        this.tiempo = tiempo;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public String getTiempoString() {
        return tiempo.toString();
    }

    public String getHora() {
        return tiempo.getHora();
    }

    public Object getFieldAt(int index) {
        switch (index) {
            case 0: return tipo;
            case 1: return valor;
            case 2: return tiempo.getHora();
            default: return null;
        }
    }
}
