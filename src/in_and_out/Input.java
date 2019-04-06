package in_and_out;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;

import bean.Discrete_Point;


public class Input {
	/**
	 * 读取数据存储到数组中
	 * @return
	 */	
	public static ArrayList<ArrayList<Discrete_Point>> readFile()
	{
		File file = new File("D:\\java项目\\my_project\\file\\data.txt");
		BufferedReader reader= null;
		
		ArrayList<ArrayList<Discrete_Point>> sum= null;//动态二维数组sum
		//ArrayList<Double> Xs=new ArrayList<Double>();//数组X
		//ArrayList<Double> Ys=new ArrayList<Double>();//数组Y
		//ArrayList<Double> Zs=new ArrayList<Double>();//数组Z
		
		try 
		{
			reader = new BufferedReader(new FileReader(file));//读取数据
			String tempString = null;
			//System.out.println(tempString);
			int count = 0;
			
			//将点存储到动态数组中
			sum = new ArrayList<ArrayList<Discrete_Point>>();
			ArrayList<Discrete_Point> points = null;
			while((tempString = reader.readLine())!= null)
			{
				//System.out.println(tempString);
				String[] str = tempString.split(",");
				
				Double[] filePoint = new Double[str.length-1];
				//System.out.println(str.length-1);
				points = new ArrayList<Discrete_Point>();

				for(int i = 0; i<str.length-1; i++)
				{
					filePoint[i] = new Double(str[i+1]);
					//System.out.println(filePoint[i]);
				}
				if(count<144)
				{
					points.add(new Discrete_Point(filePoint[0], filePoint[1], filePoint[2]));
					//System.out.println(filePoint[0]+ filePoint[1]+ filePoint[2]);
					count++;
					sum.add(points);
					//Xs.add(filePoint[0]);
					//Ys.add(filePoint[1]);
					//Zs.add(filePoint[2]);
				}
				
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		finally
		{
			if(reader != null )
			{
				try
				{
					reader.close();
				}catch(IOException e2){
					e2.printStackTrace();
				}
			}
		}
		return sum;	
	}
	
	/*public static void main(String[] args) {
		ArrayList<ArrayList<Discrete_Point>> sum = readFile();
		System.out.println(sum);
		System.out.println("文件导入成功");
	}*/
	
}

