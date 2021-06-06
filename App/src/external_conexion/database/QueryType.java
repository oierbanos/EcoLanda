package external_conexion.database;

/**
 * Tipo de querys que se pueden realizar en la base de datos mediante la aplicacion.
 */
public enum QueryType {

    GET_USER("SELECT * FROM usuario WHERE username=?"),

    GET_STOCK("SELECT l.cantidad_plantada, l.cantidad_recogida, l.fecha_plantar, l.fecha_recoger, tp.nombre\n" +
              "FROM lote_producto l\n" +
                    "JOIN tipo_producto tp on l.tipo_id = tp.id\n" +
                    "JOIN huerto h on l.huerto_id = h.id\n" +
              "WHERE h.id = ?"),

    GET_TIPOS("SELECT nombre FROM tipo_producto"),

    GET_SENSORES("SELECT m.valor, tm.nombre, m.fecha, m.hora\n" +
            "FROM medicion m\n" +
            "    JOIN tipo_medicion tm ON m.tipo_id = tm.id\n" +
            "WHERE m.huerto_id = ?"),

    FILTRO_TIPO("SELECT l.cantidad_plantada, l.cantidad_recogida, l.fecha_plantar, l.fecha_recoger, tp.nombre\n" +
                "FROM lote_producto l\n" +
                    "JOIN tipo_producto tp on l.tipo_id = tp.id\n" +
                    "JOIN huerto h on l.huerto_id = h.id\n" +
                "WHERE h.id = ? and tp.nombre = ?"),

    FILTRO_CANTIDAD_PLANTADA("SELECT l.cantidad_plantada, l.cantidad_recogida, l.fecha_plantar, l.fecha_recoger, tp.nombre\n" +
                "FROM lote_producto l\n" +
                    "JOIN tipo_producto tp on l.tipo_id = tp.id\n" +
                    "JOIN huerto h on l.huerto_id = h.id\n" +
                "WHERE h.id = ? and l.cantidad_plantada >= ?"),

    FILTRO_CANTIDAD_RECOGIDA("SELECT l.cantidad_plantada, l.cantidad_recogida, l.fecha_plantar, l.fecha_recoger, tp.nombre\n" +
                "FROM lote_producto l\n" +
                    "JOIN tipo_producto tp on l.tipo_id = tp.id\n" +
                    "JOIN huerto h on l.huerto_id = h.id\n" +
                "WHERE h.id = ? and l.cantidad_recogida >= ?"),

    FILTRO_FECHA_PLANTAR("SELECT l.cantidad_plantada, l.cantidad_recogida, l.fecha_plantar, l.fecha_recoger, tp.nombre\n" +
                "FROM lote_producto l\n" +
                    "JOIN tipo_producto tp on l.tipo_id = tp.id\n" +
                    "JOIN huerto h on l.huerto_id = h.id\n" +
                "WHERE h.id = ? and l.fecha_plantar = ?"),

    FILTRO_FECHA_RECOGER("SELECT l.cantidad_plantada, l.cantidad_recogida, l.fecha_plantar, l.fecha_recoger, tp.nombre\n" +
                "FROM lote_producto l\n" +
                    "JOIN tipo_producto tp on l.tipo_id = tp.id\n" +
                    "JOIN huerto h on l.huerto_id = h.id\n" +
                "WHERE h.id = ? and l.fecha_recoger = ?"),

    FILTRO_TIPO_SENSOR("SELECT m.valor, tm.nombre, m.fecha, m.hora\n" +
            "FROM medicion m\n" +
            "    JOIN tipo_medicion tm on m.tipo_id = tm.id\n" +
            "WHERE m.huerto_id = ? and tm.nombre = ?"),

    FILTRO_VALOR("SELECT m.valor, tm.nombre, m.fecha, m.hora\n" +
            "FROM medicion m\n" +
            "    JOIN tipo_medicion tm on m.tipo_id = tm.id\n" +
            "WHERE m.huerto_id = ? and m.valor >= ?"),

    FILTRO_HORA("SELECT m.valor, tm.nombre, m.fecha, m.hora\n" +
            "FROM medicion m\n" +
            "    JOIN tipo_medicion tm on m.tipo_id = tm.id\n" +
            "WHERE m.huerto_id = ? and m.hora = ?"),

    FILTRO_FECHA("SELECT m.valor, tm.nombre, m.fecha, m.hora\n" +
            "FROM medicion m\n" +
            "    JOIN tipo_medicion tm on m.tipo_id = tm.id\n" +
            "WHERE m.huerto_id = ? and m.fecha = ?"),

    RECENT("SELECT m.valor\n" +
            "FROM medicion m\n" +
            "    JOIN tipo_medicion tm on m.tipo_id = tm.id\n" +
            "WHERE m.huerto_id = ? && tm.nombre = ?\n" +
            "ORDER BY m.valor DESC\n" +
            "LIMIT 1"),

    HIGHEST_ID_MEDICION("SELECT id FROM medicion WHERE huerto_id = ? ORDER BY id DESC LIMIT 1");

    /**
     * Query a realizar.
     */
    private final String query;

    QueryType(String query) {
        this.query = query;
    }

    /**
     * Recibir el texto que forma la query.
     * @return La query.
     */
    public String getQuery() {
        return this.query;
    }
}
