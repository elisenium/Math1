public class Polynome {

    private static final int MAX_ETAPE = 1000;
    private double[] coefficients; // Coefficients du polynôme
    private int degre; // Degré du polynôme

    // Crée le polynôme x^degre
    // Lance une IllegalArgumentException si degre est négatif
    public Polynome(int degre) throws IllegalArgumentException {
        if (degre < 0) throw new IllegalArgumentException("Le degré du polynôme doit être positif");
        this.degre = degre;
        this.coefficients = new double[degre + 1];
        for (int i = 0; i < degre; i++) {
            this.coefficients[i] = 0;
        }
        this.coefficients[this.degre] = 1;
    }

    // Crée le polynôme nul
    public Polynome() {
        this.degre = 0;
        this.coefficients = new double[1];
        this.coefficients[0] = 0;
    }

    // Assigne la valeur coefficient au coefficient de degré degre du polynome
    // Lance une IllegalArgumentException si degre est négatif ou supérieur au degré du polynôme
    // Lance une IllegalArgumentException si on tente de changer le degré du polynôme
    public void setCoefficient(int degre, double coefficient) throws IllegalArgumentException {
        if (degre < 0 || degre > this.degre) throw new IllegalArgumentException("degré invalide");
        if (degre == this.degre && coefficient == 0 && this.degre != 0) {
            throw new IllegalArgumentException("Le coefficient de degré dominant ne peut pas être nul");
        }
        this.coefficients[degre] = coefficient;
    }

    // Renvoie la valeur du polynôme évalué en x
    public double evalueEn(double x) {
        double result = this.coefficients[this.degre];
        for (int i = this.degre - 1; i >= 0; i--) {
            result = result * x + this.coefficients[i];
        }
        return result;
    }

    // Renvoie la valeur du degré dominant du polynôme
    public int getDegre() {
        return degre;
    }

    // Renvoie le polynôme dérivé de this
    public Polynome polynomeDerive() {
        try {
            Polynome p = new Polynome(this.degre - 1);
            for (int i = 0; i < this.degre; i++) {
                p.coefficients[i] = (i + 1) * this.coefficients[i + 1];
            }
            return p;
        } catch (IllegalArgumentException e) {
            return new Polynome();
        }
    }

    // renvoie une description du polynôme courant, sous forme de chaine de caractères
    public String toString() {
        if (degre == 0)
            return "" + this.coefficients[0];
        String result = "" + this.coefficients[degre] + " x^" + degre;
        for (int i = degre - 1; i > 0; i--) {
            if (this.coefficients[i] < 0) {
                result += " - " + Math.abs(this.coefficients[i])  ;
                if (i!=1) {
                    result += " x^" + i;
                } else {
                    result += " x" ;
                }
            } else if (this.coefficients[i] > 0) {
                result += " + " + this.coefficients[i] ;
                if (i!=1) {
                    result += " x^" + i;
                } else {
                    result += " x" ;
                }
            }
        }
        if (this.coefficients[0] < 0) {
            result += " - " + Math.abs(this.coefficients[0]);
        } else if (this.coefficients[0] > 0) {
            result += " + " + this.coefficients[0];
        }

        return result;
    }

    // Renvoie une approximation de la racine contenue dans l'intervalle [a ; b] ou [b ; a],
    // obtenue par n intération de la méthode de la bissection
    // Lance une NumeriqueException si la présence d'une racine n'est pas garantie
    public double racineParBissectionNIterations(double a, double b, int n) throws NumeriqueException {
        // TO DO
        //1) Permutation des valeurs de a et b
        if (a > b) {
            double temp = a;
            a = b;
            b = temp;
        }
        //2) Vérification qu'une des bornes n'est pas la racine
        if (evalueEn(a) == 0) return a;
        if (evalueEn(b) == 0) return b;

        //3) Vérification de Bolzano
        if (evalueEn(a) * evalueEn(b) > 0) throw new NumeriqueException();

        //4) Approximation initiale
        double racine = (a+b)/2;

        //5) Itération
        for (int i = 1; i <= n; i++){
            //a) Vérification que l'approximation n'est pas la racine
            if (evalueEn(racine) == 0) return racine;

            //b) Mise à jour de l'intervalle [a, b]
            if (evalueEn(a) * evalueEn(racine) > 0){
                a = racine;
            } else {
                b = racine;
            }
            //c) Calcul de la nouvelle approximation
            racine = (a+b)/2;
        }
        //6) Renvoie de l'approximation obtenue
        return racine;
    }

    // Renvoie une approximation à minimum d décimales exactes de la racine contenue dans
    // l'intervalle [a ; b] ou [b ; a], obtenue par la méthode de la bissection
    // Lance une NumeriqueException en cas de problème
    public double racineParBissection(double a, double b, int d) throws NumeriqueException {
        // TODO
        //1) Permutation des valeurs de a et b
        if (a > b) {
            double temp = a;
            a = b;
            b = temp;
        }
        //2) Vérification qu'une des bornes n'est pas la racine
        if (this.evalueEn(a) == 0) return a;
        if (this.evalueEn(b) == 0) return b;

        //3) Vérification de Bolzano
        if (evalueEn(a) * evalueEn(b) > 0) throw new NumeriqueException();

        //4) Calcul du nombre d'itérations
        double n = Math.log(Math.pow(10,-d)*(b-a));

        if (n > MAX_ETAPE) {
            throw new NumeriqueException();
        }

        //5) Approximation initiale
        double racine = (a+b)/2;

        //6) Itération

        for (int i = 1; i < MAX_ETAPE; i++) {
            //a) Vérification que l'approximation n'est pas la racine
                if (evalueEn(racine) == 0) {
                    racine = racine + (a+b)/2;
                    return racine;
            }

            //b) Mise à jour de l'intervalle [a, b]
            if (evalueEn(a) * evalueEn(racine) > 0) {
                a = racine;
            } else {
                b = racine;
            }

            //c) Calcul de la nouvelle approximation
            racine = (a + b) / 2;
        }
        //7) Vérification finale
        double decimalesExactes = Math.log10(Math.pow(2,n)/(b-a));
        if (decimalesExactes < d) throw new NumeriqueException();
        return racine;
    }

    // Renvoie une approximation de la racine contenue dans l'intervalle [a ; b] ou [b ; a],
    // obtenue après n itération de la méthode de Newton
    // Lance une NumeriqueException si la présence d'une et une seule racine n'est pas garantie
    public double racineParNewtonNIterations(double a, double b, int n) throws NumeriqueException {
        // TO DO
        //1) Vérification qu'une des bornes n'est pas la racine
        if (evalueEn(a) == 0) return a;
        if (evalueEn(b) == 0) return b;

        //2) Vérification de Bolzano
        if (evalueEn(a) * evalueEn(b) > 0) throw new NumeriqueException();

        //3) Vérification des hypothèses supplémentaires (f' et f'' de même signe en a et b)
        if (polynomeDerive().evalueEn(a) * polynomeDerive().evalueEn(b) <= 0) throw new NumeriqueException();
        if (polynomeDerive().polynomeDerive().evalueEn(a) * polynomeDerive().polynomeDerive().evalueEn(b) <= 0) throw new NumeriqueException();

        //4) Approximation initiale
        double racine = (a+b)/2;
        if (evalueEn(a) * polynomeDerive().polynomeDerive().evalueEn(b) <= 0) return racine;
        else b = racine;

        //5) Itération
        for (int i = 1; i <= n; i++){
            //a) Calcul de la nouvelle approximation
            racine = (a+b)/2;
            //b) Vérification que la nouvelle approximation n'est pas la racine
            if (evalueEn(a) == 0) return a;
            if (evalueEn(b) == 0) return b;
        }

        //6) Renvoie de la racine
        return racine;
    }

    // Renvoie une approximation à minimum d décimales exactes de la racine contenue dans
    // l'intervalle [a ; b] ou [b ; a], obtenue par la méthode de Newton
    // Lance une NumeriqueException en cas de problème
    public double racineParNewton(double a, double b, int d) throws NumeriqueException {
        // TO DO
        //1) Vérification qu'une des bornes n'est pas la racine
        double fa = this.evalueEn(a);
        if (fa == 0) return a;
        double fb = this.evalueEn(b);
        if (fb == 0) return b;
        //2) Vérification de Bolzano

        //3) Vérification des hypothèses supplémentaires : f' et f'' de même signe en a et b

        //4) Approximation initiale et extrémité fixe

        //5) Calcul de la précision à obtenir

        //6) Itération

            //a) Calcul de la nouvelle approximation

            //b) Vérification que la nouvelle approximation n'est pas la racine

            //c) Vérification hypothèses supplémentaires : f' et f'' de signe constant

            //d) Vérification instabilités numériques

        //7) Vérification finale de la précision

        return 0 ;
    }

}