package achievement;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import bean.DEM_Point;
import in_and_out.Input;

public class meshing {
	
	/**
	 * 确定栅格网点的坐标
	 * @return
	 */
	public static ArrayList<DEM_Point> border()
	//public static void border()
	{
		ArrayList<ArrayList<DEM_Point>> sum = Input.readFile();
		//System.out.println(sum);
		double[] Xs = new double [sum.size()];//一维数组Xs
		double[] Ys = new double [sum.size()];//数组Ys
		List<DEM_Point> points = null;
		for(int i = 0; i < sum.size(); i++)
		{
			points = new ArrayList<DEM_Point>(sum.get(i));
			//System.out.println(points);
			List<Double> X = points.stream().map(DEM_Point::getX).collect(Collectors.toList());
			Xs[i] = X.get(0);
			List<Double> Y = points.stream().map(DEM_Point::getY).collect(Collectors.toList());
			Ys[i] = Y.get(0);
		}
		
		//设置边界范围
		double Xmax = Xs[0];
		double Ymax = Ys[0];
		double Xmin = Xs[0];
		double Ymin = Ys[0];
		
		for(int i = 0; i < Xs.length; i++)
		{
			Xmax = Xmax > Xs[i] ? Xmax : Xs[i];
			Xmin = Xmin < Xs[i] ? Xmin : Xs[i];
		}
		
		for(int i = 0; i < Ys.length; i++)
		{
			Ymax = Ymax > Ys[i] ? Ymax : Ys[i];
			Ymin = Ymin < Ys[i] ? Ymin : Ys[i];	
		}
		//System.out.println(Xmax + " " + Xmin);
		//System.out.println(Ymax + " " + Ymin);
		
		int demXmin = (int) Xmin;
		int demYmin = (int) Ymin;
		
		//设格网尺寸取5
		int numX = (int) ((Xmax-Xmin)/5+1);
		int numY = (int) ((Ymax-Ymin)/5+1);
		//System.out.println(numX + "  " + numY);
		
		//计算格网点坐标
		double[] demXs = new double[numX];
		double[] demYs = new double[numY];
		ArrayList<DEM_Point> demPoints = new ArrayList<DEM_Point>();
		
		for(int i = 0; i < numX; i++)
		{
			for(int j = 0; j <numY; j++)
			{
				demXs[i] = demXmin + i * 5;
				demYs[j] = demYmin + j * 5;
				demPoints.add(new DEM_Point(demXs[i], demYs[j]));
			}
		}
		//System.out.println(demPoints);
		return demPoints;
	}
	
	/*public static void main(String[] args)
	{
		border();
		
	}*/


}
