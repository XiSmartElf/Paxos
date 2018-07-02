import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//How to RUN this:
//javac FindPairBonus.java
//java FindPairBonus <your prices input file path> <card value>
//for example: java FindPairBonus prices.txt 1000
public class FindPairBonus
{
	public static void main(String[] args) {
	    String filePath = args[0];
	    int cardMax = Integer.parseInt(args[1]);
	    List<String> items = new ArrayList<String>();
	    List<Integer> prices = new ArrayList<Integer>();
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while (line != null) {
				String[] params = line.split(", ");
				items.add(params[0]);
				prices.add(Integer.parseInt(params[1]));
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        getSmartSpending(prices, items, cardMax);
	}
	
	//Complexity O(n^2)
	public static void getSmartSpending(List<Integer> prices, List<String> items, int cardMax){
		int max = -1;
        String message = null;
		
		//Use 1 loop and two pointers to find the best combination.
        for(int i=0; i<prices.size()-2; i++){
            int priceC = prices.get(i);
            int left = i+1;
		    int right = prices.size()-1;

            while(left<right){
                int priceA = prices.get(left);
                int priceB = prices.get(right);
                int total = priceA + priceB + priceC;
                if(total <= cardMax){
                    if(total > max) {
                        max = total;
                        message = items.get(left) + " "+ priceA +", "+ items.get(right)+" "+priceB + ", "+items.get(i)+" "+priceC;
                    }
                    left++;
                }else {
                    right--;
                }
            }
        }
		
		if(max==-1){
		    System.out.println("Not possible");
		} else {
		    System.out.println(message);
		}
	}	
}
