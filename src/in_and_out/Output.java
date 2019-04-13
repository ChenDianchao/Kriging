package in_and_out;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import bean.DEM_Point;
import achievement.KrigingCore;

public class Output {

	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub
		File file = new File("D:\\java项目\\my_project\\file\\my3.txt");
		ArrayList <DEM_Point> dem = KrigingCore.gridData();
		BufferedWriter bw = new BufferedWriter( new FileWriter(file));
		for(int i = 0; i < dem.size(); i++)
		{
			bw.write(dem.get(i).toString());
			bw.newLine();
		}
		
		bw.close();
		System.out.println("文件已成功写入！！!");
	}
}
