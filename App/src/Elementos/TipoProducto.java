package Elementos;

import java.util.Date;

public class TipoProducto {

    String nombre;
    int tiempo_crecimiento; // Dias
    int temperatura_ideal;  // ºC
    int humedad_ideal;      // %

    public TipoProducto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TipoProducto)) return false;
        return this.nombre.equals(((TipoProducto) (obj)).getNombre());
    }
}
