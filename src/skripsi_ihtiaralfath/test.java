/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi_ihtiaralfath;

/**
 *
 * @author User
 */
public class test {
    public static void main(String[] args){
            String str = "This sentance contains find me string";
            System.out.println(str);
            // find word in String
            String find = "find me";
            int i = str.indexOf(find);
            if(i>0)
                    System.out.println(str.substring(0, 20));
            else 
                    System.out.println("string not found");
    }
}
