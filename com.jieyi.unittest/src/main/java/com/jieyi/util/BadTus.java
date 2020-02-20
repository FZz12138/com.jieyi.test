package com.jieyi.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BadTus
 * @Description TODO
 * @Author Administrator
 * @Date 2019/9/10 0010 18:53
 * @Version 1.0
 **/
public class BadTus {
    public static void main(String[] args) {
       int[] arr={1,2,3,4,5};
       int a=3;
       int[] arr1=twoSum(arr,a);
    }
    public static int[] twoSum(int[] arr,int a){
        Map<Integer,Integer> map =new HashMap<Integer, Integer>();
        for (int i=0;i<arr.length-1;i++){
            int come = a-arr[i];
            if(map.containsKey(come)){
                return new int[]{map.get(come),i};
            }
            map.put(arr[i],i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
