package com.xp.boot.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * MD5工具类
 */

public class MD5Util {

    public static void main(String[] args) {
        //原始密码
        String source = "123456";
        //盐(这个值要是唯一的,可以使用用户名)
        String salt = "zhangsan" + "武汉";
        //散列次数
        int hashIterations = 2;

        encryption(source,salt,hashIterations);
    }

    /**
     * 根据目前shiro的加密规则对密码进行MD5散列计算,在添加用户信息时候需要对原始密码进行散列,然后在存入到数据库中
     * @param source
     * @param salt
     * @param hashIterations
     */
    public static void encryption(String source,String salt,int hashIterations) {
        //方式一:Md5Hash
        //第一个参数:明文.原始密码
        //第二个参数:盐.要使用随机数
        //第三个参数:散列的次数,如:散列两次,就是MD5(MD5('source+salt'))
        //Md5Hash md5Hash = new Md5Hash(source, salt, hashIterations);
        //String password_md5 = md5Hash.toString();
        //System.out.println(password_md5);

        //方式二:SimpleHash
        //第一个参数:散列算法
        //第二个参数:明文.原始密码
        //第三个参数:盐.
        //第四个参数:散列次数
        SimpleHash simpleHash = new SimpleHash("md5", source, salt, hashIterations);
        System.out.println(simpleHash.toString()); //639ffb0cbcca39d4fff8348844b1974e
    }
}
