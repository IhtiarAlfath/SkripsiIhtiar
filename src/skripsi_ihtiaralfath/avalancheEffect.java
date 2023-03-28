/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi_ihtiaralfath;

import java.math.BigInteger;
import java.util.Arrays;

/**
 *
 * @author User
 */
public class avalancheEffect {
    
    public String asciiToBinary(String asciiString){  
      byte[] bytes = asciiString.getBytes();  
      StringBuilder binary = new StringBuilder();  
      for (byte b : bytes)  
      {  
         int val = b;  
         for (int i = 0; i < 8; i++)  
         {  
            binary.append((val & 128) == 0 ? 0 : 1);  
            val <<= 1;  
         }  
        // binary.append(' ');  
      }  
      return binary.toString();  
    }
    
    public String [] makeSomePlain(String plaintext){
        String []somePlain = new String[5];
        char nChar = 'Z';
        String plainSub = plaintext.substring(0, 20);
        String newplain = "";
        
        for(int i=0; i<plainSub.length(); i++){
            switch (i){
                case 0:
                   newplain = plainSub.substring(0, i) + nChar +
                   plainSub.substring(i+1);
                   somePlain[0] = newplain;
                   break;
                case 4:
                   newplain = plainSub.substring(0, i) + nChar +
                   plainSub.substring(i+1);
                   somePlain[1] = newplain;
                   break;
                case 8:
                   newplain = plainSub.substring(0, i) + nChar +
                   plainSub.substring(i+1);
                   somePlain[2] = newplain;
                   break;
                case 12:
                   newplain = plainSub.substring(0, i) + nChar +
                   plainSub.substring(i+1);
                   somePlain[3] = newplain;
                   break;
                case 16:
                   newplain = plainSub.substring(0, i) + nChar +
                   plainSub.substring(i+1);
                   somePlain[4] = newplain;
                   break;    
            }
        }
        return somePlain;
    }
    
    public String avalancheEffect(String cipher1, String[] cipher2, String plaintext1, String[] plaintext2){
        double min_bit = 0;
        double AE = 0;
        String AEItem = "";
        String result = "";
        
        String asciiA = asciiToBinary(cipher1);
        int lenbinA = asciiA.length();
        char[] arrayA = new char[asciiA.length()];
        double []lenBit = new double[cipher2.length];
        double []changeBit = new double[cipher2.length];
        double []count = new double[cipher2.length];
        
        plaintext1 = plaintext1.substring(0, 20);

        for (int j=0; j<cipher2.length; j++){
            String asciiB = asciiToBinary(cipher2[j]);
            char[] arrayB = new char[asciiB.length()];
            int lenbinB = asciiB.length();  

            if (lenbinA >= lenbinB){
                min_bit = lenbinA - lenbinB;
                for (int i = 0; i < lenbinB; i++) {
                    arrayA[i] = asciiA.charAt(i);
                    arrayB[i] = asciiB.charAt(i);
                    if (arrayA[i] != arrayB[i]){
                        count[j]++;
                    } 
                }
                changeBit[j] = (count[j] + min_bit);
                lenBit[j] = lenbinA;
            } else {
                min_bit = lenbinB - lenbinA;
                for (int i = 0; i < lenbinA; i++) {
                    arrayA[i] = asciiA.charAt(i);
                    arrayB[i] = asciiB.charAt(i);
                    if (arrayA[i] != arrayB[i]){
                        count[j]++;
                    } 
                }
                changeBit[j] = (count[j] + min_bit);
                lenBit[j] = lenbinB;
            }

            AE = (changeBit[j] * 100) / lenBit[j];
            
            AEItem = "\n\n======Pengujian ke-"+(j+1)+"======"+
            "\nPlaintext 1 :"+plaintext1+
            "\nPlaintext 2 :"+plaintext2[j]+
            "\nCipher 1 :"+cipher1+
            "\nCipher 2 :"+cipher2[j]+
            "\nBinary 1 : "+asciiA+                
            "\nBinary 2 : " + asciiB+
            "\nPerubahan Bit = "+(changeBit[j])+
            "\nJumlah Bit = "+(lenBit[j])+
            "\nAvalanche effect = " + String.format("%.2f", AE) + "%";
            
            result = result + AEItem ;
        }

        //System.out.println("Avalanche effect = " + ( count / (asciiToBinary(encrypt1).length())) * 100 + "%");

        System.out.println(result);
        return result;
    }
    
    public static void main(String[] args) {   
        Key_RSA keys = new Key_RSA();
        Key_RSA key = keys.generateKey();
        avalancheEffect AE = new avalancheEffect();
        
        System.out.println("e"+key.e);
        String plainText1 = "Comic Morris returns with sitcom";
        plainText1 = plainText1.replaceAll("[^\\x00-\\x7F]", "");
        plainText1 = plainText1.trim();
        String []split1 = plainText1.split("\n");
        processRSA []ciphers1 = new processRSA[split1.length];
        String []ciphersString1 = new String[split1.length];
        String ciphertext1 = "";

        for (int i = 0 ; i<split1.length ; i++){
            if("".equals(split1[i])){
                split1[i] = " ";
            } 
            ciphers1 [i] = RSA.encryptRSA(split1[i], key.getPublicKey());
            ciphersString1 [i] = ciphers1[i].enkripsi;
        }
        
        ciphertext1 = String.join("&&",ciphersString1);
        
        String[] newPlains = AE.makeSomePlain(plainText1);
        
        System.out.println(Arrays.toString(newPlains));
        
        String []ciphertext2 = new String [newPlains.length];
        for(int i=0; i<newPlains.length; i++){
            newPlains[i] = newPlains[i].replaceAll("[^\\x00-\\x7F]", "");
            newPlains[i] = newPlains[i].trim();
            String []split2 = newPlains[i].split("\n");
            processRSA []ciphers2 = new processRSA[split2.length];
            String []ciphersString2 = new String[split2.length];
            String cipher2 = "";

            for (int j = 0 ; j<split2.length ; j++){
                if("".equals(split2[j])){
                    split2[j] = " ";
                } 
                ciphers2 [j] = RSA.encryptRSA(split2[j], key.getPublicKey());
                ciphersString2 [j] = ciphers2[j].enkripsi;
            }
            cipher2 = String.join("&&",ciphersString2);
            ciphertext2[i] = cipher2;
        }
        System.out.println("cipher2; "+Arrays.toString(ciphertext2));
        
        String result = AE.avalancheEffect(ciphertext1,  ciphertext2, plainText1, newPlains); 
        
        System.out.println("RESULT:\n=================================================\n"+result);












//================================================================================================================
//    public String avalancheEffect(String cipher1, String cipher2, String plaintext1, String plaintext2){
//        double count = 0;
//        double min_bit = 0;
//        double AE = 0;
//        
//        String ciphers1 = cipher1.replace("&", "");
//        String ciphers2 = cipher2.replace("&", "");
//        String ciphertext1 = ciphers1.replace(":", "");
//        String ciphertext2 = ciphers2.replace(":", "");;
//
//        String asciiA = asciiToBinary(ciphertext1);
//        String asciiB = asciiToBinary(ciphertext2);
//        
//        char[] arrayA = new char[asciiA.length()];
//        char[] arrayB = new char[asciiB.length()];
//        
//        int lenbinA = asciiA.length();
//        int lenbinB = asciiB.length();
//        
//        double lenBit = 0;
//        double changeBit = 0;
//        
//        System.out.println("len A"+lenbinA);
//        System.out.println("len B"+lenbinB);     
//
//        if (lenbinA >= lenbinB){
//            min_bit = lenbinA - lenbinB;
//            for (int i = 0; i < lenbinB; i++) {
//                arrayA[i] = asciiA.charAt(i);
//                arrayB[i] = asciiB.charAt(i);
//                if (arrayA[i] != arrayB[i]){
//                    count += 1;
//                } 
//            }
//            changeBit = (count + min_bit);
//            lenBit = lenbinA;
//            System.out.println("ONE");
//        } else if (lenbinB > lenbinA) {
//            min_bit = lenbinB - lenbinA;
//            for (int i = 0; i < lenbinA; i++) {
//                arrayA[i] = asciiA.charAt(i);
//                arrayB[i] = asciiB.charAt(i);
//                if (arrayA[i] != arrayB[i]){
//                    count += 1;
//                } 
//            }
//            changeBit = (count + min_bit);
//            lenBit = lenbinB;
//            System.out.println("TWO");
//        } else {
//            for (int i = 0; i < lenbinA; i++) {
//                arrayA[i] = asciiA.charAt(i);
//                arrayB[i] = asciiB.charAt(i);
//                if (arrayA[i] != arrayB[i]){
//                    count += 1;
//                } 
//            }
//            changeBit = count;
//            lenBit = lenbinA;
//            System.out.println("THREE");
//        }
//        
//        AE = (changeBit * 100) / lenBit;        
//        //System.out.println("Avalanche effect = " + ( count / (asciiToBinary(encrypt1).length())) * 100 + "%");
//        String result = "Plaintext 1 :"+plaintext1+
//                "\nPlaintext 2 :"+plaintext2+
//                "\nCipher 1 :"+cipher1+
//                "\nCipher 2 :"+cipher2+
//                "\nByte 1 : "+asciiA+                
//                "\nByte 2 : " + asciiB+
//                "\nPerubahan Bit = "+(count)+
//                "\nJumlah Bit = "+(lenBit)+
//                "\nAvalanche effect = " + String.format("%.2f", AE) + "%";
//        System.out.println(result);
//        return result;
//    }

//        Key_RSA keys = new Key_RSA();
//        Key_RSA key = keys.generateKey();
//        System.out.println("e"+key.e);
//        String plain1 = "Comic Morris returns with sitcom";
//        String plain2 = "Comic Morris returns with Zitcom";
//        plain1 = plain1.replaceAll("[^\\x00-\\x7F]", "");
//        plain1 = plain1.trim();
//        String []split1 = plain1.split("\n");
//        
//        processRSA []ciphers1 = new processRSA[split1.length];
//        String []ciphersString1 = new String[split1.length];
//        String ciphertext1 = "";
//        
//        for (int i = 0 ; i<split1.length ; i++){
//            if("".equals(split1[i])){
//                split1[i] = " ";
//            } 
//            ciphers1 [i] = RSA.encryptRSA(split1[i], key);
//            ciphersString1[i] = ciphers1[i].enkripsi;
//        }
//        
//        plain2 = plain2.replaceAll("[^\\x00-\\x7F]", "");
//        plain2 = plain2.trim();
//        String []split2 = plain2.split("\n");
//        
//        processRSA []ciphers2 = new processRSA[split2.length];
//        String []ciphersString2 = new String[split2.length];
//        String ciphertext2 = "";
//        
//        for (int i = 0 ; i<split2.length ; i++){
//            if("".equals(split2[i])){
//                split2[i] = " ";
//            } 
//            ciphers2 [i] = RSA.encryptRSA(split2[i], key);
//            ciphersString2[i] = ciphers2[i].enkripsi;
//        }
//        
//        ciphertext1 = String.join("&&",ciphersString1);
//        ciphertext2 = String.join("&&",ciphersString2);
//        
//        System.out.println("================================================");
//        avalancheEffect AE = new avalancheEffect();
//        
//        System.out.println("encryot 1: \n" + ciphertext1);
//        System.out.println("encryot 2: \n" + ciphertext2);
//        AE.avalancheEffect(ciphertext1,  ciphertext2, plain1, plain2);
    }   
}
