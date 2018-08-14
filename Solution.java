/*
 * A solution object holds the solution of an equation.
 * It also holds miscellanious data about the solution set
 */
public class Solution {
	private int max_width, height;
	private String[] solutionSet;
	private String solutionAsString;
	private int initialwhitespaces = 5;
	/*
	 * Sets all the variables
	 */
	public Solution(String[] solution){
		solutionSet = solution;
		height = solution.length;

		int rightmost_equal_sign = 0;

		//Identify the position of the equal sign
		for(int i=0;i<solution.length;i++){
			if(solution[i].indexOf("=") > rightmost_equal_sign){
				rightmost_equal_sign = solution[i].indexOf("=");
			}
		}

		StringBuffer SB = new StringBuffer();
		max_width = 0;
		for(int i=0;i<solution.length;i++){
			//Add whitespaces so that the equal signs become aligned
			int no_of_whitespaces = initialwhitespaces + rightmost_equal_sign-solution[i].indexOf("=");
			int width_of_line = no_of_whitespaces + solution[i].length();
			if(width_of_line > max_width){
				max_width = width_of_line;
			}

			for (int j=0;j< no_of_whitespaces; j++){
				SB.append(" ");
			}
			SB.append(solution[i]);
			SB.append("\n");
		}

		solutionAsString = SB.toString();
	}
	/*
	 * Returns the number of lines in the solution
	 */
	public int height(){
		return height;
	}
	/*
	 * Returns the maximum width 
	 */
	public int width(){
		return max_width;
	}
	/*
	 * Returns the solution as a string with the equal signs aligned. 
	 */
	public String toString(){
		return solutionAsString;
	}
	/*
	 * Returns a copy of the String array
	 */
	public String[] getSolutionAsArray(){
		String[] ret = new String[solutionSet.length];
		for(int i=0;i<solutionSet.length;i++){
			ret[i] = solutionSet[i];
		}
		return ret;
	}

}
