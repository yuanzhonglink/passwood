package com.pass.suanfa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author yuanzhonglin
 * @date 2021/4/15
 *
 * 算法
 */
public class One {
    public static void main(String[] args) {
        piPeiKuoHao();
    }


    // 最长公共前缀
    public static String replaceSpace(String[] strs) {
        // 数组长度
        int len = strs.length;
        // 用于保存结果
        StringBuilder res = new StringBuilder();
        // 给字符串数组的元素按照升序排序(包含数字的话，数字会排在前面)
        Arrays.sort(strs);
        int m = strs[0].length();
        int n = strs[len - 1].length();
        int num = Math.min(m, n);
        for (int i = 0; i < num; i++) {
            if (strs[0].charAt(i) == strs[len - 1].charAt(i)) {
                res.append(strs[0].charAt(i));
            } else
                break;

        }
        return res.toString();

    }


    // 根据字符串匹配最长回文串长度
    public  int longestPalindrome(String s) {
        if (s.length() == 0)
            return 0;
        // 用于存放字符
        HashSet<Character> hashset = new HashSet<>();
        char[] chars = s.toCharArray();
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (!hashset.contains(chars[i])) {// 如果hashset没有该字符就保存进去
                hashset.add(chars[i]);
            } else {// 如果有,就让count++（说明找到了一个成对的字符），然后把该字符移除
                hashset.remove(chars[i]);
                count++;
            }
        }
        return hashset.isEmpty() ? count * 2 : count * 2 + 1;
    }


    // 判断字符串是否是回文串
    public  boolean isPalindrome(String s) {
        if (s.length() == 0)
            return true;
        int l = 0, r = s.length() - 1;
        while (l < r) {
            // 从头和尾开始向中间遍历
            if (!Character.isLetterOrDigit(s.charAt(l))) {// 字符不是字母和数字的情况
                l++;
            } else if (!Character.isLetterOrDigit(s.charAt(r))) {// 字符不是字母和数字的情况
                r--;
            } else {
                // 判断二者是否相等
                if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r)))
                    return false;
                l++;
                r--;
            }
        }
        return true;
    }


    // --------------------------------找到字符串中最长的回文字符串----------------------------------------
    private static int index, len;

    public static String longestPalindromes(String s) {
        if (s.length() < 2)
            return s;
        for (int i = 0; i < s.length() - 1; i++) {
            PalindromeHelper(s, i, i);
            PalindromeHelper(s, i, i + 1);
        }
        return s.substring(index, index + len);
    }

    public static void PalindromeHelper(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        if (len < r - l - 1) {
            index = l + 1;
            len = r - l - 1;
        }
    }
    // --------------------------------找到字符串中最长的回文字符串----------------------------------------


    // 匹配括号深度
    public static void piPeiKuoHao() {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int cnt = 0, max = 0, i;
        for (i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '(')
                cnt++;
            else
                cnt--;
            max = Math.max(max, cnt);
        }
        sc.close();
        System.out.println(max);
    }


    // 字符串转换为整数
    public static int StrToInt(String str) {
        if (str.length() == 0)
            return 0;
        char[] chars = str.toCharArray();
        // 判断是否存在符号位
        int flag = 0;
        if (chars[0] == '+')
            flag = 1;
        else if (chars[0] == '-')
            flag = 2;
        int start = flag > 0 ? 1 : 0;
        int res = 0;// 保存结果
        for (int i = start; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {// 调用Character.isDigit(char)方法判断是否是数字，是返回True，否则False
                int temp = chars[i] - '0';
                res = res * 10 + temp;
            } else {
                return 0;
            }
        }
        return flag != 2 ? res : -res;
    }


    // 变态跳台阶问题
    int JumpFloorII(int number) {
        return 1 << --number;//2^(number-1)用位移操作进行，更快
    }


    // --------------------------------获取倒数第n个节点-------------------------------------------
    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
    public class Solution {
        public ListNode FindKthToTail(ListNode head, int k) {
            // 如果链表为空或者k小于等于0
            if (head == null || k <= 0) {
                return null;
            }
            // 声明两个指向头结点的节点
            ListNode node1 = head, node2 = head;
            // 记录节点的个数
            int count = 0;
            // 记录k值，后面要使用
            int index = k;
            // p指针先跑，并且记录节点数，当node1节点跑了k-1个节点后，node2节点开始跑，
            // 当node1节点跑到最后时，node2节点所指的节点就是倒数第k个节点
            while (node1 != null) {
                node1 = node1.next;
                count++;
                if (k < 1) {
                    node2 = node2.next;
                }
                k--;
            }
            // 如果节点个数小于所求的倒数第k个节点，则返回空
            if (count < index)
                return null;
            return node2;

        }
    }
    // --------------------------------获取倒数第n个节点-------------------------------------------

}
