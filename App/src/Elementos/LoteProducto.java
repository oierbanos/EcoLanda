package Elementos;

import java.util.Date;

public class LoteProducto {

    int cantidad_plantada;
    int cantidad_recogida;

    Date fecha_plantado;
    Date fecha_recogido;

    TipoProducto tipo;

    public LoteProducto (int cantidad_plantada, int cantidad_recogida, Date fecha_plantado, Date fecha_recogido, String nombre) {
        this.cantidad_plantada = cantidad_plantada;
        this.cantidad_recogida = cantidad_recogida;
        this.fecha_plantado = fecha_plantado;
        this.fecha_recogido = fecha_recogido;
        tipo = new TipoProducto(nombre);
    }

    public int getCantidad_plantada() {
        return cantidad_plantada;
    }

    public int getCantidad_recogida() {
        return cantidad_recogida;
    }

    public Date getFecha_plantado() {
        return fecha_plantado;
    }

    public Date getFecha_recogido() {
        return fecha_recogido;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public String getNombreTipo() {
        return tipo.getNombre();
    }

    public Object getFieldAt(int index) {
        switch (index) {
            case 0: return tipo.getNombre();
            case 1: return cantidad_plantada;
            case 2: return cantidad_recogida;
            case 3: return fecha_plantado;
            case 4: return fecha_recogido;
            default: return null;
        }
    }
}
