package com.sdyin.netty.demo;

/**
 * @Description: 三个物品不同重量对应不同价值，重量价值对应如下: 1kg -> 6, 2kg -> 10, 3kg -> 12
 * 背包只能装下5kg物品，请求算出能装下最大价值, 每个物品只能用一次。s
 * @Author: liuye
 * @time: 2021/11/1$ 9:58 上午$
 */
public class DpDemo1 {

    public static void main(String[] args) {
        int weightArr[] = {0 ,3, 7, 8};
        int valueArr[] = {0 ,6, 10, 12};
        // 最大重量
        int maxWeight = 5;

        int dp[][] = new int[weightArr.length][maxWeight + 1];
        int maxValue = 0;
        //物品个数
        for (int i = 0; i < weightArr.length; i++) {
            //最大重量
            for (int j = 0; j <= maxWeight; j++) {
                if (i == 0){
                    dp[i][j] = 0;
                    continue;
                }
                if(j == 0){
                    dp[i][j] = 0;
                    continue;
                }
                int weight = weightArr[i];
                if (weight <= j) {
                    /*//选当前物品
                    int a = dp[i-1][j-weightArr[i]]  +  valueArr[i];
                    //不选当前物品
                    dp[i][j] = dp[i-1][j];*/
                    //左边和上边的最大值
                    dp[i][j] = Math.max(dp[i-1][j-weightArr[i]]  +  valueArr[i], dp[i-1][j]);
                } else {
                    dp[i][j] = dp[i-1][j];
                }
                maxValue = dp[i][j];
                System.out.println("dp[" + i + "]" + "[" + j + "]:" + maxValue);
            }
        }
        System.out.println("maxValue:" + maxValue);
    }
}
