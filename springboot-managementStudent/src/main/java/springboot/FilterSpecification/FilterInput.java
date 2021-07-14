package springboot.FilterSpecification;


public class FilterInput {

    private String field;
    private Object value;
    private OperationQuery operation;
    private String operationCustom;

    public FilterInput(String field, Object value, OperationQuery operation) {
        this.field = field;
        this.value = value;
        this.operation = operation;
    }

    public String getOperationCustom() {
        return operationCustom;
    }

    public void setOperationCustom(String operationCustom) {
        this.operationCustom = operationCustom;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
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

    public FilterInput(String field, Object value, String operation) {
        this.field = field;
        this.value = value;
        this.operationCustom = operation;
    }
}
