package major;

import java.text.DecimalFormat;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//import java.util.*;

public class Random 
{
	public static void Create() throws IOException 
	{
			double[][] data = new double [144][4];
			//Random X = new Random();
			//Random Y = new Random();
			//Random Z = new Random();
			for(int i = 0; i < 144; i++)
			{
				double X = Math.random()*166+928;
				double Y = Math.random()*111+955;
				double Z = Math.random()*100+1;
				DecimalFormat df = new DecimalFormat("####.00");
				data[i][1] = Double.parseDouble(df.format(X));//保留小数点后三位
				data[i][2] = Double.parseDouble(df.format(Y));
				data[i][3] = Double.parseDouble(df.format(Z));
				data[i][0] = i;
				//System.out.println(i+"  "+data[i][1]+"   "+data[i][2]+"  "+data[i][3]);
			}
			
			File file = new File("D:\\java项目\\my_project\\file\\data.txt");
			BufferedWriter bw = new BufferedWriter( new FileWriter( file));
			for(int i = 0; i < 144; i++)
			{
				String str = "";
				for(int j = 0; j < 4; j++)
				{
					str += data[i][j] + ",";
				}
				bw.write(str);
				//System.out.println(str);
				bw.newLine();
			}
			bw.close();
			System.out.println("成功生成数据文件");
			
			
			/*File file = new File("D:\\java项目\\my_project\\file\\my3.txt");
			ArrayList <DEM_Point> dem = KrigingCore.gridData();
			BufferedWriter bw = new BufferedWriter( new FileWriter(file));
			for(int i = 0; i < dem.size(); i++)
			{
				bw.write(dem.get(i).toString());
				bw.newLine();
			}
			
			bw.close();
			System.out.println("文件已成功写入！！!");*/
		
	}
	/*public static void main(String[] args) throws IOException 
	{
		Create();
	}*/
	
}
