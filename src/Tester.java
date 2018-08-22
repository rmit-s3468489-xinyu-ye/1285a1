import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Tester tester = new Tester();
		tester.randomTestCase();
		//
	}
	
	ArrayList<String> arrayList = new ArrayList<String>();
	LinkedListMultiset<String> linkedListMultiset = new LinkedListMultiset<String>();
	SortedLinkedListMultiset<String> sortedLinkedListMultiset = new SortedLinkedListMultiset<String>();
	BstMultiset<String> bstMultiset = new BstMultiset<String>();
	HashMultiset<String> hashMultiset = new HashMultiset<String>();
	BalTreeMultiset<String> balTreeMultiset = new BalTreeMultiset<String>();
	
	private void randomTestCase(){
		
		arrayList = new ArrayList<String>();
		linkedListMultiset = new LinkedListMultiset<String>();
		sortedLinkedListMultiset = new SortedLinkedListMultiset<String>();
		bstMultiset = new BstMultiset<String>();
		hashMultiset = new HashMultiset<String>();
		balTreeMultiset = new BalTreeMultiset<String>();
		
		//step1. testcase generation
		
		//step2.
		String[] operators = new String[]{"A","S","RO","RA"};

		Random random = new Random();
		int testTimes = 1000;
		
		for(int i = 0 ; i < testTimes ; i ++){
			
			boolean isnew = true;
			System.out.println("<" + i +"> " + "current operation = " + operators[0] +", current test item == "+ (isnew?"new":"old"));
			operate(operators[0],isnew);
			
			if(!check()){
				System.err.println("Error! the results of adts not equal to each other!");
				print(System.err);
				return;
			}
		}
		
		
		for(int i = 0 ; i < testTimes ; i ++){
			
			int randomOperator = random.nextInt(operators.length);
			boolean isnew = random.nextBoolean();
//			randomOperator = 2;
//			isnew = false;
			System.out.println("<" + i +"> " + "current operation = " + operators[randomOperator] +", current test item == "+ (isnew?"new":"old"));
			
			operate(operators[randomOperator],isnew);
			
			if(!check()){
				System.err.println("Error! the results of adts not equal to each other!");
				print(System.err);
				return;
			}
		}
		
		randomTestCase();
	}
	
	private void  print(PrintStream ps){
		ps.println("------------------------ print linkedListMultiset ------------------------");
		linkedListMultiset.print(ps);
		ps.println("----------------------------------- done ----------------------------------");
		ps.println();
		
		ps.println("------------------------ print sortedLinkedListMultiset ------------------------");
		sortedLinkedListMultiset.print(ps);
		ps.println("----------------------------------- done ----------------------------------");
		ps.println();
		
		ps.println("------------------------ print bstMultiset ------------------------");
		bstMultiset.print(ps);
		ps.println("----------------------------------- done ----------------------------------");
		ps.println();
		
		ps.println("------------------------ print hashMultiset ------------------------");
		hashMultiset.print(ps);
		ps.println("----------------------------------- done ----------------------------------");
		ps.println();
		
		ps.println("------------------------ print balTreeMultiset ------------------------");
		balTreeMultiset.print(ps);
		ps.println("----------------------------------- done ----------------------------------");
		ps.println();
	}
	
	private String generateRandomStr(){
		String randomStr = "";
		char[] chars = "1234567890".toCharArray();
		Random random = new Random();
		int length = random.nextInt(5)+1;
		for(int i = 0 ; i < length ; i ++){
			randomStr += chars[random.nextInt(chars.length)];
		}
		return randomStr;
	}
	
	private String getExistItemRandomly(){
		if(arrayList.size() == 0) return generateRandomStr();
		Random random = new Random();
		return arrayList.get(random.nextInt(arrayList.size()));
	}
	private String currentTestItem = null;
	private void operate(String command,boolean isNew){
		
		String item = null;
		if(isNew){
			item = generateRandomStr();
		}else{
			item = getExistItemRandomly();
		}
		currentTestItem = item;
		System.out.println("operated Item = '" + item +"'");
		
		switch (command.toUpperCase()) {
		// add
		case "A":
			arrayList.add(item);
			linkedListMultiset.add(item);
			sortedLinkedListMultiset.add(item);
			bstMultiset.add(item);
			hashMultiset.add(item);
			balTreeMultiset.add(item);
			break;
		// search
		case "S":
			
			linkedListMultiset.search(item);
			sortedLinkedListMultiset.search(item);
			bstMultiset.search(item);
			hashMultiset.search(item);
			balTreeMultiset.search(item);
			break;
		// remove one instance
		case "RO":
			if(balTreeMultiset.search(item) == 1 || hashMultiset.search(item) == 1){
				arrayList.remove(item);
			}
			
			linkedListMultiset.removeOne(item);
			sortedLinkedListMultiset.removeOne(item);
			bstMultiset.removeOne(item);
			hashMultiset.removeOne(item);
			balTreeMultiset.removeOne(item);
			break;
		// remove all instances
		case "RA":
			arrayList.remove(item);
			linkedListMultiset.removeAll(item);
			sortedLinkedListMultiset.removeAll(item);
			bstMultiset.removeAll(item);
			hashMultiset.removeAll(item);
			balTreeMultiset.removeAll(item);
			break;		
		default:

		}
	}
	
	private boolean check(){
		
		ByteArrayOutputStream bstBaos = new ByteArrayOutputStream();
		ByteArrayOutputStream sortedListBaos = new ByteArrayOutputStream();
		PrintStream bstMultisetPs = new PrintStream(bstBaos);
		PrintStream sortedLinkedListMultisetPs = new PrintStream(sortedListBaos);
		bstMultiset.print(bstMultisetPs);
		sortedLinkedListMultiset.print(sortedLinkedListMultisetPs);
		if(!bstBaos.toString().equals(sortedListBaos.toString())){
			
			System.err.println("the print results doesn't equal!!!");
			bstMultiset.removeAll(this.currentTestItem);
			bstMultiset.print(bstMultisetPs);
			return false;
		}
		
		for(String item : arrayList){
			int result = hashMultiset.search(item);
			
			if(result != sortedLinkedListMultiset.search(item)){
				System.err.println("sortedLinkedListMultiset's search reasult is error -----------  <item = "+item+">");
				return false;
			}else if(result != bstMultiset.search(item)){
				System.err.println("bstMultiset search reasult is error -----------  <item = "+item+">");
				return false;
			}else if(result != linkedListMultiset.search(item)){
				System.err.println("linkedListMultiset search reasult is error -----------  <item = "+item+">");
				return false;
			}else if(result !=balTreeMultiset.search(item)){
				System.err.println("balTreeMultiset search reasult is error -----------  <item = "+item+">");
				return false;
			}
	
		}
		return true;
	}

}
