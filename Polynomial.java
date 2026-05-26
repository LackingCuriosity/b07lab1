import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
public class Polynomial {

    private double[] coefficents;
    private double[] exponents;
    public Polynomial() {
        coefficents = new double[1];
        exponents = new double[1];
        coefficents[0] = 0;
    }

    public Polynomial(double[] coefficents, double[] exponents) {
        this.coefficents = coefficents;
        this.exponents = exponents;
    }

    public Polynomial (File f) throws Exception{
        Scanner input = new Scanner(f);
        String s = input.nextLine();
        input.close();
        String[] sArr = s.split("[+-]");
        double negativeFactor = 1;
        if (s.charAt(0) == '-') {
            negativeFactor = -1;
        }
        this.coefficents = new double[100];
        this.exponents = new double[100];
        int k = 0;
        for(String term : sArr) {
            String[] coef_expo = term.split("x");
            try {
                this.coefficents[k] = Double.parseDouble(coef_expo[0]) * negativeFactor;
                if (coef_expo.length > 1) {
                    this.exponents[k] = Double.parseDouble(coef_expo[1]);
                }
                else {
                    this.exponents[k] = 0;
                }
            }
            catch (Exception e){
                System.out.println("Oops somethign went wrong");
            }
            k++;
        }
    }

    public void setCoefficents(double[] coefficents) {
        this.coefficents = coefficents;
    }

    public double[] getCoefficents() {
        return coefficents;
    }

    public double[] getExponents() {
        return exponents;
    }

    public void setExponents(double[] exponents) {
        this.exponents = exponents;
    }

    public Polynomial add(Polynomial p1) {
        double[] p1Coefficents = p1.getCoefficents();
        double[] p1Exponents = p1.getExponents();
        double[] resExponents = new double[p1Exponents.length + exponents.length];
        double[] resCoefficents = new double[p1Exponents.length + exponents.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < exponents.length && j <p1Exponents.length) {
            if (exponents[i] == p1Exponents[j]) {
                resCoefficents[k] = p1Coefficents[i] + coefficents[j];
                resExponents[k] = p1Exponents[j];
                i++;
                j++;
                k++;
            }
            else if (exponents[i] > p1Exponents[j]) {
                resExponents[k] = p1Exponents[j];
                resCoefficents[k] = p1Coefficents[j];
                j++;
                k++;
            }
            else {
                resExponents[k] = exponents[j];
                resCoefficents[k] = coefficents[j];
                i++;
                k++;
            }
        }
        while (i >= exponents.length && j < p1Exponents.length) {
                resExponents[k] = p1Exponents[j];
                resCoefficents[k] = p1Coefficents[j];
                j++;
                k++;
        }
        while (j >= exponents.length && i < p1Exponents.length) {
                resExponents[k] = exponents[i];
                resCoefficents[k] = coefficents[i];
                i++;
                k++;
        }
        double[] finalCoefficents = new double[k];
        double[] finalExponents = new double[k];
        for(int l=0; i < k; l++) {
            finalCoefficents[l] = resCoefficents[l];
            finalExponents[l] = resExponents[l];
        }
        return new Polynomial(finalCoefficents, finalExponents);

    }

    private int contains(double x, double[] arr) {
        for(int i=0; i < arr.length; i++) {
            if (arr[i] == x) {
                return i;
            }
        }
        return -1;
    }

    public Polynomial multiply(Polynomial p1) {
        double resExponents[] = new double[100000];
        double resCoefficents[] = new double[100000];
        int k = 0;
        for(int i = 0; i < exponents.length; i++) {
            for(int j=0; j < p1.getExponents().length; j++) {
                double coef = coefficents[i] * p1.getCoefficents()[j];
                double expo = exponents[i] + p1.getExponents()[j];
                int index = contains(expo, resExponents);
                if(index != -1) {
                    resCoefficents[index] += coef;
                }
                else {
                    resCoefficents[k] = coef;
                    resExponents[k] = expo;
                    k++;
                }
            }
        }
        return new Polynomial(resCoefficents, resExponents);
    }

    public void saveToFile (String fileName) throws Exception{
        FileWriter writer = new FileWriter(new File(fileName));
        for(int i =0; i<coefficents.length; i++) {
            if (coefficents[i] < 0) {
                writer.append(String.valueOf(coefficents[i]));
            }
            else {
                writer.append("+" + coefficents[i]);
            }
            if (exponents[i] > 0) {
                writer.append("x" + exponents[i]);
            }
        }
        writer.close();
    }

    public double evaluate(double x) {
        double result = 0;
        for(int i=0; i < coefficents.length; i++) {
            result += coefficents[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }




}