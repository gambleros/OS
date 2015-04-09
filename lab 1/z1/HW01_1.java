import java.io.*;
import java.util.Scanner;
class InvalidDirectory extends Exception{
	
	public InvalidDirectory(){
		super("InvalidDirectory");
	}
	public InvalidDirectory(String s){
		super(s);
	}
}
class HW01_1 {
	public static void main(String[] args) {
		try	{
		Scanner in=new Scanner(System.in);
		System.out.print("Enter the directory you want to search: ");
		String ini=in.nextLine();
		File f=new File(ini);
		String[] s=f.list();
		if (s==null) {
			throw new InvalidDirectory("Invalid Directory name,please enter the correct path");
		}
		String[] files=new String[s.length];
		int N=0;
		for (int i=0;i<s.length;i++) {
			if (s[i].matches(".*.txt$")) {
				files[N++]=s[i];
			}
		}
		int sum=0;
		for (int i=0; i<N; i++) {
			File tmp=new File(files[i]);
			System.out.println(tmp.length());
			sum+=tmp.length();
		}
		double finale=sum/N;
		System.out.println(finale);
	}
	catch (InvalidDirectory e) {
		System.out.println(e);
	}
	}
}