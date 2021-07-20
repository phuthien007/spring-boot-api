package springboot.FilterSpecification;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterInput {

    private String field;
    private Object value;
    private OperationQuery operation;
    private String operationCustom;
    private Map<String, Map<String, List<String>>> valueMap = new HashMap<>();

    public String getOperationCustom() {
        return operationCustom;
    }

    public void setOperationCustom(String operationCustom) {
        this.operationCustom = operationCustom;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Map<String, Map<String, List<String>>> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, Map<String, List<String>>> valueMap) {
        this.valueMap = valueMap;
    }


    public FilterInput(String field, Object value, OperationQuery operation) {
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

    public FilterInput(String field, Map<String, Map<String, List<String>>> value, OperationQuery operation) {
        this.field = field;
        this.operation = operation;
        this.valueMap = value;
    }

    public FilterInput(String field, String value, String operation) {
        this.field = field;
        this.value = value;
        this.operationCustom = operation;
    }
}
