package Elementos;

import java.util.Date;

/**
 * Conjunto de productos de un mismo tipo.
 */
public class LoteProducto {

    /**
     * Cantidad de semillas/plantas que se han plantado.
     * Se mide en unidades.
     */
    int cantidadPlantada;

    /**
     * Cantidad de productos que se han recogido.
     * Se mide en kilogramos y puede ser nulo.
     */
    int cantidadRecogida;

    /**
     * Dia en el que se han plantado los productos.
     */
    Date fechaPlantado;
    /**
     * Fecha en la que se han plantado los productos.
     * Puede ser nulo.
     */
    Date fechaRecogido;

    /**
     * Tipo de producto que se ha plantado.
     */
    TipoProducto tipo;

    /**
     * Un lote de productos se trata de un conjunto de productos que han sido plantados
     * en una zona de un huerto.
     * @param cantidad_plantada Cantidad de semillas/plantas que se han plantado.
     * @param cantidadRecogida Cantidad de productos que se han recogido.
     * @param fechaPlantado Dia en el que se han plantado los productos.
     * @param fechaRecogido Fecha en la que se han plantado los productos.
     * @param nombre Tipo de producto que se ha plantado.
     */
    public LoteProducto (int cantidad_plantada, int cantidadRecogida, Date fechaPlantado, Date fechaRecogido, String nombre) {
        this.cantidadPlantada = cantidad_plantada;
        this.cantidadRecogida = cantidadRecogida;
        this.fechaPlantado = fechaPlantado;
        this.fechaRecogido = fechaRecogido;
        tipo = new TipoProducto(nombre);
    }

    /**
     * Recibir la cantidad de productos plantados que forman parte del lote.
     * @return Cantidad de productos plantados.
     */
    public int getCantidadPlantada() {
        return cantidadPlantada;
    }

    /**
     * Recibir la cantidad de productos recogidos que forman parte del lote.
     * @return Cantidad de productos recogidos.
     */
    public int getCantidadRecogida() {
        return cantidadRecogida;
    }

    /**
     * Recibir la fecha en la que se planto un lote de productos.
     * @return Fecha en la que se planto.
     */
    public Date getFechaPlantado() {
        return fechaPlantado;
    }

    /**
     * Recibir la fecha en la que se recogio un lote de productos.
     * @return Fecha en la que se recogio.
     */
    public Date getFechaRecogido() {
        return fechaRecogido;
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
            case 1: return cantidadPlantada;
            case 2: return cantidadRecogida;
            case 3: return fechaPlantado;
            case 4: return fechaRecogido;
            default: return null;
        }
    }
}
