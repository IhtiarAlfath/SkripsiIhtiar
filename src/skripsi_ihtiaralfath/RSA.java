/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi_ihtiaralfath;

import java.math.BigInteger;

public class RSA {
    public static BigInteger enkripsi(BigInteger e,BigInteger N, BigInteger m) {
        return m.modPow(e, N);
    }

    public static BigInteger dekripsi(BigInteger d, BigInteger N, BigInteger C){
        return C.modPow(d, N);
    }

    public static String[] stringSplitter(String input, int bits){
        String [] arrStr = input.split("(?<=\\G.{" + stringLength(bits)+ "})");
        return arrStr;
    }

    public static int stringLength(int bits){
        return bits / 4;
    }

    public static processRSA encryptRSA(String plaintext, Key_RSA key){
        String [] messagesplit = stringSplitter(plaintext, Key_RSA.bits);
        String []enkripsi=new String[messagesplit.length];
        BigInteger []cipher = new BigInteger [messagesplit.length];
        
        for(int i=0; i<messagesplit.length;i++){        
            BigInteger message = new BigInteger(messagesplit[i].getBytes());
            cipher[i] = enkripsi(key.e, key.N, message);
            enkripsi[i] = cipher[i].toString();
        }
        String ciphers = String.join("::",enkripsi);
        processRSA ciphertext = new processRSA(ciphers, cipher);
        return ciphertext;
    }

    public static String decryptRSA(String []cipherText ,Key_RSA key){
        String []decrypt=new String[cipherText.length];
        BigInteger []plainbyte = new BigInteger [cipherText.length];
        
        for(int i=0; i<cipherText.length; i++){    
            BigInteger convCipher = new BigInteger(cipherText[i]);
            plainbyte[i] = dekripsi(key.d, key.N, convCipher);
            
            byte[] array2 = plainbyte[i].toByteArray();
            if (array2[0] == 0) {
                byte[] tmp = new byte[array2.length - 1];
                System.arraycopy(array2, 1, tmp, 0, tmp.length);
                array2 = tmp;
            }
            String s2 = new String(array2);
            decrypt[i]=s2;
        }
        String plaintext = String.join("", decrypt);
        return plaintext;
    }
    
//        public static void main(String[] args) {
//        long start1,start2,end1,end2,exc1,exc2;
//        Key_RSA keys = new Key_RSA();
//        Key_RSA key = keys.generateKey();
//        String plain = "Hillbillies singer Scoggins dies\n" +
//"\n" +
//"Country and Western musician Jerry Scoggins has died in Los Angeles at the age of 93, his family has said.\n" +
//"\n" +
//"Scoggins was best remembered for singing the theme tune to popular US TV show The Beverly Hillbillies. The Texan-born singer approached the producers of the programme with theme tune The Ballad of Jed Clampett for the pilot which was screened in 1962. The show, which told the story of a poor man striking oil and moving to Beverly Hills, ran until 1971.\n" +
//"\n" +
//"Scoggins' daugher Jane Kelly Misel said that her father never tired of the song and would sing it at least once a day. \"He'd sing it at birthdays and anniversaries and variety shows. He never stopped performing it,\" she said. When a film version of The Beverly Hillbillies was made in 1993, Scoggins came out of retirement to perform the theme tune. Scoggins sang the lyrics while bluegrass stars Lester Flatt and Earl Scruggs played guitar and banjo.";
//        start1=System.currentTimeMillis();
//        
//        plain = plain.replaceAll("[^\\x00-\\x7F]", "");
//        plain = plain.trim();
//        
//        String []split = plain.split("\n");
//        processRSA []ciphers = new processRSA[split.length];
//        String []ciphersString = new String[split.length];
//        String ciphertext = "";
//        
//        for (int i = 0 ; i<split.length ; i++){
//            if("".equals(split[i])){
//                split[i] = " ";
//            } 
//            ciphers [i] = encryptRSA(split[i], key);
//            ciphersString[i] = ciphers[i].enkripsi;
//        }
//        ciphertext = String.join("&&",ciphersString);
//        
//        end1=System.currentTimeMillis();
//        exc1=end1-start1;
//        
//        System.out.println("================================================");
//        
//        start2=System.currentTimeMillis();
//        
//        String []splitCiphers = ciphertext.split("&&");
//        String []newPlainsItem = new String[splitCiphers.length];
//        String newPlains = "";
//        for (int i = 0 ; i<splitCiphers.length ; i++){     
//            String []splitCipherItems = splitCiphers[i].split("::");
//            newPlainsItem[i] = decryptRSA(splitCipherItems, key);
//        }
//        newPlains = String.join("\n", newPlainsItem);
//        end2=System.currentTimeMillis();
//        exc2=end2-start2;
//        
//        System.out.println("================================================");
//        System.out.println("plaintext : \n" + plain);
//        System.out.println("ciphertext : \n" + ciphertext);
//        System.out.println("new Plaintext : \n" + newPlains);
//
//        System.out.println("start1 :"+start1+", end1: "+end1);
//        System.out.println("start2 :"+start2+", end2: "+end2);
//        System.out.println("Time enkripsi: "+exc1);
//        System.out.println("Time dekripsi: "+exc2);
//    }
}
