package lab_5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

class BSTFilesBuilder {

	public void output(int rn,String value) throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter w=new PrintWriter("./src/"+"a.txt","UTF-8");
		w.print(rn+" ");
		w.println(value);
	}
	public void createBSTFiles(int numStudents, int numTrees) {
		Random rand = new Random();
		for (int i = 1; i <= numTrees; i++) {
		    try {
				PrintWriter w = new PrintWriter("./src/" + i + ".txt", "UTF-8");
				int type = rand.nextInt(3) + 1;
				if(type == 1) {
					w.println("Integer");
					w.println(numStudents);
					for (int j = 1; j <= numStudents; j++) {
						w.print(rand.nextInt(1000));
						w.print(" ");
					}
				}
				else if(type == 2) {
					w.println("Float");
					w.println(numStudents);
					for (int j = 1; j <= numStudents; j++) {
						w.print(rand.nextFloat()*1000);
						w.print(" ");
					}
				}
				else {
					w.println("String");
					w.println(numStudents);
					String alphabet = "abcdefghijklmnopqrstuvwxyz";
					for (int j = 1; j <= numStudents; j++) {
						int len = rand.nextInt(10)+1;
						for (int k = 0; k < len; k++)
							w.print(alphabet.charAt(rand.nextInt(alphabet.length())));
						w.print(" ");
					}
				}
				w.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
class gBst <T extends Comparable<T>>
{
	node root;
	String sums;
	int size;
	int sumi;
	float sumf;
	public void insert(T d)
	{
		root=insert(root,d);
		if(d instanceof String)
		{
			sums="";
		}
		else if(d instanceof Integer)
		{
			sumi=0;
		}
		else if(d instanceof Float)
		{
			sumf=0;
		}
	}
	private node insert(node n,T d)
	{
		if(n==null)
		{
			n=new node(d);
		}
		else if(d.compareTo((T) n.getData())<0)
		{
			insert(n.getLeft(),d);
		}
		else
			insert(n.getRight(),d);
		return n;
	}
	public void inorder()
	{
		inorder(root);
	}
	private void inorder(node r)
	{
		if(r!=null)
		{
			inorder(r.getLeft());
			if(r.getData() instanceof String)
			{
				sums+=(String)r.getData();
			}
			else if(r.getData() instanceof Integer)
			{
				sumi+=(int)r.getData();
			}
			else if(r.getData() instanceof Float)
			{
				sumf+=(float)r.getData();
			}
			r.pos+=1;
			inorder(r.getRight());
			
		}
	}
    void deleteKey(T key)
    {
        root = deleteRec(root, key);
    }
     node deleteRec(node root, T key)
    {
        if (root==null)  return root;
         if (key.compareTo((T) root.getLeft())<0)
            root.setLeft(deleteRec(root.getLeft(),key));
        else if (key.compareTo((T) root.getData())>0)
            root.setRight(deleteRec(root.getRight(),key));
        else
        {
        	if (root.getLeft() == null)
                return root.getRight();
            else if (root.getRight() == null)
                return root.getLeft();
            root.setData(minValue(root.getRight()));
            root.setRight(deleteRec(root.getRight(),key));
        } 
        return root;
    }
     T minValue(node root)
     {
         T minv = (T) root.getData();
         while (root.getLeft() != null)
         {
             minv =(T)root.getLeft().getData();
             root=root.getLeft();
         }
         return minv;
     }
 	public void dinorder()
 	{
 		dinorder(root);
 	}
 	private void dinorder(node r)
 	{
 		if(r!=null)
 		{
 			inorder(r.getLeft());
 			deleteKey((T)r.getData());
 			inorder(r.getRight());
 			
 		}
 	}
 	public boolean isEmpty()
 	{
 		if(root==null)
 			return true;
 		else return false;
 	}

}
class node<T>
{
	private T data;
	private node<T> left;
	private node<T> right;
	int pos=0;
	node(T data)
	{
		this.data=data;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public node<T> getLeft() {
		return left;
	}
	public void setLeft(node<T> left) {
		this.left = left;
	}
	public node<T> getRight() {
		return right;
	}
	public void setRight(node<T> right) {
		this.right = right;
	}
	
}

public class lab5 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner sc =new Scanner(System.in);
		int c=0;
		int trees=sc.nextInt();
		int students=sc.nextInt();
		BSTFilesBuilder a=new BSTFilesBuilder();
		a.createBSTFiles(students,trees);
		gBst[] b=new gBst[trees];
		for(int i=0;i<=trees;i++){
			String source="./src/";
			int temp=i+1;
			source+=temp;
			source+=".txt";
			FileReader file=new FileReader(source);
			BufferedReader rd=new BufferedReader(file);
			String t=rd.readLine();
			rd.readLine();
			if(t.equals("Integer"))
			{
				b[i]=new gBst<Integer>();
				for(int j=1;j<students;j++)
				{
					b[i].insert(rd.read());
				}
			}
			else if(t.equals("Float"))
			{
				b[i]=new gBst<Float>();
				for(int j=1;j<students;j++)
				{
					b[i].insert(rd.read());
				}
				
			}
			if(t.equals("String"))
			{
				b[i]=new gBst<Integer>();
				for(int j=1;j<students;j++)
				{
					b[i].insert(rd.read());
				}
			}
			b[i].inorder();					
		}
		for(int i=1;i<trees;i++)
		{
			if(b[i].root.getData() instanceof String)
			{
				if(i==b[i].root.pos)
				{
					a.output(i,b[i].sums);
					c++;
				}
			}
			else if(b[i].root.getData() instanceof Integer)
			{
				if(i==b[i].root.pos)
				{
					a.output(i,Integer.toString(b[i].sumi));
					c++;
				}
			}
			else if(b[i].root.getData() instanceof Float)
			{
				if(i==b[i].root.pos)
				{
					a.output(i,Float.toString(b[i].sumf));
					c++;
				}
			}

		}
		a.output(c,null);

	}

}
