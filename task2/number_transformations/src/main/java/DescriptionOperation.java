public enum DescriptionOperation {
    ADDITION("K2"),
    MULTIPLICATION("K1");

    private String code;

    DescriptionOperation(String code){
        this.code = code;
    }
    public String getCode(){ return code;}
}
