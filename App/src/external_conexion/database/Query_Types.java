package external_conexion.database;

public enum Query_Types {

    GET_USER("SELECT * FROM usuario WHERE username=?"),

    GET_STOCK("SELECT l.cantidad_plantada, l.cantidad_recogida, l.fecha_plantar, l.fecha_recoger, tp.nombre\n" +
              "FROM lote_producto l\n" +
                    "JOIN tipo_producto tp on l.tipo_id = tp.id\n" +
                    "JOIN huerto h on l.huerto_id = h.id\n" +
              "WHERE h.id = ?"),

    GET_TIPOS("SELECT nombre FROM tipo_producto"),

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
                "WHERE h.id = ? and l.fecha_recoger = ?");

    private final String query;

    Query_Types(String query) {
        this.query = query;
    }

    public String GetQuery() {
        return this.query;
    }
}
