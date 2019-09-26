package Section;

public class Query {
    private boolean quick;
    private boolean sensitive;
    private String query;

    Query(boolean quick, boolean sensitive, String query) {
        this.quick = quick;
        this.sensitive = sensitive;
        this.query = query;
    }

    public boolean isQuick() {
        return quick;
    }
    public boolean isSensitive() {
        return sensitive;
    }
    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "quick is " + quick + "\n" + "case is "+ sensitive + "\nquery is: " + query;
    }
}
