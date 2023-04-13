/** Classe Suite

*/
import java.util.*;
import java.io.*;

public class Suite extends SuiteDeBase {

	// valeur num�rique de MAXELT
   private static final int MAX = Elt.MAXELT.val();

    public Suite(Suite s1, Suite s2){
        this(s2);
        this.ajouter(s1);
    }

	/** Constructeur vide */
   public Suite() {
      super();
   }

	/** Constructeur par recopie */
   public Suite(SuiteDeBase s) {
      super(s);
   }

	/** Constructeur � partir d'une String */
   public Suite(String st) {
      super(st);
   }

	/** Constructeur � partir d'un Elt et d'une Suite */
   public Suite(Elt t, Suite c) {
      super(t, c);
   }

	/** Construit la Suite r�duite � (x) */
   public Suite(Elt x) {
      this();
      this.ajouter(x);
   }

   public Suite corps() {
      return (Suite) super.corps();
   }
	
   @Override
   public int hashCode(){
      int prime = 31;
      int result = 1;
      for (Elt e : this){
         result = result *prime + e.hashCode();
      }
      return result;
   }


	/** Renvoie la longueur de la Suite courante */
   public int longueur() {
       if (this.estVide())
           return 0;
       return 1 + this.corps().longueur();
   }
  
  

	/** renvoie TRUE si e a au moins une occurrence dans la Suite courante
	 * @throws IllegalArgumentException en cas de param�tre invalide*/
   public boolean contient(Elt e){
       if (e == null) throw new IllegalArgumentException("e = null");
       if (this.estVide()) return false;
       if (tete().equals(e)) return true;
       return this.corps().contient(e);
   }

	/** renvoie le nombre d'occurrences de e dans la Suite courante
	 * @throws IllegalArgumentException en cas de param�tre invalide*/
   public int nombreOccur(Elt e){
       int nombreOccur = 0;
       if (e == null) throw new IllegalArgumentException("e = null");
       if (this.estVide()) return 0;
       if (this.tete().equals(e)) nombreOccur = 1;

       return nombreOccur + this.corps().nombreOccur(e);
   }
	
	
	/** renvoie la position de la premi�re occurrence de e dans la Suite courante ; 
	 * renvoie 0 si e n'a pas d'occurrence dans la Suite courante 
	 * @throws IllegalArgumentException en cas de param�tre invalide*/
   public int position(Elt e){
       if (e == null) throw new IllegalArgumentException("e = null");
       if (this.estVide() || nombreOccur(e) == 0) return 0;
       if (this.tete().equals(e)) return 1;
       return 1 + this.corps().position(e);
       // Solution sera donnée au cours de la semaine pro
   }
	
		
	/** renvoie le i-�me �l�ment de la Suite courante s'il existe ;
	 * @throws IllegalArgumentException s'il n'exite pas de i-�me �l�ment*/
   public Elt iEme(int i){
       if (i <= 0) throw new IllegalArgumentException("i négatif");
       if (this.estVide()) throw new IllegalArgumentException("Pas de ième élément car i trop grand");
       if (i == 1) return tete();
      return this.corps().iEme(i-1);	}
    //pas d'appel a une autre méthode!!!! interdit à l'examen
	
	
	/** renvoie le dernier �l�ment de la Suite courante si elle est non vide, 
	 * @throws MathException si elle est vide*/
   public Elt dernier(){
       if (this.estVide()) throw new MathException("La suite est vide");
       if (this.estVide()) return null; //Si la suite est vide -> on renvoie null
       if (this.corps().estVide()) return this.tete(); //Si le corps est vide -> le dernier nombre est la tete
       return this.corps().dernier(); //return le dernier nombre du corps
   }

   @Override
   /** renvoie true si la Suite courante est �gale � s*/
   public boolean equals(Object o){
       if (o == this) return true;
       if (o == null) return false;
       if (this.getClass() != o.getClass()) return false;
       Suite s = (Suite) o;
       if (this.estVide() && s.estVide()) return true;
       if (this.estVide()) return false; //si l'objet est vide -> return false
       if (s.estVide()) return false; //si la suite est vide -> return false
       if (this.tete().equals(s.tete())) { //si la tete de l'objet est égal à la tete de la suite
           return this.corps().equals(s.corps()); //alors le corps de l'objet est égal au corps de la suite
       }
       return false;
   }
	
	
	/** renvoie true si la Suite courante est un pr�fixe de s
	 * @throws IllegalArgumentException en cas de param�tre invalide*/
   public boolean prefixe(Suite s){
       if (s == null) throw new IllegalArgumentException("s est invalide");
       if (this.estVide()) return true;
       if (s.estVide()) return false;
       if (this.tete().equals(s.tete())) {
           return this.corps().prefixe(s.corps());
       }
       return false;
   }

	
	/** renvoie true si la Suite courante est une sous-suite de s
	 * @throws IllegalArgumentException en cas de param�tre invalide*/
   public boolean sousSuite(Suite s){
       if (s == null) throw new IllegalArgumentException("s est invalide");
      return sousSuiteBis(s);
   }

   private boolean sousSuiteBis(Suite s){
       if (this.estVide()) //tester ça avant
           return true;
       if (s.estVide()) //tester ça avant
           return false;
       if (this.tete().equals(s.tete()))
           return this.corps().sousSuiteBis(s.corps());
       else
           return this.sousSuiteBis(s.corps()); //toujours mettre le Bis pour que ce soit efficace -> perte de points à l'exam
   }

	
	/** ajoute s � gauche de la Suite courante
	 * @throws IllegalArgumentException en cas de param�tre invalide*/
   public void ajouter(Suite s){
       if (s == null) throw new IllegalArgumentException("s est invalide");
       this.ajouterBis(s);
   }

   private void ajouterBis(Suite s){
       if (!s.estVide()){
           if (!s.corps().estVide()){
               this.ajouterBis(s.corps());
           }
           this.ajouter(s.tete());
       }
   }

	
	/** ajoute s � l'envers et � gauche de la Suite courante
	 * @throws IllegalArgumentException en cas de param�tre invalide*/
   public void ajouterALEnvers(Suite s){
       if (s == null) throw new IllegalArgumentException("s est invalide");
       this.ajouterALEnversBis(s);
   }

   private void ajouterALEnversBis(Suite s){
       if (!s.estVide()){
           this.ajouter(s.tete());
           if (!s.corps().estVide()){
               this.ajouterALEnvers(s.corps());
           }
       }
   }


	/** renvoie la Suite courante invers�e*/
   public Suite inverse(){
       Suite s = new Suite();
       Iterator <Elt> it = this.iterator();
       while (it.hasNext())
           s.ajouter(it.next());
      return s;
   }


	
	/** renvoie une Suite contenant, une et une seule fois, tous les �l�ments de la Suite courante*/
   public Suite reduite(){
       if (this.estVide()) return new Suite();
       Suite s = this.corps().reduite();

       if (s.contient(this.tete())) return s;
       return new Suite(this.tete());
   }
	
	
	 /**Renvoie la Suite courante de laquelle on a supprim� tous les �l�ments � partir du k-i�me 
	  * Si la suite n'a pas k �l�ments, elle renvoie une copie de la suite.
	  * @throws IllegalArgumentException en cas de param�tre invalide
	  * */
   public Suite tronquee(int k){
       if (k < 1) throw new IllegalArgumentException("k est invalide");
       if (this.estVide() || k == 1) return new Suite();
       return new Suite(this.tete(), this.corps().tronquee(k-1));
   }

	
	/** renvoie une copie de la Suite courante dans laquelle on a supprim� la premi�re occurrence (�ventuelle) de x
	 * @throws IllegalArgumentException en cas de param�tre invalide*/	
   public Suite moinsPremOcc(Elt x){
       if (x == null) throw new IllegalArgumentException("x est invalide");

       if (this.estVide()) return new Suite();
       if (this.tete().equals(x)) return this.corps();

       return new Suite(this.tete(), this.corps().moinsPremOcc(x));
      
   }


	/** renvoie une copie de la Suite courante dans laquelle on a supprim� toutes les occurrences de x
	 * @throws IllegalArgumentException en cas de param�tre invalide*/
   public Suite moinsToutesOcc(Elt x){
       if (x == null) throw new IllegalArgumentException("x est invalide");

       if (this.estVide()) return new Suite();
       if (this.tete().equals(x)) return this.corps().moinsToutesOcc(x);

      return new Suite(this.tete(), this.corps().moinsToutesOcc(x));
   }

   
   /** renvoie une Suite constituee de tous les Elt ayant plus d'une occurrence dans la Suite courante */
   public Suite doublons(){
       if (this.estVide()) return new Suite();

       Suite s;
       s = this.corps().doublons();
       if (this.corps().contient(this.tete()) && !s.contient(this.tete()))
           s.ajouter(this.tete());

      return s;
   }
   
   /** renvoie true ssi la Suite courante contient au moins k Elt distincts 
    * @throws IllegalArgumentException en cas de param�tre invalide*/
   public boolean auMoinsK(int k){
       if (k < 0) throw new IllegalArgumentException("k est invalide");
       return auMoinsKBis(k);
   }

   private boolean auMoinsKBis(int k){
       if (k == 0) return true;
       if (this.estVide()) return false;
       if (!this.corps().contient(this.tete())) return this.corps().auMoinsKBis(k-1);
       return this.corps().auMoinsKBis(k);
   }
  
   /** renvoie true ssi tous les element de la Suite sont distincts */
   public boolean tousDistincts(){
       if (this.estVide()) return true;
       if (this.corps().contient(this.tete())) return false;
       return this.corps().tousDistincts();
   }
   
	/** renvoie une copie de la Suite courante dans laquelle toutes les occurrences de x ont �t� remplac�es par y
	 * @throws IllegalArgumentException en cas de param�tre invalide*/
   public Suite substitut(Elt x, Elt y){
       if (x == null || y == null) throw new IllegalArgumentException("x ou y invalide");
       return substitutBis(x,y);
   }

   private Suite substitutBis(Elt x, Elt y){
       if (this.estVide()) return new Suite();
       if (this.tete().equals(x)) return new Suite(y, this.corps().substitutBis(x,y));
       return new Suite(this.tete(), this.corps().substitutBis(x,y));
   }
   
	/** renvoie true si la Suite courante est strictement croissante*/
   public boolean estTriee(){
       if (this.estVide()) return true;
       if (this.corps().estVide()) return true;
       if (this.tete().val() >= this.corps().tete().val()) return false;

       return this.corps().estTriee();
   }
	

} // class Suite

