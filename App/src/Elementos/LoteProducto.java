package Elementos;

import java.util.Date;

public class LoteProducto {

    /**
     * Cantidad de semillas/plantas que se han plantado.
     * Se mide en unidades.
     */
    int cantidad_plantada;

    /**
     * Cantidad de productos que se han recogido.
     * Se mide en kilogramos y puede ser nulo.
     */
    int cantidad_recogida;

    /**
     * Dia en el que se han plantado los productos.
     */
    Date fecha_plantado;
    /**
     * Fecha en la que se han plantado los productos.
     * Puede ser nulo.
     */
    Date fecha_recogido;

    /**
     * Tipo de producto que se ha plantado.
     */
    TipoProducto tipo;

    /**
     * Un lote de productos se trata de un conjunto de productos que han sido plantados
     * en una zona de un huerto.
     * @param cantidad_plantada Cantidad de semillas/plantas que se han plantado.
     * @param cantidad_recogida Cantidad de productos que se han recogido.
     * @param fecha_plantado Dia en el que se han plantado los productos.
     * @param fecha_recogido Fecha en la que se han plantado los productos.
     * @param nombre Tipo de producto que se ha plantado.
     */
    public LoteProducto (int cantidad_plantada, int cantidad_recogida, Date fecha_plantado, Date fecha_recogido, String nombre) {
        this.cantidad_plantada = cantidad_plantada;
        this.cantidad_recogida = cantidad_recogida;
        this.fecha_plantado = fecha_plantado;
        this.fecha_recogido = fecha_recogido;
        tipo = new TipoProducto(nombre);
    }

    /**
     * Recibir la cantidad de productos plantados que forman parte del lote.
     * @return Cantidad de productos plantados.
     */
    public int getCantidad_plantada() {
        return cantidad_plantada;
    }

    /**
     * Recibir la cantidad de productos recogidos que forman parte del lote.
     * @return Cantidad de productos recogidos.
     */
    public int getCantidad_recogida() {
        return cantidad_recogida;
    }

    /**
     * Recibir la fecha en la que se planto un lote de productos.
     * @return Fecha en la que se planto.
     */
    public Date getFecha_plantado() {
        return fecha_plantado;
    }

    /**
     * Recibir la fecha en la que se recogio un lote de productos.
     * @return Fecha en la que se recogio.
     */
    public Date getFecha_recogido() {
        return fecha_recogido;
    }

    /**
     * Recibir el tipo de producto del lote.
     * @return Tipo de prodcuto.
     */
    public TipoProducto getTipo() {
        return tipo;
    }

    /**
     * Recibir el nombre del tipo de producto del lote.
     * @return Nombre del tipo.
     */
    public String getNombreTipo() {
        return tipo.getNombre();
    }

    /**
     * Obtener el paremetro del objeto para mostrar en la columna de la tabla.
     * @param index Columna de la tabla.
     * @return Parametro que va en la columna de la tabla.
     */
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
