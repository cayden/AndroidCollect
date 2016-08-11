package com.cayden.collect.mode;

import java.util.LinkedList;

/**
 * Created by cuiran on 16/8/2.
 */
public class Suanfa {

    /**
     * 1. 一个房间有100盏灯（全是关着的），
     * 由编号1-100的开关（只有两种状态，开或者关）控制，门外有100个学生，学生按次序一次进入房间，
     * 第一个进入的学生切换是1的倍数的开关，第二个学生切换是2的倍数的开关，以此类推，问，第100学生进入切换后，还有多少盏灯是亮着的？
     */
    private static void light(){
        int N=100;
        int[] light=new int[N];
        for(int k=1;k<=N;k++){
            for(int i=1;i<=N;i++){
                int index=k*i-1;
                if(index<N){
                    light[index]=light[index]+1;
                }else{
                    break;
                }

            }
        }

        for(int i=1;i<=N;i++){
            if(light[i-1]%2!=0){
                System.out.print(i+"("+light[i-1]+")\t");
            }
        }
    }

    /**
     * 2.写一个字符串倒序的算法，请勿使用系统api
     */
    private static void strRevers(){
        char[] str="abcdefghijkl".toCharArray();
        int len=str.length;
        for(int i=0;i<len/2;i++){
            char c=str[i];
            str[i]=str[len-1-i];
            str[len-1-i]=c;
        }
        System.out.println(str);
    }

    /**
     * 3.有一个三位数，个位是c，十位是b，百位是a，求满足abc + cba = 1333的abc
     */
    private static void f3(){
        int b=1;
        for(int a=0;a<=9;a++){
            for(int c=0;c<=9;c++){
                if(a+c==13){
                    System.out.println(a+" "+b+" "+c);
                }
            }
        }
    }

    /**
     * 4.有一组数，求这组数的最大数和最小数的绝对值是多少？
     */
    private static void f4(){
        int [] arr=new int []{4, 6, 9, 52, 36, 97, -63, -55, -1, 64, -36 };
        int len=arr.length;
        int max=0;
        int min=0;
        for(int i=0;i<len;i++){
            if(arr[i]>max){
                max=arr[i];
            }

            if(arr[i]<min){
                min=arr[i];
            }
        }

        System.out.println(Math.abs(max-min));

    }

    /**
     * 5.50个人围成一圈数到三和三的倍数时出圈，问剩下的人是谁？在原来的位置是多少？
     */
    private static void f5(int n,int m){
        LinkedList ll=new LinkedList();
        for(int i=0;i<n;i++){
            ll.add(new Integer(i+1));
        }
        int removed=-1;
        while(ll.size()>1){
            removed=(removed+m)%ll.size();
            ll.remove(removed--);
        }
        System.out.println(((Integer) ll.get(0)).intValue());
    }

    /**
     *6.【java数据结构和算法】给定一个数n,将其扩大为原来的15倍，不能使用乘法、除法和加法，
     * @param n
     */
    private static void f6(int n){
        int result=(n<<4)-n;
        System.out.println(result);
    }

    /**
     * 7.1000台阶 一个人每次走1个或2个 有多少走法
     * @param n
     * @return
     */
    private static int f7(int n){
        if(n==1)return 1;
        if(n==2)return 2;
        if(n==3)return 3;

        return f7(n-1)+f7(n-2);
    }
    public static void main(String args[]){
//        Suanfa.light();
//        Suanfa.strRevers();
//        Suanfa.f3();
//        Suanfa.f4();
//        Suanfa.f5(50,3);
//        Suanfa.f6(10);
        System.out.print("result:"+Suanfa.f7(50));
    }
}
