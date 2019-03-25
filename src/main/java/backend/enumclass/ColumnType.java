package backend.enumclass;

/**
 * Created by lienming on 2019/1/17.
 */
public enum ColumnType {
    INT("int"),
    BIGINT("bigint"),
    DOUBLE("double"),
    DECIMAL("decimal"),
    STRING("mediumtext"),
    BOOLEAN("boolean"),
    DATETIME("datetime"),
    VARCHAR("varchar")
    ;

    public String sqltype;

    ColumnType(String sqltype) {
        this.sqltype = sqltype;
    }

    public String toMySqlString(){
        return this.sqltype;
    }
}
