import java.io.*;

class HW01_2 {
	public static String readTextFile(String path) throws IOException{
		FileInputStream br= new FileInputStream(path);
		int line=0;
		StringBuilder sb=new StringBuilder();
		while ((line=br.read())!= -1) {
			sb.append((char)line);
		}
		sb.reverse();
		br.close();
		return sb.toString();
	}
	public static void writeTextFile(String path,String text,boolean append) throws IOException{
		FileOutputStream  br=new FileOutputStream(path,append);
		byte[] b=text.getBytes();
		br.write(b);
		br.close();
	}
	public static void main (String[] args) throws IOException {
		File f=new File("izvor.txt");
		f.createNewFile();
		f=new File("destinacija.txt");
		f.createNewFile();
		String tmp1="Operativni sistemi";
		HW01_2.writeTextFile("izvor.txt",tmp1,false);
		String tmp=HW01_2.readTextFile("izvor.txt");
		HW01_2.writeTextFile("destinacija.txt",tmp,false);

	}
}