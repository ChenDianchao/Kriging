package achievement;

import Jama.Matrix;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.text.DecimalFormat;

import bean.DEM_Point;
import in_and_out.Input;
import achievement.meshing;

public class KrigingCore {
	
	/**
	 * 克里金法
	 * @return
	 */
	//半方差函数，也称变差函数。采用球状模型公式（对气象要素插值时通常采用球状模型）
	//初步估算变程a=130.000  基台值为850.000  块金常数为500.000
	public static double function(double h)
	{
		double r = 0;//初始化变差函数值
		if (h == 0) //h为步长
		{
			r = 500.000;
			} 
		else if (h > 0 && h <= 130) 
		{
			r = 500.000 +13.500 *(((3 * h) / (2 * 130))-((h*h*h)/(2*130*130*130)));
			//r = 0.000 + 13.500 * ((3 * h * h * h * h) / (4 * 30 * 30 * 30 * 30));
		} 
		else 
		{//r=C0+C  基台值
			r = 850.000;
		}
		return r;

	}
	//变差函数  采用高斯模型
	/*public double function(double h)
	{
		double r = 0;
		if(h == 0)  r = 0;
		else if(h > 0 && h <= 11 )
		{
			r = 0.0 + 0.06 * (1.0 - Math.exp(-h/11) );
		}
		else  r = 3.202;
		
		return r;
	}*/
	
	
	public static ArrayList<DEM_Point> gridData()
	//public static  void gridData()
	{
		ArrayList<DEM_Point> demPoints = meshing.border();//网格点数组
		ArrayList<ArrayList<DEM_Point>> sum = Input.readFile();//离散点数据
		//System.out.println(demPoints);
		
		int count = 0;
		int XLength = sum.size();
		//System.out.println( XLength ) ;
		ArrayList<DEM_Point> dem = new ArrayList<DEM_Point>();//插值后格网点数据
		double[] Xs = new double[XLength];//离散点X的数组
		double[] Ys = new double[XLength];//离散点Y的数组
		double[] Zs = new double[XLength];//离散点Z的数组
		double[] rData = new double[XLength + 1];//网格点与离散点之间的距离
		double[][] ZD = new double[XLength+1][XLength + 1];//离散点Z的差值
		double[][] data = new double[XLength + 1][XLength + 1];//离散点与离散点的距离
		List<DEM_Point> points = null;
			
		//System.out.println(sum.size());
		//取出离散点并分入数组Xs[],Ys[],Zs[]
		for(int i = 0; i < sum.size(); i++)
		{
			points = new ArrayList<DEM_Point>(sum.get(i));
			//System.out.println(i+" "+sum.get(i));
			List<Double> X = points.stream().map(DEM_Point::getX).collect(Collectors.toList());
			//System.out.println(X);
			Xs[i] = X.get(0);
			//System.out.println(Xs[i]);
			List<Double> Y = points.stream().map(DEM_Point::getY).collect(Collectors.toList());
			Ys[i] = Y.get(0);
			List<Double> Z = points.stream().map(DEM_Point::getZ).collect(Collectors.toList());
			Zs[i] = Z.get(0);
		}
			
			/*for(int i = 0; i < Xs.length; i++)
			{
				System.out.println(Xs[i]);
			}*/
				
			//求出变差函数  即系数矩阵
			for(int i = 0; i <= XLength ; i++ )
			{
				for(int j = 0; j <= XLength ; j++ )
				{
					if((i < XLength) && (j < XLength) )
					{
						if( i != j )
						{
							double TempDis = 0;
							
							TempDis = Math.sqrt( ( (Xs[i]-Xs[j]) * (Xs[i]-Xs[j]) )
									+ ( (Ys[i]-Ys[j]) * (Ys[i]-Ys[j]) ) );
							ZD[i][j] =Math.abs(Zs[i] - Zs[j]);//求绝对值
							
							//System.out.println(TempDis);
							data[i][j] = function (TempDis);
							/*data[i][j] = Math.sqrt( ( (Xs[i]-Xs[j]) * (Xs[i]-Xs[j]) )
									+ ( (Ys[i]-Ys[j]) * (Ys[i]-Ys[j]) ) );*/
						}
						else
						{
							data[i][j] = 0 ;
						}
					}
					else if ( (i == XLength ) || (j == XLength ))
					{
						data[i][j] = 1;
					}
					else if ((i == XLength ) && (j == XLength ))
					{
						data[i][j] = 0;
					}
				}
			}
	
			/*for(int i = 0; i <XLength ; i++ )
			{
				for(int j = 0; j < XLength ; j++ )
				{
					System.out.println(data[i][j] + "  " + i +  "  " + j);
				}
			}*/
			
			Matrix m = new Matrix( data );//根据二维数组data得出矩阵m
			Matrix M = m.inverse(); //矩阵m求逆后得到的矩阵M
			
			while(count < demPoints.size())
			{
				DEM_Point p = demPoints.get(count++);
				double xp = p.getX();//格网点X
				double yp = p.getY();//格网点Y
				double zp = 0;//格网点Z
				//System.out.println(p);
				for(int k = 0; k <= XLength; k++)
				{
					if(k < XLength)
					{
						double TempDis2 = 0;
				
						 TempDis2 = Math.sqrt((xp - Xs[k])
							* (xp - Xs[k]) + (yp - Ys[k])
							* (yp - Ys[k]));
						rData[k]= function( TempDis2);
					}
					else
						rData[k] = 1;
					//System.out.println(rData[k]);
				}
				//System.out.println(" ------------------------------------------");
				double[] rData2 = new double[XLength + 1];//权重系数λi
				for( int k = 0; k < (XLength + 1); k++)
				{
					for(int k2 = 0; k2 < (XLength + 1); k2++)
					{
						rData2[k] += M.get(k, k2) * rData[k2];
					}
					//System.out.println(rData2[k]);
				}
				
				//System.out.println(" ------------------------------------------");
				for(int k = 0; k < XLength; k++)//得到估计值
				{
					zp += Zs[k]*rData2[k];
					//System.out.println(rData2[k]);
					//System.out.println(Zs[k] +" "+ rData2[k]+" "+ count);
				}
				
				DecimalFormat df = new DecimalFormat("###.000");
				double Zp = Double.parseDouble(df.format(zp));//保留小数点后三位
				//System.out.println(zp+" "+ count);
				dem.add(new DEM_Point(xp, yp, Zp));	
			}
			//List<Double> X = points.stream().map(DEM_Point::getX).collect(Collectors.toList());
			System.out.println(dem.stream().map(DEM_Point::getX));
			return dem;
	}

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("开始执行操作");
		gridData();
		System.out.println("完成操作");

	}*/

}
