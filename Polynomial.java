public class Polynomial {

    private double[] coefficents;
    public Polynomial() {
        coefficents = new double[1];
        coefficents[0] = 0;
    }

    public Polynomial(double[] coefficents) {
        this.coefficents = coefficents;
    }

    public void setCoefficents(double[] coefficents) {
        this.coefficents = coefficents;
    }

    public double[] getCoefficents() {
        return coefficents;
    }

    public Polynomial add(Polynomial p1) {
        int smallerArr = -1;
        int smallArrLength = 0;
        double[] newCoefficents;
        if (coefficents.length > p1.getCoefficents().length) {
            smallerArr = 1;
            smallArrLength = p1.getCoefficents().length;
            newCoefficents = new double[coefficents.length];
        }
        else {
            smallerArr = 0;
            smallArrLength = coefficents.length;
            newCoefficents = new double[p1.getCoefficents().length];
        }
        for(int i=0; i < smallArrLength; i++) {
            newCoefficents[i] = p1.getCoefficents()[i] + coefficents[i];
        }
        for(int i=smallArrLength; i < newCoefficents.length; i++) {
            if (smallerArr == 1) {
                newCoefficents[i] = coefficents[i];
            }
            else {
                newCoefficents[i] = p1.getCoefficents()[i];
            }
        }
        return new Polynomial(newCoefficents);
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