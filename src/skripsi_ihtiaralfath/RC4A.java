/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi_ihtiaralfath;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class RC4A {
        
    private int[] S1, S2; 
    private int K, J1, J2, Temp, Y;

    public ascii_RC4A tbl = new ascii_RC4A();
    
    public Character[] ch;
    
    public RC4A(){
        this.S1 = new int[256];
        this.S2 = new int[256];
    }
    
    public void KSA1(String key) {
        for(int i=0; i<256; i++) {
            this.S1[i] = i;
        } 
        for(int i=0; i<256; i++) {
            char c = key.charAt(i % key.length());
            this.K = tbl.getDesimal(c);
            this.J1 = (this.J1 + this.S1[i] + this.K) % 256;
            this.Temp = this.S1[i];
            this.S1[i] = this.S1[J1];
            this.S1[J1] = this.Temp;
        } 
    } 
    
    public void KSA2(String key) {
        for(int i=0; i<256; i++) {
            this.S2[i] = i;
        } 
        for(int i=0; i<256; i++) {
            char c = key.charAt(i % key.length());
            this.K = tbl.getDesimal(c);
            this.J2 = (this.J2 + this.S2[i] + this.K) % 256;
            this.Temp = this.S2[i];
            this.S2[i] = this.S2[J2];
            this.S2[J2] = this.Temp;
        } 
    }
    
    public String PRGA1(String keys) {
        String Key = "";
        for(int i=0; i<keys.length(); i++) {
            this.Y = i % 256;
            this.J1 = (this.J1 + this.S1[this.Y]) % 256;
            this.Temp = this.S1[this.Y];
            this.S1[this.Y] = this.S1[this.J1];
            this.S1[J1] = this.Temp;
            int Z1 = this.S1[(this.S1[this.Y] + this.S1[this.J1]) % 256];
            Key += tbl.getChar(Z1);
        } 
        return Key;
    } 
    
    public String PRGA2(String data) {
        String Ciphertext = "";
        for(int i=0; i<data.length(); i++){
            char c = data.charAt(i);
            int Z1 = 0, Z2 = 0;
            this.Y = i % 256;
            this.J1 = (this.J1 + this.S1[this.Y]) % 256;
            this.Temp = this.S1[this.Y];
            this.S1[Y] = this.S1[this.J1];
            this.S1[J1] = this.Temp;
            Z1 = this.S2[(this.S1[this.Y] + this.S1[this.J1]) % 256];
            Z1 = Z1 ^ tbl.getDesimal(c);
            this.J2 = (this.J2 + this.S2[this.Y]) % 256;
            this.Temp = this.S2[this.Y];
            this.S2[Y] = this.S2[this.J2];
            this.S2[J2] = this.Temp;
            Z2 = this.S1[(this.S2[this.Y] + this.S2[this.J2]) % 256];
            Z2 = Z2 ^ Z1;
            Ciphertext += tbl.getChar(Z2);
        } 
     return Ciphertext;
    }
    
    public String Process(String data, String key) { 
        this.K=0;
        this.Temp = 0;
        this.J1 = 0; 
        this.J2 = 0; 
        this.Y = 0;
        KSA1(key);
        KSA2(PRGA1(key)); 
        return PRGA2(data); 
    }
    
    public String convertStringToBinary(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );
        }
        return result.toString();

    }    
    
    public BigInteger encryptProcessRC4A(String plaintext, String key){
        RC4A objek = new RC4A();
        String encrypt = "";
        String binary = "";
        String hex = "";
        ArrayList<String> cipherList = new ArrayList<String>();
        
        Scanner scanner = new Scanner(plaintext);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            encrypt = objek.Process(line, key);     
            cipherList.add(encrypt);
        }
        String cipher = String.join("#:#:", cipherList);

        byte [] b = cipher.getBytes(); //cipher conv to byte and byte conv to bigInt
        BigInteger ciphertext = new BigInteger(b);
         
        return ciphertext;
    }
    
    public String decryptProcessRC4A(String ciphertext, String key){
        RC4A objek = new RC4A(); //cipherlisst didapat terus diubah formatnyake string
        String plainDecrypt = "";
        String binaryDecrypt = "";
        String hexDecrypt = "";
        
        BigInteger cipherBigInteger = new BigInteger(ciphertext);
        byte [] b = cipherBigInteger.toByteArray();
        String ciphers = new String(b);
        
        String []split = ciphers.split("#:#:");
        ArrayList<String> plainList = new ArrayList<String>();
        int i = 0;
        for(String cipher : split){
            plainDecrypt = objek.Process(cipher, key);
            plainList.add(plainDecrypt);
            i++;
        }
        String plainText = String.join("\n", plainList);
        return plainText;
    }
    
//    public static void main(String[] args) {
//        RC4A rc = new RC4A();
//        long start,end,exc;
//        Scanner in = new Scanner (System.in);
//        System.out.println("Input Plaintext:");
//        String plain = "Hillbillies si'ng'er Scoggins dies\n" +
//"\n" +
//"Country and Western musician Jerry Scoggins has died in Los Angeles at the age of 93, his family has said.\n" +
//"\n" +
//"Scoggins was best remembered for singing the theme tune to popular US TV show The Beverly Hillbillies. The Texan-born singer approached the producers of the programme with theme tune The Ballad of Jed Clampett for the pilot which was screened in 1962. The show, which told the story of a poor man striking oil and moving to Beverly Hills, ran until 1971.\n" +
//"\n" +
//"Scoggins' daugher Jane Kelly Misel said that her father never tired of the song and would sing it at least once a day. \"He'd sing it at birthdays and anniversaries and variety shows. He never stopped performing it,\" she said. When a film version of The Beverly Hillbillies was made in 1993, Scoggins came out of retirement to perform the theme tune. Scoggins sang the lyrics while bluegrass stars Lester Flatt and Earl Scruggs played guitar and banjo.";
//        System.out.println(plain);
//        System.out.println("Input Kunci:==================================================");
//        String key = in.next();
//        start = System.currentTimeMillis();
//        BigInteger encrypt = rc.encryptProcessRC4A(plain, key);
//        end = System.currentTimeMillis();
//        System.out.println("exc="+(end-start));
//        System.out.println("encrypt BigInteger:\n"+encrypt);
//        String encryptStr = encrypt.toString();
//        String decrypt = rc.decryptProcessRC4A(encryptStr, key);
//        System.out.println("============================================");
//        System.out.println("encrypt result:\n"+encryptStr+"\n");
//        System.out.println("decrypt result:\n"+decrypt);
//        System.out.println("============================================");    
//    }
}
