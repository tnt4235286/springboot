package com.duodian.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boot")
public class BootController {
	
	// 用于序号
    private static int  index=1;
    
    // 用于清除重复的数，因为有两个2的影响
    private static Set<String> set=new HashSet<String>();
	
	@RequestMapping()
	public String boot(@RequestParam String name) {
		return "dog tian spring boot hello!";
	}
	//用1、2、2、3、4、5这六个数字，用java写一个程序，打印出所有不同的排列，如：512234、412325等，要求："4"不能在第三位，"3"与"5"不能相连。
	
	public static void main(String args[]){
		 Integer[] arr02={1,2,2,3,4,5};
//	     Integer[] arr02={1,2,3};
	     permutation(arr02,0,arr02.length);
	}
	
	 public static void permutation(Integer[] arr,int start,int end){
	        if(start<end+1){
	            permutation(arr,start+1,end);
	            
	            for(int i=start+1;i<end;i++){
	                Integer temp;
	                
	                temp=arr[start];
	                arr[start]=arr[i];
	                arr[i]=temp;
	                
	                permutation(arr,start+1,end);
	                
	                temp=arr[i];
	                arr[i]=arr[start];
	                arr[start]=temp;
	            }
	        }
	        else{
	            print(arr);
	        }
	    }
	 private static void print(Integer[] arr){
	        // "4"不能在第三位
	        if(arr[2]==4){
	            return;
	        }
	        
	        StringBuilder sb=new StringBuilder();
	        
	        for(int i=0;i<arr.length;i++){
	            sb.append(arr[i]);
	        }
	        
	        String word=sb.toString();
	        
	        // "3"与"5"不能相连
	        if(word.contains("35") || word.contains("53")){
	            return;
	        }
	        
	        // 打印
	        System.out.println((index++)+" "+word);
//	        if(set.contains(word)==false){
//	            System.out.println((index++)+" "+word);
//	            set.add(word);
//	        }
	    }
}
