import java.util.List;
import java.util.Random;

public class Aufg1 {
    interface IFunction {
        double getVal(double x);
    }

    static class SquareFunction implements IFunction {
        public double getVal(double x){
            return x * x;
        }
    }
    static class SqrtFunction implements IFunction{
        public double getVal(double x){
            return Math.sqrt(x);
        }
    }

    public static void printFunction(IFunction func, String name){
        System.out.println("Selected function: " + name);
        System.out.println("x\t\tf(x)");
        System.out.println("---------------------------");
        for(double x = 1.0; x <= 10.0; x++){
            System.out.printf("%.1f\t\t%.4f%n", x, func.getVal(x));
        }
    }

    public static void main(String[] args){
        List<FunctionEntry> functions = List.of(
                new FunctionEntry("x^2", new SquareFunction()),
                new FunctionEntry("√x", new SqrtFunction()),
                new FunctionEntry("2x", new IFunction(){
                    public double getVal(double x){
                        return 2 * x;
                    }
                }),
                new FunctionEntry("e^x", Math::exp)
        );

        Random random = new Random();
        int index = random.nextInt(functions.size());
        FunctionEntry selected = functions.get(index);

        printFunction(selected.function, selected.name);
    }
    static class FunctionEntry{
        String name;
        IFunction function;

        FunctionEntry(String name, IFunction function){
            this.name = name;
            this.function = function;
        }
    }

}
