public class Complex {
    private double real;
    private double imagine;

    public Complex() {
        real = 0;
        imagine = 0;
    }


    public Complex(double real, double imagine) {
        this.real = real;
        this.imagine = imagine;
    }

    public Complex(Complex other) {
        real = other.real;
        imagine = other.imagine;
    }

    public boolean equals(Complex other) {
        return this.real == other.real && this.imagine == other.imagine;
    }

    @Override
    public String toString() {
        String info = "";
        if (real == 0 && imagine == 0) {
            info = "0";
            return info;
        }

        if (real == 0) {
            info += imagine + "i";
            return info;
        }

        info += real;

        if (imagine > 0) {
            info += "+" + imagine + "i";
        } else if (imagine < 0) {
            info += imagine + "i";
        }

        return info;
    }

    public double getReal() { return real; }
    public double getImagine() { return imagine; }
    public void setReal(double value) { real = value; }
    public  void setImagine(double value) { imagine = value; }


    public Complex add(Complex other) {
        return new Complex(this.real + other.real,
                this.imagine + other.imagine);
    }


    public Complex subtract(Complex other) {
        return new Complex(this.real - other.real,
                this.imagine - other.imagine);
    }

    public Complex multiply(Complex other) {
        var newReal = this.real * other.real - this.imagine * other.imagine;
        var newImagine = this.real * other.imagine + this.imagine * other.real;
        return new Complex(newReal, newImagine);
    }

    public Complex setScale(double lambda) {
        return new Complex(real * lambda, imagine * lambda);
    }

    public Complex conjugate() {
        return new Complex(real, -imagine);
    }

    public Complex divideBy(Complex other) {
        if (other.equals(new Complex(0, 0)))
            throw new ArithmeticException("/ by zero");
        Complex _other = other.conjugate();
        var divisor = other.multiply(_other).real;
        Complex result = this.multiply(_other);
        result.real /= divisor;
        result.imagine /= divisor;
        return result;
    }

    public double module() {
        return Math.sqrt(this.real * this.real + this.imagine * this.imagine);
    }

    public double argument() {
        if (real == 0 && imagine > 0)
            return Math.PI / 2;
        else if (real == 0 && imagine < 0)
            return -Math.PI / 2;
        return Math.atan(imagine / real);
    }
}
