package com.seal.reactive;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: seal
 * @Description: 普通测试
 * @company: xingfeiinc
 * @e-mail: linxiao@xingfeiinc.com
 * @date: 2018-10-24 10:44
 */
public class CommonTest extends ReactiveApplicationTests {

    @Test
    public void versionCodeTest() throws Exception {
//        String versionCode = "1.99.99";
//        String versionCode = "2.56.12";
//        String versionCode = "1.23.99";
        String versionCode = "1.33.11";
        String newVersionCode = increase(versionCode);
        System.out.println(newVersionCode);
    }

    private String increase(String versionCode) throws Exception {
        String[] codes = versionCode.split("\\.");
        if (codes.length == 3) {
            int firstVersionNum = Integer.parseInt(codes[0]);
            Integer secondVersionNum = Integer.valueOf(codes[1]);
            Integer lastVersionNum = Integer.valueOf(codes[2]);
            if (codes[1].equals("99") && codes[2].equals("99")) {
                firstVersionNum ++;
                return firstVersionNum + ".00.00";
            } else if (codes[2].equals("99")) {
                secondVersionNum ++;
                return firstVersionNum + "." + secondVersionNum + ".00";
            } else {
                lastVersionNum ++;
                return firstVersionNum + "." + secondVersionNum + "." + lastVersionNum;
            }
        } else {
            throw new Exception();
        }
    }


    @Test
    public void uuidTest() throws InterruptedException {
        Set<String> set = Sets.newConcurrentHashSet();
        Thread thread01 = new Thread(new UUIDGenTester(set));
        Thread thread02 = new Thread(new UUIDGenTester(set));
        Thread thread03 = new Thread(new UUIDGenTester(set));

        thread01.start();
        thread02.start();
        thread03.start();

        TimeUnit.SECONDS.sleep(15);

        System.out.println(set.size());
    }

    private class UUIDGenTester implements Runnable {

        private Set<String> set;

        public UUIDGenTester(Set<String> set) {
            this.set = set;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                UUID uuid = UUID.randomUUID();
                String s = uuid.toString().replaceAll("-", "").substring(0, 16);
                set.add(s);
                System.out.println(s);
                System.out.println(s.length());
            }
        }
    }
}
