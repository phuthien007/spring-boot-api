package springboot.FilterSpecification;


public class FilterInput {

    private String field;
    private String value;
    private OperationQuery operation;

    public FilterInput(String field, String value, OperationQuery operation) {
        this.field = field;
        this.value = value;
        this.operation = operation;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public OperationQuery getOperation() {
        return operation;
    }

    public void setOperation(OperationQuery operation) {
        this.operation = operation;
    }
}
