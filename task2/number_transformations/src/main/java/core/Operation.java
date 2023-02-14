package core;

public class Operation <T extends Number>{
    private Operate<T> operate;
    private T operateParam;
    private DescriptionOperation descriptionOperation;

    public Operation(Operate operate, T operateParam, DescriptionOperation descriptionOperation) {
        this.operate = operate;
        this.operateParam = operateParam;
        this.descriptionOperation = descriptionOperation;
    }

    public Operate getOperate() {
        return operate;
    }

    public void setOperate(Operate operate) {
        this.operate = operate;
    }

    public DescriptionOperation getDescriptionOperation() {
        return descriptionOperation;
    }

    public void setDescriptionOperation(DescriptionOperation descriptionOperation) {
        this.descriptionOperation = descriptionOperation;
    }

    public T getOperateParam() {
        return operateParam;
    }

    public void setOperateParam(T operateParam) {
        this.operateParam = operateParam;
    }
}
