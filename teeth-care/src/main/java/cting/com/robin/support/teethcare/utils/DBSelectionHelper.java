package cting.com.robin.support.teethcare.utils;


public class DBSelectionHelper {

    public static final String getMaxClause(String table,String column) {
        StringBuilder where = new StringBuilder();
        where.append(column)
                .append("=(select MAX(")
                .append(column)
                .append(") from ")
                .append(table)
                .append(")");
        return where.toString();
    }
}
