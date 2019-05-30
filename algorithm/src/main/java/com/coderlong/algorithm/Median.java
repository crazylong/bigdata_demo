package com.coderlong.algorithm;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 求中位数
 * 常规思路，我们可将所有数据排序，然后以O(1)时间复杂度取其中位数。但排序的耗费太大，所以我们得另寻他法。
 * 用一个最大堆实现中位数左边位置的存储，最小堆实现右边位置的存储，往堆中插入一个数据的时间复杂度是O(log(n))，取得中位数的时间复杂度是O(1)，并且Java中的PriorityQueue已经帮我们实现了堆的相关操作。
 * @author Long Qiong
 * @create 2019/5/20
 */
public class Median {
    public static void main(String[] args) {

        int[] testDatas={10,20,1,19,28,34,54,33,9,13,12,17,15};
        System.out.println(solution(testDatas));

    }

    public static double solution(int[] datas) {

        if(datas==null||datas.length==0){
            throw new IllegalArgumentException();
        }else {

            double midData=-1;
            //小顶堆
            Queue<Integer> minHeap=new PriorityQueue<Integer>(new Comparator<Integer>() {
                public int compare(Integer o1, Integer o2) {
                    return o1-o2;
                }
            });
            //大顶堆
            Queue<Integer> maxHeap=new PriorityQueue<Integer>(new Comparator<Integer>() {
                public int compare(Integer o1, Integer o2) {
                    return o2-o1;
                }
            });
            for(int i=1;i<=datas.length-1;i++){
                if((i&1)==1){//奇数，插入大顶堆
                    maxHeap.add(datas[i]);
                }else {//偶数，插入小顶堆
                    maxHeap.add(datas[i]);
                    minHeap.add(maxHeap.poll());
                }
            }


            while (!maxHeap.isEmpty()){
                System.out.println(maxHeap.poll());
            }
            while (!minHeap.isEmpty()){
                System.out.println(minHeap.poll());
            }
//            if((datas.length&1)==1){
//                midData=minHeap.peek();
//            }else {
//                midData=(minHeap.peek()+maxHeap.peek())/2.0;
//            }

            return midData;

        }

    }
}
