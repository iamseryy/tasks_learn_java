import java.util.List;

public class Solution <T extends Number>{
    private List<Operation<T>> operations;
    private Integer result;

    public Solution(List<Operation<T>> operations, Integer result) {
        this.operations = operations;
        this.result = result;
    }

    public List<Operation<T>> getOperations() {
        return operations;
    }

    public Integer getResult() {
        return result;
    }

    public void setOperations(List<Operation<T>> operations) {
        this.operations = operations;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
