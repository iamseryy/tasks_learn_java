package core;

@FunctionalInterface
public interface Operate <T extends Number>{
    T simpleOperate(T number1, T number2);
}
