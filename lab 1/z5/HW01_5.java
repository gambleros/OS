import java.io.*;

class HW01_5 {
	public static String readTextFile(String path) throws IOException{
		BufferedReader br= new BufferedReader(new InputStreamReader(
			new FileInputStream(path),"UTF-8"));
		String line=null;
		StringBuilder sb=new StringBuilder();
		int[] tmps=null;
		int skip,counter;
		skip=counter=0;
		String[] predmeti=null;
		while ((line=br.readLine())!=null) {
			String[] sep=null;
			if(line.contains(","))
				sep=line.split("\\,");
			else
				sep=line.split("\t");
			if (tmps==null) {
				tmps=new int[sep.length];
				predmeti=new String[sep.length];
				skip=0;
			}
			tmps[0]=0;
			for(int i=0;i<sep.length;i++){
				sb.append(sep[i]).append("\t");
				if(skip==0) predmeti[i]=sep[i];
				if (i==0 || skip==0) {
					continue;
				}
				int pom=Integer.parseInt(sep[i]);
				tmps[0]+=pom;
				tmps[i]+=pom;
			}
			counter++;
			sb.append("\n");
			if(skip==0) {
				skip=1;
				continue;
			}
			double prosek=(double)tmps[0]/(sep.length-1);
			System.out.printf("%s %.2f \n",sep[0],prosek);
		}
		for(int i=1;i<tmps.length;i++){
			double prosek=(double)tmps[i]/(counter-1);
			System.out.printf("%s : %.2f \n",predmeti[i],prosek);
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
		File f=new File("rezultati.csv");
		f.renameTo(new File("rezultati.tsv"));
		String tmp=HW01_5.readTextFile("rezultati.tsv");
		// System.out.println(tmp);
		HW01_5.writeTextFile("rezultati.tsv",tmp,false);

	}
}