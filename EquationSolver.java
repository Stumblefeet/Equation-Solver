
import java.util.*;
/*
 * An equation solver for linear equations.
 * 
 * @version 2.0
 */
public class EquationSolver {

	static String[] left, right;
	static String equation;
	static LinkedList<String> q;
	static boolean integers;
	static boolean comma;
	static String unknown;

	/*
	 * Change the head sign of the addend
	 */
	private static String chs(String addend){
		if(addend.charAt(0) == '-'){
			return addend.replace('-', '+');
		}else{
			return addend.replace('+', '-');
		}
	}
	/*
	 * Solves an equation, and returns the solution as a String[]
	 * from other methods and combines them.
	 */
	public static Solution solveEquation(String eq){

		equation = eq;
		//A stack of the equations to be presented in the solution
		q = new LinkedList<String>();


		comma = equation.contains(",");
		equation = equation.replace(',', '.');
		integers = !equation.contains(".");

		int position_of_the_equal_sign =  equation.indexOf("=");
		String left_String = equation.substring(0, position_of_the_equal_sign);
		String right_String = equation.substring(position_of_the_equal_sign+1);

		//Convert the String objects to tokenized String arrays with no whitespaces
		left = find_addends(left_String);
		right = find_addends(right_String);

		//Add the original problem to the solution string
		addEquation();

		unknown = findUnkown();
		if(unknown == null){
			q.add("No unknowns...");
			double sum_l = 0;
			double sum_r = 0;
			for(int i=0;i<left.length;i++){
				sum_l += Double.parseDouble(left[i]);
			}
			for(int i=0;i<right.length;i++){
				sum_r += Double.parseDouble(right[i]);
			}

			left = new String[1];
			right = new String[1];
			left[0] = ""+sum_l;
			right[0] = ""+sum_r;
			if(integers){
				left[0] = ""+(int)sum_l;
				right[0] = ""+(int)sum_r;
			}

			if(sum_l > 0){
				left[0] = "+"+left[0];
			}			
			if(sum_r > 0){
				right[0] = "+"+right[0];
			}

			addEquation();
			if(sum_l == sum_r){
				q.add("Sidene er like store");
			}else {
				q.add("Sidene er ikke like store");
			}
			String[] ret = q.toArray(new String[0]);

			return new Solution(ret);
		}

		LinkedList<String> LL = new LinkedList<String>();
		LinkedList<String> LR = new LinkedList<String>();
		LinkedList<String> RR = new LinkedList<String>();
		LinkedList<String> RL = new LinkedList<String>();

		for(int i=0;i<left.length;i++){
			String s = left[i];
			if(left[i].contains(unknown)){
				LL.add(s);
			}else{ 
				s = chs(s);
				LR.add(s);
			}
		}
		for(int i=0;i<right.length;i++){
			String s = right[i];
			if(!right[i].contains(unknown)){
				RR.add(s);
			}else{ 
				//''''''''''''''''''''''''''''''
				//if the addend is 0x or 0

				
				
				//*****************
				
				
				
				
				s = chs(s);
				RL.add(s);
			}
		}
		LL.addAll(RL);
		RR.addAll(LR);
		left = LL.toArray(new String[0]);
		right = RR.toArray(new String[0]);
		if(left.length == 1 && right.length == 1){
			if(left[0].length() == 1){
				return finished(1);
			}else{
				if(left[0].equals("-"+unknown)){
					return finished(-1);
				}
			}
		}
		/*
		if(right.length == 1){
			if(right[0].equals("-0")){
				right[0] = "+0";
			}
			if(right[0].equals("-0.0")){
				right[0] = "+0.0";
			}
		}
		*/
		addEquation();

		//Sum up addends
		double left_n = 0;
		double right_n = 0;

		for(int i=0;i<left.length;i++){

			double d = 0.0;
			if(left[i].length() == 2){
				d=1;
			}else{
				String s = left[i].substring(1, left[i].length()-unknown.length());
				System.out.println(s);
				System.out.println(unknown);
				if(!s.contains(".")){
					s = s+".0";
				}

				d = Double.parseDouble(s);
			}
			if(left[i].charAt(0) == '-'){
				d *= -1;
			}
			System.out.println(d);
			left_n += d;
		}
		for(int i=0;i<right.length;i++){
			String s = right[i].substring(1);

			if(!s.contains(".")){
				s = s+".0";
			}

			double d = Double.parseDouble(s);
			if(right[i].charAt(0) == '-'){
				d *= -1;
			}
			right_n += d;
		}

		left = new String[1];
		right = new String[1];
		if(integers){
			int a = (int) left_n;
			int b = (int) right_n;
			left[0] = a+unknown;
			right[0] = ""+b;
		}else{
			left[0] = ""+left_n+unknown;
			right[0] = ""+right_n;
		}
		if(left_n>=0){
			left[0] = "+"+left[0];
		}
		if(right_n>=0){
			right[0] = "+"+right[0];
		}

		if(left_n == 1.0 || left_n == -1.0){
			return finished(left_n);
		}

		addEquation();

		//Normalisation
		if(left_n == 0){
			left[0] = "+0"+unknown;
			if(right_n == 0){
				right[0] = "+0";
				addEquation();
				q.add("Alle verdier for "+unknown+" er løsninger.");
				String[] ret = q.toArray(new String[0]);
				return new Solution(ret);
			}else{
				right[0] = ""+right_n;
				if(integers){
					right[0] = ""+(int)right_n;
				}
				if(right_n > 0){
					right[0] = "+"+right[0];
				}
				addEquation();
				q.add("Der finnes ingen løsninger.");
				String[] ret = q.toArray(new String[0]);
				return new Solution(ret);
			}

		}
		left[0] = left[0]+" / "+left_n;
		right[0] = ""+right_n+" / "+left_n;
		if(right_n > 0){
			right[0] = "+"+right[0];
		}
		if(integers){
			int a = (int) left_n;
			int b = (int) right_n;
			left[0] = ""+a+unknown+" / "+a;
			right[0] = ""+b+" / "+a;

			if(a>0){
				left[0] = "+"+left[0];
			}
			if(b>0){
				right[0] = "+"+right[0];
			}

		}
		addEquation();

		//Solve and round off the right hand expression
		double d = right_n / left_n; 
		//Rounding
		d*=1000;
		int c = (int)Math.round(d);
		d= c/1000.0;
		right[0] =  ""+d;

		if(d - (int)d == 0.0){
			//The solution is an integer.
			right[0] = ""+ (int) d; 
		}
		if(d>0){

			right[0] = "+"+right[0];

		}
		left[0] = "+"+unknown;
		addEquation();

		String[] ret = q.toArray(new String[0]);

		return new Solution(ret);
	}
	/*
	 * The equation has been tested, and we're at one of two sepcial cases.
	 */
	private static Solution finished(double left_n){
		if(left_n == 1.0){
			left[0] = "+"+unknown;
			//Done...
			addEquation();
			String[] ret = q.toArray(new String[0]);
			return new Solution(ret);
		}else{//left_n == -1.0
			left[0] = "-"+unknown;
			addEquation();
			left[0] = chs(left[0]);
			right[0] = chs(right[0]);
			addEquation();
			String[] ret = q.toArray(new String[0]);
			return new Solution(ret);
		}
	}
	/*
	 * Creates a String representation of a line in the solution
	 */
	private static String createEquation(){
		StringBuffer e = new StringBuffer();

		if(left[0].charAt(0) == '-'){
			e.append(left[0]);
		}else{
			e.append(left[0].substring(1));
		}
		for(int i=1; i<left.length;i++){
			//Inserting a space before and between the prefix and the addend
			e.append(" "+left[i].charAt(0)+ " " +left[i].substring(1));         
		}

		e.append(" = ");
		if(right[0].charAt(0) == '-'){
			e.append(right[0]);
		}else{
			e.append(right[0].substring(1));         
		}
		for(int i=1; i<right.length;i++){
			//Inserting a space before and between the prefix and the addend
			e.append(" "+right[i].charAt(0)+ " " +right[i].substring(1));
		}
		String ret = e.toString();
		if(comma){
			ret = ret.replace('.', ',');
		}
		return ret;
	}
	/*
	 * Prints the actual line in the solution to the console
	 */
	private static void printEquation(){
		String e = createEquation();
		System.out.println(e);
	}
	/*
	 * Adds an equation to the solution
	 */
	private static void addEquation(){
		String e = createEquation();
		if(q.isEmpty()){
			q.add(e);
		}else{
			//Only add an equation if it is different 
			//from the previous line in the solution.
			if(!q.getLast().equals(e)){
				q.add(e);
			}
		}
	}

	/*
	 * Clears every whitespace from the input string
	 */
	private static String clear_whitespaces(String s){
		int number_of_whitespaces = 0;
		char[] char_tab_original = s.toCharArray();
		for(int i=0;i<char_tab_original.length;i++){
			if(char_tab_original[i] == ' '){
				number_of_whitespaces++;
			}
		}
		char[] char_tab = new char[char_tab_original.length - number_of_whitespaces];

		int pos = 0;
		for(int i=0;i<char_tab_original.length;i++){
			if(char_tab_original[i] != ' '){
				char_tab[pos] = char_tab_original[i];
				pos++;
			}
		}
		return new String(char_tab);
	}
	/*
	 * Identifies addends within the input string
	 */
	private static String[] find_addends(String str){

		str = clear_whitespaces(str);
		char[] char_tab = str.toCharArray();

		LinkedList<String> addends= new LinkedList<String>();

		int pos = 0;

		//i = 1 sorts the sign issue where [0] is a '-'
		for(int i=1;i<char_tab.length;i++){
			if(char_tab[i] == '+' || char_tab[i] == '-'){
				String s = str.substring(pos, i);
				pos = i;
				addends.add(s);
			}
		}
		addends.add(str.substring(pos));

		//Stack to String[]
		String[] ret = new String[addends.size()];
		for(int i = 0;i<ret.length;i++){
			ret[i] = addends.poll();
		}

		//Adding + to any non-negative first addend
		if(ret[0].charAt(0)!='-'){
			ret[0] = "+"+ret[0];
		}

		return ret;
	}
	private static String findUnkown(){
		for(int i=0;i<left.length;i++){
			if(Integer.getInteger(left[i]) == null){
				char[] tab = left[i].toCharArray();
				for(int j=1;j<tab.length;j++){
					if(!(Character.isDigit(tab[j]) || tab[j] == '.')){
						return left[i].substring(j);
					}
				}
			}
		}
		for(int i=0;i<right.length;i++){
			if(Integer.getInteger(right[i]) == null){
				char[] tab = right[i].toCharArray();
				for(int j=1;j<tab.length;j++){
					if(!Character.isDigit(tab[j])){
						return right[i].substring(j);
					}
				}
			}
		}
		//Dummy
		return null;
	}
}
