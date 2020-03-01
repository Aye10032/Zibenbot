package com.dazo66.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;

public class fangzhoudiaoluoTest {
    public static void main(String[] args) throws IOException {



        URL fileURL= fangzhoudiaoluoTest.class.getResource("../../../");
        System.out.println(fileURL);
        String s = IOUtils.toString(fileURL.openStream());
        System.out.println(s);





    }





}
