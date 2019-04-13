package achievement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import bean.DEM_Point;
import in_and_out.Input;

public class Variogram{
//��Ϻ���    ����������a=130.000  ��ֵ̨Ϊ850.000  �����Ϊ500.000
	public static void fitting(double[][] z, double[][] h)
	{
		//temp = fitting(data,ZD);
		double Zmax = 0;
		double Zmin = z[0][0];
		//System.out.println(z.length + "  " + z[0].length + "  " + z[3][2]);
	
	
		for(int i = 0; i < h.length; i++)
		{
			for(int j = 0; j < h[0].length; j++)
			{
				//if((i != j) && (i < z.length-1) && (j < z[0].length - 1 )) 
				//{
					//System.out.println(h[i][j] + "  " + z[i][j]);
					Zmax = Zmax > z[i][j] ? Zmax : z[i][j];
					Zmin = Zmin < z[i][j] ? Zmin : z[i][j];
					//}
			}
		}
		//System.out.println(Zmax + "  " + Zmin);
		
		for(int k = 20; k < ( Zmax + 20 ); k += 20) //kΪ����
		{	
			int count = 0 ;
			double distance = 0;
			double temph = 0;
			
			for(int i = 0; i < h.length; i++)
			{
				for(int j = 0; j < h[0].length; j++)
				{
					//System.out.println( z[i][j]);
					if((z[i][j] < k) && (z[i][j] >= (k - 20)))
					{
						count++;
						//System.out.println( k + "  " + z[i][j] );
						distance += z[i][j];
						temph += h[i][j] * h[i][j];	
					}		
				}
			}
			distance = distance / count;
			temph = temph / (2 * count );
			System.out.println(distance + "  " + temph);
		}
	}
	
	//��ȡ�������
	public static void Var()
	{
		ArrayList<ArrayList<DEM_Point>> sum = Input.readFile();//��ɢ������
		//System.out.println(demPoints);

		int XLength = sum.size();
		double[] Xs = new double[XLength];//��ɢ��X������
		double[] Ys = new double[XLength];//��ɢ��Y������
		double[] Zs = new double[XLength];//��ɢ��Z������
		double[][] ZD = new double[XLength+1][XLength + 1];//��ɢ��Z�Ĳ�ֵ
		double[][] data = new double[XLength + 1][XLength + 1];//��ɢ������ɢ��ľ���
		List<DEM_Point> points = null;
			
		//System.out.println(sum.size());
		//ȡ����ɢ�㲢��������Xs[],Ys[],Zs[]
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
			//System.out.println(Zs[i]);
		}
		
		for(int i = 0; i < XLength ; i++ )
		{
			for(int j = 0; j < XLength ; j++ )
			{
					ZD[i][j] =Math.abs(Zs[i] - Zs[j]);//�����ֵ
					//System.out.println(ZD[i][j]);
					data[i][j] = Math.sqrt( ( (Xs[i]-Xs[j]) * (Xs[i]-Xs[j]) )
							+ ( (Ys[i]-Ys[j]) * (Ys[i]-Ys[j]) ) );
					DecimalFormat df = new DecimalFormat("####.00");
					data[i][j] = Double.parseDouble(df.format(data[i][j]));//����С�������λ
					ZD[i][j] = Double.parseDouble(df.format(ZD[i][j]));
					//System.out.println(data[i][j]+"      "+ZD[i][j]);
			}
		}
		fitting(data,ZD);
	}
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Var();
	}*/

}
