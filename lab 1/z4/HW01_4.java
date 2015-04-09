import java.io.*;

class HW01_4 {
	public static int readTextFile(String path,String compare) throws IOException{
		BufferedReader br= new BufferedReader(new InputStreamReader(
			new FileInputStream(path),"UTF-8"));
		String line=null;
		int all=0;
		while ((line=br.readLine())!=null) {
			String tmp[]=line.split(" ");
			for (int i=0; i<tmp.length; i++) {
				if (tmp[i].equals(compare)) {
					all++;
				}
			}
		}
		br.close();
		return all;
	}
	public static void main (String[] args) throws IOException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String loc=br.readLine().trim();
		String zbor=br.readLine().trim();
		int tmp=HW01_4.readTextFile(loc,zbor);
		System.out.println(tmp);
	}
}