import java.io.*;

class HW01_3 {
	public static String readTextFile(String path) throws IOException{
		BufferedReader br= new BufferedReader(new InputStreamReader(
			new FileInputStream(path),"UTF-8"));
		String line=null;
		StringBuilder sb=new StringBuilder();
		while ((line=br.readLine())!=null) {
			sb.append(line).reverse().append("\n");
		}
		br.close();
		return sb.toString();
	}
	public static void writeTextFile(String path,String text,boolean append) throws IOException{
		BufferedWriter br=new BufferedWriter(new FileWriter(path,append));
		br.write(text);
		br.close();
	}
	public static void main (String[] args) throws IOException {
		File f=new File("izvor.txt");
		f.createNewFile();
		f=new File("destinacija.txt");
		f.createNewFile();
		String tmp1="Operativni sistemi";
		HW01_3.writeTextFile("izvor.txt",tmp1,false);
		String tmp=HW01_3.readTextFile("izvor.txt");
		HW01_3.writeTextFile("destinacija.txt",tmp,false);

	}
}