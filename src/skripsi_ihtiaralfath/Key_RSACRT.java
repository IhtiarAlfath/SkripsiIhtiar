/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Skripsi_IhtiarAlfath ;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

public class Key_RSACRT {
    public BigInteger p, q, N, phiN, d, e;
    public final static int bits = 2048;
    SecureRandom  r = new SecureRandom ();
    
    Key_RSACRT(){
        this.p = BigInteger.ZERO;
        this.q = BigInteger.ZERO;
        this.N = BigInteger.ZERO;
        this.phiN = BigInteger.ZERO;
        this.d = BigInteger.ZERO;
        this.e = BigInteger.ZERO;
    }
    
    public void setP(BigInteger p){
        this.p = p;
    }
    
    public BigInteger getP(){
        return this.p;
    }
    
    public void setQ(BigInteger q){
        this.q = q;
    }
    
    public BigInteger getQ(){
        return this.q;
    }

    public void setN(BigInteger N){
        this.N = N;
    }
    
    public BigInteger getN(){
        return this.N;
    }

    public void setPhiN(BigInteger phiN){
        this.phiN = phiN;
    }
    
    public BigInteger getPhiN(){
        return this.phiN;
    }

    public void setD(BigInteger d){
        this.d = d;
    }
    
    public BigInteger getd(){
        return this.d;
    }   
    
    public void setE(BigInteger e){
        this.e = e;
    }
    
    public BigInteger getE(){
        return this.e;
    }   

    public static BigInteger generateProbablePrime(int bitSize){
        return BigInteger.probablePrime(bitSize, new SecureRandom());
    }

    public static BigInteger calculateN(BigInteger p, BigInteger q){
        return p.multiply(q);
    }

    public static BigInteger toten(BigInteger p, BigInteger q){
        BigInteger phiN = p.subtract(BigInteger.ONE);
        phiN = phiN.multiply(q.subtract(BigInteger.ONE));
        return phiN;
    }

    public static BigInteger gcd(BigInteger e, BigInteger n){
        if(n.equals(BigInteger.ZERO))
            return e;
        return gcd(n, e.mod(n));
    }

    public static BigInteger crt(BigInteger d, BigInteger p, BigInteger q, BigInteger m){
       BigInteger dp, dq, qInverse, m1, m2, h;

       dp = d.mod(p.subtract(BigInteger.ONE));
       dq = d.mod(q.subtract(BigInteger.ONE));
       qInverse = q.modInverse(p);
       
       m1 = m.modPow(dp,p);
       m2 = m.modPow(dq,q);
       h = qInverse.multiply(m1.subtract(m2)).mod(p);
       m = m2.add(h.multiply(q));

       return m;
    }
    
    public BigInteger generateE(BigInteger toten){
         BigInteger elocal = new BigInteger(bits, 100, r);
         BigInteger gcd = gcd(elocal, toten);
         while (elocal.compareTo(toten) == 1 || elocal.compareTo(BigInteger.ONE)
                 == -1 || gcd.equals(BigInteger.ONE) == false){
             generateE(toten);
         }
         return elocal;
    }
    
    public Key_RSACRT generateKey(){
        Key_RSACRT key = new Key_RSACRT();
        key.setP(generateProbablePrime(bits));
        key.setQ(generateProbablePrime(bits));
        key.setN(calculateN(key.p,key.q));
        key.setPhiN(toten(key.p,key.q));
        key.setE(generateE(key.phiN));    
        //generate the d decryption exponent
        key.setD(key.e.modInverse(key.phiN)); 
        return key;
    }    
    
    public static int saveFileKey(Key_RSACRT key, String fName){
        try{
            File myPublicKey = new File(fName + "-pbc" + ".rsacrtpublickey");
            if(myPublicKey.createNewFile()){
                try (FileWriter myWriter = new FileWriter(fName + "-pbc" + ".rsacrtpublickey")) {
                    String content = key.N.toString() + "," + key.e.toString();
                    myWriter.write(content);
                } catch(Exception exc){
                    System.out.println(exc);
                    return -1;
                }
                System.out.println("Public key saved");          
            } else {
                System.out.println("File already exists.");
                return -1;
            }

            File myPrivateKey = new File(fName + "-pvt" + ".rsacrtprivatekey");
            if(myPrivateKey.createNewFile()){
                try (FileWriter myWriter = new FileWriter(fName + "-pvt" + ".rsacrtprivatekey")) {
                    String content = key.N.toString() + "," + key.d.toString()+ "," + key.p.toString()+ "," + key.q.toString();
                    myWriter.write(content);
                }catch(Exception exc){
                    System.out.println(exc);
                    return -1;
                }
                System.out.println("Private key saved");
            } else {
                System.out.println("File already exists.");
                return -1;
            }
            
        }catch(Exception exc){
            System.out.println(exc);
            return -1;
        }
        return 1;
    }
    
    public String[] readKey(String path){
        String data = "";
        String key[] = {};
        try{
            File myObj = new File(path);
            Scanner readFile = new Scanner(myObj);
            while (readFile.hasNextLine()) {
                data = readFile.nextLine();
                System.out.println(data);
            }
            key = data.split(",");
            readFile.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        return key;
    }
    
    public void setPublicKey(BigInteger N, BigInteger e){
        this.N = N;
        this.e = e;
    }
    
    public void setPrivateKey(BigInteger N, BigInteger D, BigInteger P, BigInteger Q){
        this.N = N;
        this.d = D;
        this.p = P;
        this.q = Q;
    }
    
    public Key_RSACRT getPublicKey(){
        Key_RSACRT key = new Key_RSACRT();
        key.N = this.N;
        key.e = this.e;
        return key;
    }
    
    public Key_RSACRT getPrivateKey(){
        Key_RSACRT key = new Key_RSACRT();
        key.N = this.N;
        key.d = this.d;
        key.p = this.p;
        key.q = this.q;
        return key;
    }
    
    public Key_RSACRT getPublicKey(String [] readKey){
        Key_RSACRT key = new Key_RSACRT();
        key.N = new BigInteger(readKey[0]);
        key.e = new BigInteger(readKey[1]);
        return key;
    }
    
    public Key_RSACRT getPrivateKey(String [] readKey){
        Key_RSACRT key = new Key_RSACRT();
        key.N = new BigInteger(readKey[0]);
        key.d = new BigInteger(readKey[1]);
        key.p = new BigInteger(readKey[2]);
        key.q = new BigInteger(readKey[3]);
        return key;
    }    
}
