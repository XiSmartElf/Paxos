import java.util.*;

//How to RUN this:
//javac ReplaceX.java
//java ReplaceX <input string>
//for example: java ReplaceX 10X10X0
public class ReplaceX
{
	//Used to cache the next read index in the string so it
	//does not need to reiterate from the beginning of the string again.
	static class IntermediateForm {
		public String str;
		public int nextReadIndex;
		public IntermediateForm(String str, int nextReadIndex){
			this.str = str;
			this.nextReadIndex = nextReadIndex;
		}
	}

	//Complexity O(2^n), memory O(2^n * n) where n is the length of the string
	public static void main(String[] args) {
	    String input= args[0];
	    Stack<IntermediateForm> stack = new Stack<IntermediateForm>();
	    stack.push(new IntermediateForm(input, 0));
	    
	    while(!stack.isEmpty()){
	        IntermediateForm elem = stack.pop();
			String str = elem.str;
			int nextReadIndex = elem.nextReadIndex;
	        boolean manipulated = false;
			
	        for(int i=nextReadIndex; i<str.length(); i++){
	            if(str.charAt(i)=='X'){
	                stack.push(new IntermediateForm(str.substring(0,i)+"0"+str.substring(i+1), i+1));
	                stack.push(new IntermediateForm(str.substring(0,i)+"1"+str.substring(i+1), i+1));
	                manipulated = true;
	                break;
	            }
	        }
	        
	        if(!manipulated){
	            System.out.println(str);
	        }
	    }
	}
}

