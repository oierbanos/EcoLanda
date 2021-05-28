package external_conexion.database;

public enum Query_Types {

    GET_USER("SELECT * FROM usuario WHERE username=?");

    private final String query;

    Query_Types(String query) {
        this.query = query;
    }

    public String GetQuery() {
        return this.query;
    }
}
