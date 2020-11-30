package com.example.mycalculator;

import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.lang.*;
        import java.util.ArrayList;
        import java.util.*;

public class InfixInToDuffix {

    //使用集合定义好符号的运算优先级别
    private static final Map<Character,Integer>basic =new HashMap<Character, Integer>();
    static {
        basic.put('-',1);
        basic.put('+', 1);
        basic.put('*', 2);
        basic.put('/', 2);
        basic.put('(', 0);//在运算中  （）的优先级最高，但是此处因程序中需要 故设置为0
    }


    //将中缀表达式转换为后缀表达式
    public String toSuffix(StringBuilder infix){
        List<String> queue = new ArrayList<String>();   //定义队列用于存储数字以及最后的后缀表达式
        List<Character> stack = new ArrayList<Character>();//定义栈用于存储运算符

        char[] charArr = infix.substring(0,infix.length()).trim().toCharArray();//字符数组 用于拆分数字或符号
        String standard = "*/+-()";//判定标准 将表达式中会出现的运算符写出来
        char ch = '&';
        int len = 0;
        for (int i = 0; i < charArr.length; i++) {

            ch = charArr[i];
            if(Character.isDigit(ch)) {
                len++;
            }else if(ch == '.'){
                len++;
            }else if(standard.indexOf(ch) != -1) {
                if(len > 0) {
                    queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len, i)));
                    len = 0;
                }
                if(ch == '(') {
                    stack.add(ch);
                    continue;
                }
                if (!stack.isEmpty()) {
                    int size = stack.size() - 1;
                    boolean flag = false;
                    while (size >= 0 && ch == ')' && stack.get(size) != '(') {
                        queue.add(String.valueOf(stack.remove(size)));
                        size--;
                        flag = true;
                    }
                    if(ch==')'&&stack.get(size) == '('){
                        flag = true;
                    }
                    while (size >= 0 && !flag && basic.get(stack.get(size)) >= basic.get(ch)) {
                        queue.add(String.valueOf(stack.remove(size)));
                        size--;
                    }
                }
                if(ch != ')') {
                    stack.add(ch);
                } else {
                    stack.remove(stack.size() - 1);
                }
            }
            if(i == charArr.length - 1) {
                if(len > 0) {
                    queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len+1, i+1)));
                }
                int size = stack.size() - 1;
                while (size >= 0) {
                    queue.add(String.valueOf(stack.remove(size)));
                    size--;
                }
            }

        }
        String a = queue.toString();
        return a.substring(1,a.length()-1);
    }


    public String dealEquation(String equation){

        String [] arr = equation.split(", ");                                    //根据, 拆分字符串
        List<String> list = new ArrayList<String>();                            //用于计算时  存储运算过程的集合【例如list中当前放置  100   20  5  /  则取出20/5 最终将结果4存入list   此时list中结果为  100  4 】


        for (int i = 0; i < arr.length; i++) {                                    //此处就是上面说的运算过程， 因为list.remove的缘故，所以取出最后一个数个最后两个数  都是size-2
            int size = list.size();
            switch (arr[i]) {
                case "+": double a = Double.parseDouble(list.remove(size-2))+ Double.parseDouble(list.remove(size-2)); list.add(String.valueOf(a));     break;
                case "-": double b = Double.parseDouble(list.remove(size-2))- Double.parseDouble(list.remove(size-2)); list.add(String.valueOf(b));     break;
                case "*": double c = Double.parseDouble(list.remove(size-2))* Double.parseDouble(list.remove(size-2)); list.add(String.valueOf(c));     break;
                case "/": double d = Double.parseDouble(list.remove(size-2))/ Double.parseDouble(list.remove(size-2)); list.add(String.valueOf(d));       break;
                default: list.add(arr[i]);     break;                                    //如果是数字  直接放进list中
            }
        }

        return list.size()


                == 1 ? list.get(0) : "运算失败" ;                    //最终list中仅有一个结果，否则就是算错了
    }

}
