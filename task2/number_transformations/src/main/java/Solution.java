import java.util.List;

public class Solution <T extends Number>{
    private List<Operation<T>> operations;
    private T result;

    public Solution(List<Operation<T>> operations, T result) {
        this.operations = operations;
        this.result = result;
    }

    public List<Operation<T>> getOperations() {
        return operations;
    }

    public T getResult() {
        return result;
    }

    public void setOperations(List<Operation<T>> operations) {
        this.operations = operations;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
