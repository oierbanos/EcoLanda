package Elementos;

/**
 * El tipo de un lote de productos.
 */
public class TipoProducto {

    /**
     * Nombre del tipo de producto.
     */
    String nombre;
    /**
     * Tiempo aproximado que tarda en crecer el tipo de productos.
     * Tiempo en dias.
     */
    int tiempo_crecimiento;
    /**
     * Temperatura ideal a la que se tiene que cultivar un producto.
     * Temperatura en ºC.
     */
    int temperatura_ideal;
    /**
     * Humedad ideal a la que se tiene que cultivar un producto.
     * Humedad en %.
     */
    int humedad_ideal;

    /**
     * Un tipo de producto que se cultiva en un huerto.
     * @param nombre Nombre del tipo de producto.
     */
    public TipoProducto(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Recibir el nombre del producto.
     * @return Nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Comparar dos tipos de producto.
     * @param obj Tipo de producto con el que se va a comparar.
     * @return Si el nombre es el mismo devolver true, sino false.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TipoProducto)) return false;
        return this.nombre.equals(((TipoProducto) (obj)).getNombre());
    }
}
